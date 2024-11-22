package org.example;

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


        // Controller


        // -****************************

        // Menu
        var menuOptionsFactory = new MenuOptionsFactory();
        Menu adminMenu = new Menu(menuOptionsFactory.getAdminMenuCommands());
        adminMenu.open();
    }
}