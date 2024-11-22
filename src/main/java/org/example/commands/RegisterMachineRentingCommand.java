package org.example.commands;

import org.example.Menu;
import org.example.commands.interfaces.MenuCommand;
import org.example.controllers.ClientsController;
import org.example.controllers.MachinesController;
import org.example.controllers.RentingsController;
import org.example.entities.Client;
import org.example.entities.Machine;
import org.example.entities.Renting;
import org.example.enums.MachineStatus;
import org.example.utils.InputRequester;

import javax.swing.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class RegisterMachineRentingCommand implements MenuCommand {
    private final MachinesController machinesController;
    private final RentingsController rentingsController;
    private final ClientsController clientsController;

    public RegisterMachineRentingCommand(MachinesController machinesController, RentingsController rentingsController, ClientsController clientsController) {
        this.machinesController = machinesController;
        this.rentingsController = rentingsController;
        this.clientsController = clientsController;
    }

    @Override
    public void execute(Menu menu) {
        // List active machines
        List<Machine> activeMachines = this.machinesController.findAllActive();

        if (activeMachines.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay maquinas disponibles", "Informacion", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Get the client
        String clientEmail = InputRequester.requestString("Ingresa el correo electrónico del cliente");
        Optional<Client> clientOptional = this.clientsController.findOneByEmail(clientEmail);

        if (clientOptional.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No existe un cliente registrado con ese correo electrónica", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Client client = clientOptional.get();

        // Select a machine
        List<String> machinesAsStringList = activeMachines.stream()
                .map(m -> String.format("%s - %s - %s", m.getModel(), m.getSerialNumber(), m.getStatus().name()))
                .toList();
        int machineIndex = InputRequester.requestAnIndexFrom(machinesAsStringList, "Selecciona el número de la maquina a rentar");

        Machine machineToRent = activeMachines.get(machineIndex);

        // Prompt return date
        LocalDateTime disabledDate;

        while (true) {
            disabledDate = InputRequester.requestLocalDateTime("Ingresa la fecha y hora de devolución de la maquina").orElseThrow();
            if (LocalDateTime.now().isBefore(disabledDate)) break;

            JOptionPane.showMessageDialog(null, "La fecha de devolución debe ser en el futuro. Inténtalo de nuevo", "Error", JOptionPane.ERROR_MESSAGE);
        }

        if (disabledDate.isBefore(LocalDateTime.now())) {
            JOptionPane.showMessageDialog(null, "La fecha de devolución debe ser posterior a la actual", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Save renting
        Renting newRenting = new Renting(
                client,
                machineToRent,
                Timestamp.valueOf(LocalDateTime.now()),
                Timestamp.valueOf(disabledDate)
        );

        this.rentingsController.create(newRenting);

        // Change machine status
        machineToRent.setStatus(MachineStatus.RENTED);
        boolean couldUpdateMachine = this.machinesController.update(machineToRent);

        if (!couldUpdateMachine) {
            JOptionPane.showMessageDialog(null, "Error al reservar la maquina.", "Error", JOptionPane.ERROR_MESSAGE);
            newRenting.setDisabledDate(Timestamp.valueOf(disabledDate));
            this.rentingsController.update(newRenting);
            return;
        }

        JOptionPane.showMessageDialog(null, "La maquina ha sido rentada correctamente", "Informacion", JOptionPane.INFORMATION_MESSAGE);

    }
}
