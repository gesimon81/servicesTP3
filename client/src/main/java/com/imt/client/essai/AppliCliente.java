package com.imt.client.essai;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientConfig;

import java.util.ArrayList;
import java.util.Random;


public class AppliCliente {

    public static final String BASE_URL = "http://localhost:8081/PortailServeur2/portail";
    public static final String[] ALGOS = {
            "recherche sync seq",
            "recherche sync multi",
            "recherche sync stream 8",
            "recherche sync stream rx",
            "recherche async seq",
            "recherche async multi",
            "recherche async stream 8",
            "recherche async stream rx"
    };

    public static final int NB_REPETITION = 10;
    public static final int NB_LIVRES = 1000; // TODO: appeler bib0/bibliotheque/compter quand fonctionnel
    public static final int NB_BIBLIOTHEQUES = 10;

    public static Client clientJAXRS() {
        ClientConfig config = new ClientConfig();
        return ClientBuilder.newClient(config);
    }

    public static ArrayList<String> getCatalogues() {
        ArrayList<String> catalogues = new ArrayList<String>();

        WebTarget target = clientJAXRS().target(BASE_URL + "/catalogue");
        Response response = target.request().get();

        System.out.println(response);

        if (response.getStatus() == Response.Status.OK.getStatusCode()) {
            // response.getEntity();
        }

        return catalogues;
    }

    public static void TestAlgorithm(String algorithm) {

        // Swap research algorithm
        Response update = clientJAXRS().target(BASE_URL + "/admin/recherche").request().put(Entity.xml("<algo><nom>" + algorithm + "</nom></algo>"));

        long start = System.nanoTime();

        // Search for books
        for (int i = 0; i < NB_REPETITION; i++) {
            long idBib = Math.round(Math.random() * NB_BIBLIOTHEQUES);
            long idLivre = Math.round(Math.random() * NB_LIVRES);
            Response response = clientJAXRS().target(BASE_URL).request().put(Entity.xml("<livre><titre>Services" + idBib + "." + idLivre + "</titre></livre>"));
            //System.out.println(response + " " + "<livre><titre>Services" + idBib + "." + idLivre + "</titre></livre>");
        }

        long end = System.nanoTime();

        long duration = (end - start) / 1000000;

        System.out.println(" * ALGO [" + algorithm + "]");
        System.out.println("        - total duration: " + duration + " ms");
        System.out.println("        - average duration: " + (duration / NB_REPETITION) + " ms");

        // System.out.println(response.readEntity(String.class));
    }

    public static void main(String[] args) {

        ArrayList<String> catalogues = getCatalogues();

        System.out.println("=== BENCHMARKING ==============================================");
        System.out.println(NB_REPETITION + " searchs for random books in " + NB_LIVRES);

        for (String algo : ALGOS) {
            TestAlgorithm(algo);
        }
    }
}