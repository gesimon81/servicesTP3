# servicesTP3

- Repertoire {

	@PUT
	@Produces(TYPE_MEDIA)
	@Consumes(TYPE_MEDIA)
	@ReponsesPUTOption
	// Requête (méthode http + url) : GET http://localhost:8081/PORTAIL/portail
	// Corps : 
	<livre>
		<titre>Services0.0</titre>
	</livre>
	// Réponses (à spécifier par code) :
	// - code : REQUEST FAILED (500) <!-- il n'y a pas encore d'algorithme de recherche implémenté -->
	Optional<HyperLien<Livre>> chercher(Livre l);


	@PUT
	@ReponsesPUTOption
	@Path(JAXRS.SOUSCHEMIN_ASYNC)
	@Consumes(JAXRS.TYPE_MEDIA)
	@Produces(JAXRS.TYPE_MEDIA)
	// Requête (méthode http + url) : 
	// Corps : 
	// Réponses (à spécifier par code) :
	// - code : 
	Future<Optional<HyperLien<Livre>>> chercherAsynchrone(Livre l, @Suspended final AsyncResponse ar);

	@GET
	@Path(SOUSCHEMIN_CATALOGUE)
	@Produces(TYPE_MEDIA)
	// Requête (méthode http + url) : PUT http://localhost:8081/PORTAIL/portail/catalogue
	// Corps : rien
	// Réponses (à spécifier par code) :
	// - code : 
	<liste>
		<hyperlien uri="http://localhost:8081/bib{x}/BIBLIO/{y}"/> 
		<!-- {x} est l'id de la bibliothèque; {y} est l'id du livre -->
	</liste>
	HyperLiens<Livre> repertorier();

- Archive 
	@Path("{id}")
	@ReponsesGETNullEn404
	// Adresse de la sous-ressource : 
	// Requête sur la sous-ressource (méthode http + url) : 
	// Corps : 
	// Réponses (à spécifier par code) :
	// - code : 
	Livre sousRessource(@PathParam("id") IdentifiantLivre id) ;

	@Path("{id}")
	@GET 
	@Produces(JAXRS.TYPE_MEDIA)
	@ReponsesGETNullEn404
	// Requête (méthode http + url) : 
	// Corps : 
	// Réponses (à spécifier par code) :
	// - code : 
	Livre getRepresentation(@PathParam("id") IdentifiantLivre id);

	@POST
	@ReponsesPOSTEnCreated
	@Consumes(JAXRS.TYPE_MEDIA)
	@Produces(JAXRS.TYPE_MEDIA)
	// Requête (méthode http + url) : 
	// Corps : 
	// Réponses (à spécifier par code) :
	// - code : 
	HyperLien<Livre> ajouter(Livre l);
}

- AdminAlgo
	@PUT
	@Path(JAXRS.SOUSCHEMIN_ALGO_RECHERCHE)
	@Consumes(JAXRS.TYPE_MEDIA)
	// Requête (méthode http + url) : 
	// Corps : 
	// Réponses (à spécifier par code) :
	// - code : 
	void changerAlgorithmeRecherche(NomAlgorithme algo);
