package org.example.controllers;

import org.example.entities.Client;
import org.example.models.ClientsModel;

import java.util.Optional;

public class ClientsController {
    private final ClientsModel clientsModel;

    public ClientsController(ClientsModel clientsModel) {
        this.clientsModel = clientsModel;
    }

    public Optional<Client> findOneByEmail(String email) {
        return this.clientsModel.findOneByEmail(email);
    }

    public Client create(Client baseClient) {
        return this.clientsModel.create(baseClient);
    }
}
