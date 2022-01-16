package modele;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

import javax.ws.rs.client.Client;

import infrastructure.jaxrs.HyperLien;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RechercheSynchroneStreamRx extends RechercheSynchroneAbstraite{

	private NomAlgorithme nomAlgo;

	public RechercheSynchroneStreamRx(String nomAlgo) {
		this.nomAlgo = new ImplemNomAlgorithme(nomAlgo);
	}


	@Override
	public Optional<HyperLien<Livre>> chercher(Livre l, List<HyperLien<Bibliotheque>> bibliotheques, Client client) {
		// TODO Auto-generated method stub
		return Observable.fromIterable(bibliotheques)
				.flatMap(h -> Observable.fromCallable(new MyCallable(h,this, l, client)).subscribeOn(Schedulers.io()))
				.filter(Optional::isPresent).blockingFirst();
	}

	@Override
	public NomAlgorithme nom() {
		return this.nomAlgo;
	}

	private class MyCallable implements Callable<Optional<HyperLien<Livre>>> {

		private HyperLien<Bibliotheque> lien;
		private int index;
		private AtomicReference<Optional<HyperLien<Livre>>> result;
		private RechercheSynchroneStreamRx rechercheSynchroneStreamRx;
		private Livre l;
		private Client client;

		MyCallable(HyperLien<Bibliotheque> lien, RechercheSynchroneStreamRx rechercheSynchroneStreamRx, Livre l, Client client){
			this.lien = lien;
			this.index = index;
			this.result = result;
			this.rechercheSynchroneStreamRx = rechercheSynchroneStreamRx;
			this.l = l;
			this.client = client;
		}

		@Override
		public Optional<HyperLien<Livre>> call() throws Exception {
			return rechercheSynchroneStreamRx.rechercheSync(lien, l, client);
		}
	}
}
