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

    public static final String BASE_URL = "http://localhost:8081";
    public static final String[] ALGOS = {
            "recherche sync seq",
            "recherche sync multi",
            "recherche sync stream rx",
            "recherche sync stream 8",
            "recherche async seq",
            "recherche async multi",
            "recherche async stream 8",
            "recherche async stream rx"
    };

    public static final int NB_REPETITION = 1000;
    public static final int NB_LIVRES = 20000; // TODO: appeler bib0/bibliotheque/compter quand fonctionnel

    public static Client clientJAXRS() {
        ClientConfig config = new ClientConfig();
        return ClientBuilder.newClient(config);
    }

    public static ArrayList<String> getCatalogues() {
        ArrayList<String> catalogues = new ArrayList<String>();

        WebTarget target = clientJAXRS().target(BASE_URL + "/PortailServeur2/portail/catalogue");
        Response response = target.request().get();

        System.out.println(response);

        if (response.getStatus() == Response.Status.OK.getStatusCode()) {
            // response.getEntity();
        }

        return catalogues;
    }

    public static void TestAlgorithm(String algorithm) {

        // Swap research algorithm
        Response update = clientJAXRS().target(BASE_URL + "/PortailServeur2/portail/admin/recherche").request().put(Entity.xml("<algo><nom>" + algorithm + "</nom></algo>"));

        long start = System.nanoTime();

        // Search for books
        for (int i = 0; i < NB_REPETITION; i++) {
            long idLivre = Math.round(Math.random() * NB_LIVRES);
            clientJAXRS().target("http://localhost:8090/bib0/bibliotheque/" + idLivre).request().get();
        }

        long end = System.nanoTime();

        long duration = (end - start) / 1000000;

        System.out.println(" * ALGO [" + algorithm + "] - average duration: " + (duration / NB_REPETITION) + " ms");

        // System.out.println(response.readEntity(String.class));
    }

    public static void main(String[] args) {

        ArrayList<String> catalogues = getCatalogues();

        System.out.println("=== BENCHMARKING ==============================================");
        System.out.println(NB_REPETITION + " searchs for randoms books in " + NB_LIVRES);

        for (String algo : ALGOS) {
            TestAlgorithm(algo);
        }
    }
}