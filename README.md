
# Service-oriented - TP3 - Recherche efficace

Groupe TP : Geraud SIMON, Raphael PAINTER, Mael LHOUTELLIER, Simon SASSI


## En étudiant le code des interfaces **Bibliotheque** et **Portail** (et de leurs interfaces parentes) ainsi que celui de leurs implémentations, déterminer l'ensemble des requêtes **http** acceptées par ces services. On supposera que la bibliothèque est déployée à l'adresse **BIBLIO** et le portail à l'adresse **PORTAIL**. Placer votre réponse dans un fichier **readme**.

- Répertoire 

```java
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
```

- Archive 
```java
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
```

- AdminAlgo
```java
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
```

## En étudiant les interfaces **NomAlgorithme** et **Livre**, donner le schéma et un exemple de données XML pour un nom d'algorithme et un livre. Répondre dans le **readme**.

- NomAlgorithme

```xml
<algo>
 <nom>Lineaire</nom>
</algo>
```
```xml
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<xs:schema version="1.0" xmlns:xs="http://www.w3.org/2001/XMLSchema">

  <xs:element name="livre" type="Livre"/>

  <xs:complexType name="Livre">
    <xs:sequence>
      <xs:element name="titre" type="xs:string" minOccurs="0"/>
    </xs:sequence>
  </xs:complexType>
</xs:schema>
```


- Livre
```xml
<livre>
 <titre>La zone du dehors</titre>
</livre>
```
```xml
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<xs:schema version="1.0" xmlns:xs="http://www.w3.org/2001/XMLSchema">

  <xs:element name="algo" type="NomAlgorithme"/>

  <xs:complexType name="NomAlgorithme">
    <xs:sequence/>
    <xs:attribute name="nom" type="xs:string"/>
  </xs:complexType>
</xs:schema>
```

```
=== BENCHMARKING ==============================================
10 searchs for random books in 1000
SYNC
 * ALGO [recherche sync seq]
        - total duration: 81502 ms
        - average duration: 8150 ms
 * ALGO [recherche sync multi]
        - total duration: 11907 ms
        - average duration: 1190 ms
 * ALGO [recherche sync stream 8]
        - total duration: 11144 ms
        - average duration: 1114 ms
 * ALGO [recherche sync stream rx]
        - total duration: 13992 ms
        - average duration: 1399 ms
ASYNC
 * ALGO [recherche async seq]
        - total duration: 15391 ms
        - average duration: 1539 ms
 * ALGO [recherche async multi]
        - total duration: 3507 ms
        - average duration: 350 ms
 * ALGO [recherche async stream 8]
        - total duration: 15149 ms
        - average duration: 1514 ms
 * ALGO [recherche async stream rx]
        - total duration: 3795 ms
        - average duration: 379 ms

Process finished with exit code 0
```
