package modele;

import java.util.List;
import java.util.Optional;

import javax.ws.rs.client.Client;

import infrastructure.jaxrs.HyperLien;

public class RechercheSynchroneStreamParallele extends RechercheSynchroneAbstraite{
	private NomAlgorithme nomAlgo;

	@Override
	public Optional<HyperLien<Livre>> chercher(Livre l, List<HyperLien<Bibliotheque>> bibliotheques, Client client) {
		return bibliotheques.parallelStream().map((b) -> super.rechercheSync(b, l, client)).filter(Optional::isPresent).findAny().orElse(Optional.empty());
	}

	@Override
	public NomAlgorithme nom() {
		return this.nomAlgo;
	}

	

}
