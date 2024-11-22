package org.example.controllers;

import org.example.entities.Machine;
import org.example.models.MachinesModel;

import java.util.Optional;

public class MachinesController {
    private final MachinesModel machinesModel;

    public MachinesController(MachinesModel machinesModel) {
        this.machinesModel = machinesModel;
    }

    public Optional<Machine> findOneBySerialNumber(String serialNumber) {
        return this.machinesModel.findOneBySerialNumber(serialNumber);
    }

    public void create(Machine baseMachine) {
        this.machinesModel.create(baseMachine);
    }
}
