package org.example;

import org.example.commands.*;
import org.example.controllers.ClientsController;
import org.example.controllers.MachinesController;

import java.util.List;

public class MenuOptionsFactory {

    private final ClientsController clientsController;
    private final MachinesController machinesController;

    public MenuOptionsFactory(ClientsController clientsController, MachinesController machinesController) {
        this.clientsController = clientsController;
        this.machinesController = machinesController;
    }


    public List<MenuOption> getAdminMenuCommands() {
        return List.of(
                new MenuOption("Registrar Cliente", new RegisterClientCommand(this.clientsController)),
                new MenuOption("Listar Clientes", new ListClientsCommand(this.clientsController)),
                new MenuOption("Registrar maquina", new RegisterMachineCommand(this.machinesController)),
                new MenuOption("Listar maquinas", new ListMachinesCommand(this.machinesController)),
                new MenuOption("Salir", new CloseCommand())
        );
    }
}
