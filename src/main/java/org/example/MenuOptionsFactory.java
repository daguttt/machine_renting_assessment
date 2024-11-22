package org.example;

import org.example.commands.CloseCommand;
import org.example.commands.ListClientsCommand;
import org.example.commands.RegisterClientCommand;
import org.example.commands.RegisterMachineCommand;
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
                new MenuOption("Salir", new CloseCommand())
        );
    }
}
