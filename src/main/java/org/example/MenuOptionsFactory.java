package org.example;

import org.example.commands.CloseAllCommand;

import java.util.List;

public class MenuOptionsFactory {

    public List<MenuOption> getAdminMenuCommands() {
        return List.of(
                new MenuOption("Salir", new CloseAllCommand())
        );
    }
}
