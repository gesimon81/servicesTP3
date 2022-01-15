package modele;

import java.util.ArrayList;
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
import javax.ws.rs.client.InvocationCallback;

import infrastructure.jaxrs.HyperLien;

public class RechercheSynchroneMultiTaches extends RechercheSynchroneAbstraite implements AlgorithmeRecherche {

private NomAlgorithme nomAlgo;
private ExecutorService pool;
	
	public RechercheSynchroneMultiTaches(String nomAlgo) {
		this.pool = Executors.newCachedThreadPool();
		this.nomAlgo = new ImplemNomAlgorithme(nomAlgo);
		this.pool = Executors.newCachedThreadPool();
	}
	
	@Override
	public Optional<HyperLien<Livre>> chercher(Livre l, List<HyperLien<Bibliotheque>> bibliotheques, Client client) {
		final CountDownLatch doneSignal = new CountDownLatch(bibliotheques.size());
		AtomicReference<Optional<HyperLien<Livre>>> result = new AtomicReference<Optional<HyperLien<Livre>>>();

		for (HyperLien<Bibliotheque> lien : bibliotheques) {
	           for (int i = 0; i < 10; i++) {  
	        	   this.pool.execute(new MyRunnable(lien, i, result, doneSignal, this, l, client));
	           } 
		}

		try {
			doneSignal.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result.get();
	}
	
	private class MyRunnable implements Runnable{

		private HyperLien<Bibliotheque> lien;
		private int index;
		private AtomicReference<Optional<HyperLien<Livre>>> result;
		private CountDownLatch doneSignal;
		private RechercheSynchroneMultiTaches rechercheSynchroneMultiTaches;
		private Livre l;
		private Client client;
		
		MyRunnable(HyperLien<Bibliotheque> lien, int index, AtomicReference<Optional<HyperLien<Livre>>> result, CountDownLatch doneSignal, RechercheSynchroneMultiTaches rechercheSynchroneMultiTaches, Livre l, Client client){
			this.lien = lien;
			this.index = index;
			this.result = result;
			this.doneSignal = doneSignal;
			this.rechercheSynchroneMultiTaches = rechercheSynchroneMultiTaches;
			this.l = l;
			this.client = client;
		}
		
		@Override
		public void run() {
			Optional<HyperLien<Livre>> livre = rechercheSynchroneMultiTaches.rechercheSync(lien, l, client);
			if(livre.isPresent()) {
				result.set((livre));
				for (int i = 0;i< doneSignal.getCount();i++) {
					doneSignal.countDown();
				}
			}
			doneSignal.countDown();
		}
	}


	@Override
	public NomAlgorithme nom() {
		return nomAlgo;
	}


}
