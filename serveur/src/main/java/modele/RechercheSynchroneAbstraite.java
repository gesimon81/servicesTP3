package modele;

import java.util.Optional;

import javax.ws.rs.client.Client;

import infrastructure.jaxrs.HyperLien;
import infrastructure.jaxrs.LienVersRessource;

public abstract class RechercheSynchroneAbstraite implements AlgorithmeRecherche {

	protected Optional<HyperLien<Livre>>
    rechercheSync(HyperLien<Bibliotheque> h, Livre l, Client client){
		Bibliotheque bib = LienVersRessource.proxy(client, h, Bibliotheque.class);
		System.out.println("RechercheSynchroneAbstraite.rechercheSync(...)");
		return bib.chercher(l);
	};
}