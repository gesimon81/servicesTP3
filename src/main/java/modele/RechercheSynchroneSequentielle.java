package modele;

import java.util.List;
import java.util.Optional;

import javax.ws.rs.client.Client;

import infrastructure.jaxrs.HyperLien;

public class RechercheSynchroneSequentielle extends RechercheSynchroneAbstraite implements AlgorithmeRecherche {

	private NomAlgorithme nomAlgo;

	public RechercheSynchroneSequentielle(String nomAlgo) {
		this.nomAlgo = new ImplemNomAlgorithme(nomAlgo);
	}

	@Override
	public Optional<HyperLien<Livre>> chercher(Livre l, List<HyperLien<Bibliotheque>> bibliotheques, Client client) {
		for (int i = 0; i < bibliotheques.size(); i++) {
			Optional<HyperLien<Livre>> lienLivre = super.rechercheSync(bibliotheques.get(i), l, client);
			if (!lienLivre.equals(Optional.empty())) {
				return lienLivre;
			}
		}
		return Optional.empty();
	}

	@Override
	public NomAlgorithme nom() {
		return nomAlgo;
	}

}
