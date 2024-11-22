package org.example.controllers;

import org.example.entities.Renting;
import org.example.models.RentingsModel;

import java.util.List;

public class RentingsController {
    private final RentingsModel rentingsModel;

    public RentingsController(RentingsModel rentingsModel) {
        this.rentingsModel = rentingsModel;
    }


    public void create(Renting baseRenting) {
        this.rentingsModel.create(baseRenting);
    }

    public boolean update(Renting baseRenting) {
        return this.rentingsModel.update(baseRenting);
    }

    public List<Renting> findAllByStatus(boolean isDisabled) {
        return this.rentingsModel.findAllByStatus(isDisabled);
    }
}
