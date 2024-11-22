package org.example.commands;

import org.example.Menu;
import org.example.commands.interfaces.MenuCommand;
import org.example.controllers.ClientsController;
import org.example.entities.Client;
import org.example.exceptions.CouldntCreateClientException;
import org.example.utils.InputRequester;

import javax.swing.*;
import java.util.Optional;

public class RegisterClientCommand implements MenuCommand {
    private final ClientsController clientsController;

    public RegisterClientCommand(ClientsController clientsController) {
        this.clientsController = clientsController;
    }


    @Override
    public void execute(Menu menu) {
        String fullName = InputRequester.requestString("Ingresa el nombre completo del cliente");
        String email = InputRequester.requestString("Ingresa el correo electrónico del cliente");

        // Validate email uniqueness
        Optional<Client> foundClient = this.clientsController.findOneByEmail(email);
        if (foundClient.isPresent()) {
            JOptionPane.showMessageDialog(null, "El correo electrónica ya existe. Inténtalo de nuevo", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String phoneNumber = InputRequester.requestString("Ingresa el telefono del cliente");
        String address = InputRequester.requestString("Ingresa la dirección del cliente");

        Client newClient = new Client(fullName, email, phoneNumber, address);
        try {
            this.clientsController.create(newClient);
        } catch (CouldntCreateClientException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JOptionPane.showMessageDialog(null, String.format("¡Has registrado a '%s' correctamente!", fullName));
    }
}
