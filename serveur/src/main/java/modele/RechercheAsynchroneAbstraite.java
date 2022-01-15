package modele;

import java.util.Optional;
import java.util.concurrent.Future;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.InvocationCallback;
import javax.ws.rs.client.WebTarget;

import infrastructure.jaxrs.HyperLien;
import infrastructure.langage.Types;

public abstract class RechercheAsynchroneAbstraite implements AlgorithmeRecherche {

	protected Future<Optional<HyperLien<Livre>>> rechercheAsync(HyperLien<Bibliotheque> h, Livre l, Client client) {
		WebTarget cible = client.target(h.getUri());
		var entity = Entity.xml(l);
		return cible.request().async().method("PUT", entity, Types.typeRetourChercherAsync());

	}

	protected Future<Optional<HyperLien<Livre>>> rechercheAsyncAvecRappel(HyperLien<Bibliotheque> h, Livre l,
			Client client, InvocationCallback<Optional<HyperLien<Livre>>> retour) {
		WebTarget cible = client.target(h.getUri());
		return cible.request().async().method("PUT", Entity.xml(l), retour);
	}
}
