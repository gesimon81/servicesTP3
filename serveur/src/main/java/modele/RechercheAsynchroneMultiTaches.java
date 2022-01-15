package modele;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.InvocationCallback;

import infrastructure.jaxrs.HyperLien;

public class RechercheAsynchroneMultiTaches extends RechercheAsynchroneAbstraite {
	private NomAlgorithme nomAlgo;

	public RechercheAsynchroneMultiTaches(String nomAlgo) {
		this.nomAlgo = new ImplemNomAlgorithme(nomAlgo);
	}

	@Override
	public Optional<HyperLien<Livre>> chercher(Livre l, List<HyperLien<Bibliotheque>> bibliotheques, Client client) {
		final CountDownLatch doneSignal = new CountDownLatch(bibliotheques.size());
		final AtomicReference<Optional<HyperLien<Livre>>> resultat = new AtomicReference<Optional<HyperLien<Livre>>>();
		for (HyperLien<Bibliotheque> lien : bibliotheques) {
			InvocationCallback<Optional<HyperLien<Livre>>> cb = new InvocationCallback<Optional<HyperLien<Livre>>>() {

				@Override
				public void failed(Throwable throwable) {
					// TODO Auto-generated method stub
					doneSignal.countDown();
				}

				@Override
				public void completed(Optional<HyperLien<Livre>> response) {

					doneSignal.countDown();
					if (response.isPresent()) {
						resultat.set(response);
						for (int i = 0; i < bibliotheques.size(); i++) {
							doneSignal.countDown();
						}
					}
				}
			};

			super.rechercheAsyncAvecRappel(lien, l, client, cb);
		}
		try {
			doneSignal.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultat.get();
	}

	@Override
	public NomAlgorithme nom() {
		return this.nomAlgo;
	}

}
