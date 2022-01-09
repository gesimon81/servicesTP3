package modele;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Future;

import javax.ws.rs.client.Client;

import infrastructure.jaxrs.HyperLien;
import infrastructure.jaxrs.Outils;

public class RechercheAsynchroneSequentielle extends RechercheAsynchroneAbstraite implements AlgorithmeRecherche {
	private NomAlgorithme nomAlgo;

	public RechercheAsynchroneSequentielle(String nomAlgo) {
		this.nomAlgo = new ImplemNomAlgorithme(nomAlgo);
	}

	@Override
	public Optional<HyperLien<Livre>> chercher(Livre l, List<HyperLien<Bibliotheque>> bibliotheques, Client client) {
		List<Future<Optional<HyperLien<Livre>>>> promesses = new ArrayList<>();
		for (HyperLien<Bibliotheque> lien : bibliotheques) {
			promesses.add(super.rechercheAsync(lien, l, client));
		}

		for (Future<Optional<HyperLien<Livre>>> promesse : promesses) {
			Optional<HyperLien<Livre>> result = Outils.remplirPromesse(promesse);
			if (result != null && result.isPresent()) {
				return result;
			}
		}
		return null;
	}

	@Override
	public NomAlgorithme nom() {
		return this.nomAlgo;
	}

}
