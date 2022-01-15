package modele;

import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;

import configuration.JAXRS;

public interface AdminAlgo {
	/**
	 * @Requêtes (méthode http + url) : PUT http://PORTAIL/portail/admin/recherche
	 * @Corps un livre : ```xml <algo><nom>{t}</nom></algo> <!-- {t} est un nom
	 *        d'algorithme choisi arbitrairement -->```
	 * @Réponses - 200 vide
	 * 
	 */
	@PUT
	@Path(JAXRS.SOUSCHEMIN_ALGO_RECHERCHE)
	@Consumes(JAXRS.TYPE_MEDIA)
	void changerAlgorithmeRecherche(NomAlgorithme algo);
}
