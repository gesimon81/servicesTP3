package modele;

import java.util.Optional;
import java.util.concurrent.Future;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;

import configuration.JAXRS;
import infrastructure.jaxrs.HyperLien;
import infrastructure.jaxrs.HyperLiens;
import infrastructure.jaxrs.annotations.ReponsesPUTOption;

import static configuration.JAXRS.*;

public interface Repertoire {
	/**
	 * @Requêtes (méthode http + url) : PUT http://PORTAIL/portail ou PUT
	 *           http://BIBLIOTHEQUE/bibliotheque
	 * @Corps ```xml <livre><titre>{t}</titre></livre><!-- {t} est un titre choisi
	 *        arbitrairement --> ```
	 * @Réponses - 500 algorithme non implémenté : il n'y a pas encore d'algorithme
	 *           de recherche implémenté
	 * 
	 *           - 404 livre non trouvé : {uri} - {description du livre}
	 * 
	 *           - 200 livre trouvé : ```xml <livre><titre>{t}</titre></livre>```
	 * 
	 */
	@PUT
	@Produces(TYPE_MEDIA)
	@Consumes(TYPE_MEDIA)
	@ReponsesPUTOption
	Optional<HyperLien<Livre>> chercher(Livre l);

	/**
	 * @Requêtes (méthode http + url) : PUT http://PORTAIL/portail/async ou PUT
	 *           http://BIBLIOTHEQUE/bibliotheque/async
	 * @Corps ```xml <livre><titre>{t}</titre></livre><!-- {t} est un titre choisi
	 *        arbitrairement --> ```
	 * @Réponses - 500 algorithme non implémenté : il n'y a pas encore d'algorithme
	 *           de recherche implémenté
	 * 
	 *           - 404 livre non trouvé : {uri} - {description du livre}
	 * 
	 *           - 200 livre trouvé : ```xml <livre><titre>{t}</titre></livre>```
	 * 
	 */
	@PUT
	@ReponsesPUTOption
	@Path(JAXRS.SOUSCHEMIN_ASYNC)
	@Consumes(JAXRS.TYPE_MEDIA)
	@Produces(JAXRS.TYPE_MEDIA)
	Future<Optional<HyperLien<Livre>>> chercherAsynchrone(Livre l, @Suspended final AsyncResponse ar);

	/**
	 * @Requêtes (méthode http + url) : GET http://PORTAIL/portail/catalogue ou GET
	 *           http://BIBLIOTHEQUE/bibliotheque/catalogue
	 * @Corps vide
	 * @Réponses - 200 liste des livres : ```xml <liste><hyperlien uri=
	 *           "http://localhost:8081/bib{x}/BIBLIO/{y}"/></liste> <!-- {x} est
	 *           l'id de la bibliothèque; {y} est l'id de la ressource (livre) -->
	 *           ```
	 * 
	 */
	@GET
	@Path(SOUSCHEMIN_CATALOGUE)
	@Produces(TYPE_MEDIA)
	HyperLiens<Livre> repertorier();

}
