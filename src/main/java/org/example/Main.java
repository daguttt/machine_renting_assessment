package org.example;

import org.example.controllers.ClientsController;
import org.example.controllers.MachinesController;
import org.example.models.ClientsModel;
import org.example.models.MachinesModel;
import org.example.persistence.Database;

public class Main {
    public static void main(String[] args) {
        System.out.println("***********************************************************");
        System.out.println("Machine Renting Assessment");
        System.out.println("***********************************************************");

        String host = args[0];
        String port = args[1];
        String dbName = args[2];
        String dbUser = args[3];
        String dbPassword = args[4];

        // -****************************
        // Dependency Injection
        var database = new Database(host, port, dbName, dbUser, dbPassword);
        database.testConnection();

        // Models
        var clientsModel = new ClientsModel(database);
        var machinesModel = new MachinesModel(database);


        // Controller
        var clientsController = new ClientsController(clientsModel);
        var machinesController = new MachinesController(machinesModel);


        // -****************************

        // Menu
        var menuOptionsFactory = new MenuOptionsFactory(
                clientsController,
                machinesController
        );
        Menu adminMenu = new Menu(menuOptionsFactory.getAdminMenuCommands());
        adminMenu.open();
    }
}