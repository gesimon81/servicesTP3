package modele;

import java.util.List;
import java.util.Optional;

import javax.ws.rs.client.Client;

import infrastructure.jaxrs.HyperLien;
import infrastructure.jaxrs.Outils;

public class RechercheAsynchroneStreamParallele extends RechercheAsynchroneAbstraite implements AlgorithmeRecherche {

	private NomAlgorithme nomAlgo;

	public RechercheAsynchroneStreamParallele(String nomAlgo) {
		this.nomAlgo = new ImplemNomAlgorithme(nomAlgo);
	}

	@Override
	public Optional<HyperLien<Livre>> chercher(Livre l, List<HyperLien<Bibliotheque>> bibliotheques, Client client) {
		return bibliotheques.parallelStream().map((b) -> super.rechercheAsync(b, l, client))
				.map(Outils::remplirPromesse).filter(Optional::isPresent).findAny().orElse(Optional.empty());

	}

	@Override
	public NomAlgorithme nom() {
		return this.nomAlgo;
	}

}
