package modele;

import java.util.List;
import java.util.Optional;

import javax.ws.rs.client.Client;

import infrastructure.jaxrs.HyperLien;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RechercheAsynchroneStreamRx extends RechercheAsynchroneAbstraite {

	private NomAlgorithme nomAlgo;

	public RechercheAsynchroneStreamRx(String nomAlgo) {
		this.nomAlgo = new ImplemNomAlgorithme(nomAlgo);
	}

	@Override
	public Optional<HyperLien<Livre>> chercher(Livre l, List<HyperLien<Bibliotheque>> bibliotheques, Client client) {
		// TODO Auto-generated method stub
		return Observable.fromIterable(bibliotheques)
				.flatMap(h -> Observable.fromFuture(rechercheAsync(h, l, client)).subscribeOn(Schedulers.io()))
				.filter(Optional::isPresent).blockingFirst();
	}

	@Override
	public NomAlgorithme nom() {
		return this.nomAlgo;
	}

}
