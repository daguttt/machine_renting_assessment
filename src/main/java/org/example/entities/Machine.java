package org.example.entities;

import org.example.enums.MachineStatus;

public class Machine {
    private Long id;
    private String model;
    private String serialNumber;
    private MachineStatus status;

    public Machine() {
    }

    public Machine(String model, String serialNumber) {
        this(null, model, serialNumber, MachineStatus.AVAILABLE);
    }

    public Machine(String model, String serialNumber, MachineStatus status) {
        this(null, model, serialNumber, status);
    }

    public Machine(Long id, String model, String serialNumber, MachineStatus status) {
        this.id = id;
        this.model = model;
        this.serialNumber = serialNumber;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public String getModel() {
        return model;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public MachineStatus getStatus() {
        return status;
    }

    // Setters

    public void setId(Long id) {
        this.id = id;
    }

    public void setStatus(MachineStatus status) {
        this.status = status;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    @Override
    public String toString() {
        return String.format("%d - %s - %s - %s", id, model, serialNumber, status.name());
    }
}
