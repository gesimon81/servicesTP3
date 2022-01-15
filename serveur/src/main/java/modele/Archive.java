package modele;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import configuration.JAXRS;
import infrastructure.jaxrs.HyperLien;
import infrastructure.jaxrs.annotations.ReponsesGETNullEn404;
import infrastructure.jaxrs.annotations.ReponsesPOSTEnCreated;

public interface Archive {

	/**
	 * @Adresse bibliotheque/{y} {y} est un entier naturel répresentant l'id de la
	 *          ressource (livre),
	 * @Requêtes (méthode http + url) : GET http://BIBLIOTHEQUE/bibliotheque/{id}
	 * @Corps Vide
	 * @Réponses - 404 livre non trouvé : {uri}
	 * 
	 *           - 200 livre trouvé : ```xml <livre><titre>{t}</titre></livre>```
	 * 
	 */
	@Path("{id}")
	@ReponsesGETNullEn404
	Livre sousRessource(@PathParam("id") IdentifiantLivre id);

	/**
	 * @Requêtes (méthode http + url) : GET http://BIBLIOTHEQUE/bibliotheque/{id}
	 * @Corps Vide
	 * @Réponses - 404 livre non trouvé : {uri}
	 * 
	 *           - 200 livre trouvé : ```xml <livre><titre>{t}</titre></livre>```
	 * 
	 */
	@Path("{id}")
	@GET
	@Produces(JAXRS.TYPE_MEDIA)
	@ReponsesGETNullEn404
	Livre getRepresentation(@PathParam("id") IdentifiantLivre id);

	/**
	 * @Requêtes (méthode http + url) : POST http://BIBLIOTHEQUE/bibliotheque
	 * @Corps un livre : ```xml <livre><titre>{t}</titre></livre>```
	 * @Réponses - 201 livre créé : Entête Location avec l'hyperlien vers la
	 *           nouvelle sous-ressource, corps de réponse vide
	 * 
	 */
	@POST
	@ReponsesPOSTEnCreated
	@Consumes(JAXRS.TYPE_MEDIA)
	@Produces(JAXRS.TYPE_MEDIA)
	HyperLien<Livre> ajouter(Livre l);
}
