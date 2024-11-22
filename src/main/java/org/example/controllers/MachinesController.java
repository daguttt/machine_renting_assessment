package org.example.controllers;

import org.example.entities.Machine;
import org.example.models.MachinesModel;

import java.util.Arrays;
import java.util.List;
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

    public int count() {
        return this.machinesModel.count();
    }

    public List<Machine> findAll(int page, int pageSize) {
        return this.machinesModel.findAll(page, pageSize);
    }
}
