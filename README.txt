# servicesTP3

- Repertoire {

	@PUT
	@Produces(TYPE_MEDIA)
	@Consumes(TYPE_MEDIA)
	@ReponsesPUTOption
	// Requête (méthode http + url) : PUT http://localhost:8081/PORTAIL/portail
	// Corps : 
	<livre>
		<titre>{t}</titre> <!-- {t} est un titre choisi arbitrairement -->
	</livre>
	// Réponses (à spécifier par code) :
	// - code : 500 (Request failed)
	Content-Type	text/html;charset=ISO-8859-1
	Connection	close
	Content-Length	...	
	REQUEST FAILED <!-- il n'y a pas encore d'algorithme de recherche implémenté -->
	Optional<HyperLien<Livre>> chercher(Livre l);


	@PUT
	@ReponsesPUTOption
	@Path(JAXRS.SOUSCHEMIN_ASYNC)
	@Consumes(JAXRS.TYPE_MEDIA)
	@Produces(JAXRS.TYPE_MEDIA)
	// Requête (méthode http + url) : PUT http://localhost:8081/PORTAIL/portail/async
	// Corps : 
	<livre>
		<titre>Services0.0</titre>
	</livre>
	// Réponses (à spécifier par code) :
	// - code : 500 (Request failed)
	Content-Type	text/html;charset=ISO-8859-1
	Connection	close
	Content-Length	...
	REQUEST FAILED <!-- il n'y a pas encore d'algorithme de recherche implémenté -->
	Future<Optional<HyperLien<Livre>>> chercherAsynchrone(Livre l, @Suspended final AsyncResponse ar);

	@GET
	@Path(SOUSCHEMIN_CATALOGUE)
	@Produces(TYPE_MEDIA)
	// Requête (méthode http + url) : PUT http://localhost:8081/PORTAIL/portail/catalogue
	// Corps : rien
	// Réponses (à spécifier par code) :
	// - code :  200 (OK)
	Content-Type	application/xml
	Content-Length	...
	<liste>
		<hyperlien uri="http://localhost:8081/bib{x}/BIBLIO/{y}"/> <!-- {x} est l'id de la bibliothèque; {y} est l'id de la ressource (livre) -->
	</liste>
	HyperLiens<Livre> repertorier();

- Archive 
	@Path("{id}")
	@ReponsesGETNullEn404
	// Adresse de la sous-ressource : bib{x}/BIBLIO/{y} <!-- {x} est l'id de la bibliothèque; {y} est l'id de la ressource (livre) -->
	// Requête sur la sous-ressource (méthode http + url) : ???
	// Corps : 
	// Réponses (à spécifier par code) :
	// - code : 
	Livre sousRessource(@PathParam("id") IdentifiantLivre id) ;

	@Path("{id}")
	@GET 
	@Produces(JAXRS.TYPE_MEDIA)
	@ReponsesGETNullEn404
	// Requête (méthode http + url) :  GET http://localhost:8081/bib{x}/BIBLIO/{y} <!-- {x} est l'id de la bibliothèque; {y} est l'id de la ressource (livre) -->
	// Corps : rien
	// Réponses (à spécifier par code) :
	// - code : 200 (OK)
	Content-Type	application/xml
	Content-Length	96
	<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
	<livre>
		<titre>{t}</titre> <!-- {t} est le titre de la sous-ressource à cette adresse -->
	</livre>
	Livre getRepresentation(@PathParam("id") IdentifiantLivre id);

	@POST
	@ReponsesPOSTEnCreated
	@Consumes(JAXRS.TYPE_MEDIA)
	@Produces(JAXRS.TYPE_MEDIA)
	// Requête (méthode http + url) : 
	// Corps : 
	// Réponses (à spécifier par code) :
	// - code : 201 (created)
	Location	http://localhost:8090/bib0/bibliotheque/10
	Content-Length	0
	HyperLien<Livre> ajouter(Livre l);
}

- AdminAlgo
	@PUT
	@Path(JAXRS.SOUSCHEMIN_ALGO_RECHERCHE)
	@Consumes(JAXRS.TYPE_MEDIA)
	// Requête (méthode http + url) : 
	// Corps : 
	<algo>
		<nom>{t}</nom> <!-- {t} est un titre choisi arbitrairement -->
	</algo>
	// Réponses (à spécifier par code) :
	// - code : 500 (Request Failed)
	Content-Type	text/html;charset=ISO-8859-1
	Connection	close
	Content-Length	1031
	void changerAlgorithmeRecherche(NomAlgorithme algo);
