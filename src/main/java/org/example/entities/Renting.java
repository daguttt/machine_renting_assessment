package org.example.entities;

import java.time.LocalDateTime;

public class Renting {
    private Long id;
    private Client client;
    private Machine machine;
    private LocalDateTime startDate;
    private LocalDateTime disabledDate;

    public Renting() {
    }

    public Renting(Client client, Machine machine, LocalDateTime startDate, LocalDateTime disabledDate) {
        this(null, client, machine, startDate, disabledDate);
    }

    public Renting(Long id, Client client, Machine machine, LocalDateTime startDate, LocalDateTime disabledDate) {
        this.id = id;
        this.client = client;
        this.machine = machine;
        this.startDate = startDate;
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

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getDisabledDate() {
        return disabledDate;
    }
}
