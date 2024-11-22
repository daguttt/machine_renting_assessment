package org.example;

import org.example.commands.*;
import org.example.controllers.ClientsController;
import org.example.controllers.MachinesController;
import org.example.controllers.RentingsController;

import java.util.List;

public class MenuOptionsFactory {

    private final ClientsController clientsController;
    private final MachinesController machinesController;
    private final RentingsController rentingsController;

    public MenuOptionsFactory(ClientsController clientsController, MachinesController machinesController, RentingsController rentingsController) {
        this.clientsController = clientsController;
        this.machinesController = machinesController;
        this.rentingsController = rentingsController;
    }


    public List<MenuOption> getAdminMenuCommands() {
        return List.of(
                new MenuOption("Registrar Cliente", new RegisterClientCommand(this.clientsController)),
                new MenuOption("Listar Clientes", new ListClientsCommand(this.clientsController)),
                new MenuOption("Registrar maquina", new RegisterMachineCommand(this.machinesController)),
                new MenuOption("Listar maquinas", new ListMachinesCommand(this.machinesController)),
                new MenuOption("Registrar alquiler", new RegisterMachineRentingCommand(this.machinesController, this.rentingsController, this.clientsController)),
                new MenuOption("Listar alquileres", new ListRentingsCommand(this.rentingsController)),
                new MenuOption("Salir", new CloseCommand())
        );
    }
}
