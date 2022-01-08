package modele;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.Future;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.InvocationCallback;

import infrastructure.jaxrs.HyperLien;

public class RechercheAsynchroneStreamRx extends RechercheAsynchroneAbstraite {

	@Override
	public Optional<HyperLien<Livre>> chercher(Livre l, List<HyperLien<Bibliotheque>> bibliotheques, Client client) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NomAlgorithme nom() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Future<Optional<HyperLien<Livre>>> rechercheAsync(HyperLien<Bibliotheque> h, Livre l, Client client) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Future<Optional<HyperLien<Livre>>> rechercheAsyncAvecRappel(HyperLien<Bibliotheque> h, Livre l,
			Client client, InvocationCallback<Optional<HyperLien<Livre>>> retour) {
		// TODO Auto-generated method stub
		return null;
	}

}
