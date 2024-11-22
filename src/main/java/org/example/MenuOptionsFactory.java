package org.example;

import org.example.commands.CloseCommand;
import org.example.commands.RegisterClientCommand;
import org.example.controllers.ClientsController;

import java.util.List;

public class MenuOptionsFactory {

    private final ClientsController clientsController;

    public MenuOptionsFactory(ClientsController clientsController) {
        this.clientsController = clientsController;
    }


    public List<MenuOption> getAdminMenuCommands() {
        return List.of(
                new MenuOption("Registrar Cliente", new RegisterClientCommand(clientsController)),
                new MenuOption("Salir", new CloseCommand())
        );
    }
}
