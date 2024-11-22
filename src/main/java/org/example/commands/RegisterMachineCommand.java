package org.example.commands;

import org.example.Menu;
import org.example.commands.interfaces.MenuCommand;
import org.example.controllers.ClientsController;
import org.example.controllers.MachinesController;
import org.example.entities.Client;
import org.example.entities.Machine;
import org.example.enums.MachineStatus;
import org.example.exceptions.CouldntCreateClientException;
import org.example.utils.InputRequester;

import javax.swing.*;
import java.util.Optional;

public class RegisterMachineCommand implements MenuCommand {
    private final MachinesController machinesController;

    public RegisterMachineCommand(MachinesController machinesController) {
        this.machinesController = machinesController;
    }


    @Override
    public void execute(Menu menu) {
        String model = InputRequester.requestString("Ingresa el modelo de la maquina");
        String serialNumber = InputRequester.requestString("Ingresa el número serial de la maquina (es único)");

        // Validate serialNumber uniqueness
        Optional<Machine> foundMachine = this.machinesController.findOneBySerialNumber(serialNumber);
        if (foundMachine.isPresent()) {
            JOptionPane.showMessageDialog(null, "La maquina ya existe. Inténtalo de nuevo", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Machine newMachine = new Machine(model, serialNumber);

        try {
            this.machinesController.create(newMachine);
        } catch (CouldntCreateClientException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JOptionPane.showMessageDialog(null, String.format("¡Has registrado la maquina '%s' con el serial '%s' correctamente!", model, serialNumber ));
    }
}
