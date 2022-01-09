package modele;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicReference;

import javax.ws.rs.client.Client;

import infrastructure.jaxrs.HyperLien;

public class RechercheSynchroneMultiTaches extends RechercheSynchroneAbstraite{

private NomAlgorithme nomAlgo;
private final ExecutorService pool;
	
	public RechercheSynchroneMultiTaches(String nomAlgo) {
		this.pool = Executors.newCachedThreadPool();
		this.nomAlgo = new ImplemNomAlgorithme(nomAlgo);
		
	}
	
	@Override
	public Optional<HyperLien<Livre>> chercher(Livre l, List<HyperLien<Bibliotheque>> bibliotheques, Client client) {
		final AtomicReference<Optional<HyperLien<Livre>>> resultat = new AtomicReference<Optional<HyperLien<Livre>>>();
	    CountDownLatch doneSignal = new CountDownLatch(bibliotheques.size());
	    List<Future<AtomicReference<Optional<HyperLien<Livre>>>>> results = new LinkedList<>();
     
		for(int i = 0;i < bibliotheques.size();i++) {
			
			SearchTask searchTask = new SearchTask(doneSignal, l, bibliotheques.get(i), client, this);
			System.out.println("submitting tasks");
			results.add(pool.submit(searchTask));
			try {
				doneSignal.await();
				pool.shutdown();
				System.out.println("Resultats: "+results);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return Optional.empty();
	}
	
	private class SearchTask implements Callable<AtomicReference<Optional<HyperLien<Livre>>>> {
	    
		private CountDownLatch doneSignal;
		private Livre l;
		private  HyperLien<Bibliotheque> bibliotheque;
		private Client client;
		private RechercheSynchroneMultiTaches rechercheSynchroneMultiTaches;
		public SearchTask(CountDownLatch doneSignal,Livre l, HyperLien<Bibliotheque> bibliotheque, Client client, RechercheSynchroneMultiTaches rechercheSynchroneMultiTaches) {
			this.doneSignal = doneSignal;
			this.l = l;
			this.bibliotheque = bibliotheque;
			this.client = client;
			this.rechercheSynchroneMultiTaches = rechercheSynchroneMultiTaches;
		}
		
		public AtomicReference<Optional<HyperLien<Livre>>> call() {
			Optional<HyperLien<Livre>> lienLivre = rechercheSynchroneMultiTaches.rechercheSync(bibliotheque, l, client);
	    	  	
	    	doneSignal.countDown();
	    	return new AtomicReference<Optional<HyperLien<Livre>>>(lienLivre);
	    }
	}

	@Override
	public NomAlgorithme nom() {
		return nomAlgo;
	}


}
