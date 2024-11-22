package org.example.commands;

import org.example.Menu;
import org.example.commands.interfaces.MenuCommand;
import org.example.controllers.MachinesController;
import org.example.entities.Machine;
import org.example.utils.InputRequester;

import javax.swing.*;
import java.util.List;

public class ListMachinesCommand implements MenuCommand {
    private final MachinesController machinesController;

    public ListMachinesCommand(MachinesController machinesController) {
        this.machinesController = machinesController;
    }

    @Override
    public void execute(Menu menu) {
        final int initialPage = 1;
        final int machinesPerPage = 5;
        int totalMachines = this.machinesController.count();

        if (totalMachines == 0) {
            JOptionPane.showMessageDialog(null, "No hay maquinas registradas", "Informacion", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        int totalPages = (int) Math.ceil((double) totalMachines / machinesPerPage);

        boolean showingMachines = true;
        int page = initialPage;
        while (showingMachines){

            var machineListAsStringList =  this.machinesController.findAll(page, machinesPerPage).stream().map(Machine::toString).toList();

            String message = String.format("""
                    Listado de maquinas:
                    
                    %s
                    
                    Pagina actual: %d
                    Total de paginas: %d
                    """, String.join("\n", machineListAsStringList), initialPage, totalPages);

            int choice = InputRequester.requestAnIndexFrom(List.of("Anterior", "Siguiente", "Cerrar"), message);
            switch (choice) {
                case 0 -> {
                    if (page < totalPages) page++;
                }
                case 1 -> {
                    if (page > 1) page--;
                }
                case 2 -> showingMachines = false;
            }
        }
    }

}
