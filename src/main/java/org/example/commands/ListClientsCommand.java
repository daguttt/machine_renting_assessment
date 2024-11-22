package org.example.commands;

import org.example.Menu;
import org.example.commands.interfaces.MenuCommand;
import org.example.controllers.ClientsController;
import org.example.entities.Client;

import javax.swing.*;
import java.util.List;

public class ListClientsCommand implements MenuCommand {
    private final ClientsController clientsController;

    public ListClientsCommand(ClientsController clientsController) {
        this.clientsController = clientsController;
    }

    @Override
    public void execute(Menu menu) {
        List<Client> clientsList = this.clientsController.findAll();

        if (clientsList.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay clientes registrados", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        List<String> clientsListString = clientsList.stream().map(Client::toString).toList();
        JOptionPane.showMessageDialog(null, String.join("\n", clientsListString), "Clientes", JOptionPane.INFORMATION_MESSAGE);
    }
}
