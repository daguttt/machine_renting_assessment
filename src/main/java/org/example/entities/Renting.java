package org.example.entities;

import java.sql.Timestamp;

public class Renting {
    private Long id;
    private Client client;
    private Machine machine;
    private Timestamp startingDate;
    private Timestamp disabledDate;

    public Renting() {
    }

    public Renting(Client client, Machine machine, Timestamp startingDate, Timestamp disabledDate) {
        this(null, client, machine, startingDate, disabledDate);
    }

    public Renting(Long id, Client client, Machine machine, Timestamp startingDate, Timestamp disabledDate) {
        this.id = id;
        this.client = client;
        this.machine = machine;
        this.startingDate = startingDate;
        this.disabledDate = disabledDate;
    }

    public Long getId() {
        return id;
    }

    public Client getClient() {
        return client;
    }

    public Machine getMachine() {
        return machine;
    }

    public Timestamp getStartingDate() {
        return startingDate;
    }

    public Timestamp getDisabledDate() {
        return disabledDate;
    }

    // Setters

    public void setId(Long id) {
        this.id = id;
    }

    public void setDisabledDate(Timestamp disabledDate) {
        this.disabledDate = disabledDate;
    }
}
