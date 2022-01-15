package modele;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import static configuration.JAXRS.SOUSCHEMIN_COMPTER;
import static configuration.JAXRS.TYPE_MEDIA;

public interface Bibliotheque extends Repertoire, Archive {
    /**
     * @Requêtes GET http://BIBLIOTHEQUE/bibliotheque/compter
     * @Corps vide
     * @Réponses - 200 Integer : ```size of the bibliotheque```
     *
     */
    @GET
    @Path(SOUSCHEMIN_COMPTER)
    @Produces(TYPE_MEDIA)
    Integer compterLivres();
}
