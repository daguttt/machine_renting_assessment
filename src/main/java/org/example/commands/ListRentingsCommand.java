package org.example.commands;

import org.example.Menu;
import org.example.commands.interfaces.MenuCommand;
import org.example.controllers.RentingsController;
import org.example.entities.Renting;
import org.example.utils.InputRequester;

import javax.swing.*;
import java.util.List;

public class ListRentingsCommand implements MenuCommand {
    private final RentingsController rentingsController;

    public ListRentingsCommand(RentingsController rentingsController) {
        this.rentingsController = rentingsController;
    }

    @Override
    public void execute(Menu menu) {
        // Choose active or disabled
        var optionList = List.of("Activas", "Inactivas");
        int choice = InputRequester.requestAnIndexFrom(optionList, "Selecciona el estado de las rentas que quires ver");
        boolean isDisabled = optionList.get(choice).equals("Inactivas");
        List<Renting> rentingList = this.rentingsController.findAllByStatus(isDisabled);

        if (rentingList.isEmpty()) {
            JOptionPane.showMessageDialog(null, String.format("No hay rentas disponibles '%s' ", optionList.get(choice)), "Informacion", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        var rentingsAsListString = rentingList.stream()
                .map(renting -> String.format(
                        """
                                ID: %d | Fecha de inicio: %Td
                                Cliente -> email: %s - Nombre: %s
                                Maquina -> Modelo: %s - Serial: %s
                                """, renting.getId(), renting.getStartingDate(),
                        renting.getClient().getEmail(), renting.getClient().getFullName(),
                        renting.getMachine().getModel(), renting.getMachine().getSerialNumber()))
                .toList();

        JOptionPane.showMessageDialog(null, String.join("\n", rentingsAsListString));
    }
}
