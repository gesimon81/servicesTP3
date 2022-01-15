package com.imt.client.essai;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.glassfish.jersey.client.ClientConfig;

public class AppliCliente {
    public static Client clientJAXRS() {
        ClientConfig config = new ClientConfig();
        return ClientBuilder.newClient(config);
    }
    public static void main(String[] args) {
        WebTarget cible = clientJAXRS().target("http://localhost:8080/");
    }
}