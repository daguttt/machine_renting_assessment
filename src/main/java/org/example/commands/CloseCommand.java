package org.example.commands;

import org.example.Menu;
import org.example.commands.interfaces.MenuCommand;

import javax.swing.*;

public class CloseCommand implements MenuCommand {
    @Override
    public void execute(Menu menu) {
        menu.close();
        JOptionPane.showMessageDialog(null, "¡Hasta luego!");
    }
}
