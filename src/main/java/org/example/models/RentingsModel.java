package org.example.models;

import org.example.entities.Client;
import org.example.entities.Machine;
import org.example.entities.Renting;
import org.example.enums.MachineStatus;
import org.example.exceptions.CouldnCreateRentingException;
import org.example.persistence.Database;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RentingsModel {
    private final Database database;

    public RentingsModel(Database database) {
        this.database = database;
    }

    public void create(Renting baseRenting) {
        var sql = """
                INSERT INTO rentings (starting_date, disabled_at, client_id, machine_id)\s
                    VALUES (?, ?, ?, ?);
                """;

        try (
                var connection = database.openConnection();
                var statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS))
        {
            statement.setTimestamp(1, baseRenting.getStartingDate());
            statement.setTimestamp(2, baseRenting.getDisabledDate());
            statement.setLong(3, baseRenting.getClient().getId());
            statement.setLong(4, baseRenting.getMachine().getId());

            statement.execute();

            var resultSet = statement.getGeneratedKeys();

            if (resultSet.next()) {
                long givenRentingId = resultSet.getLong(1);
                baseRenting.setId(givenRentingId);
            } else throw new CouldnCreateRentingException("Couldn't register machine renting");

            resultSet.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public boolean update(Renting rentingToUpdate) {
        var sql = """
                UPDATE LOW_PRIORITY rentings\s
                    SET\s
                        disabled_at = ?,\s
                    WHERE id = ?;
                """;
        try (
                var connection = database.openConnection();
                var statement = connection.prepareStatement(sql)
        ) {
            statement.setTimestamp(1, rentingToUpdate.getDisabledDate());
            statement.setLong(2, rentingToUpdate.getId());
            var rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public List<Renting> findAllByStatus(boolean isDisabled) {
        var sql = """
                SELECT r.id, r.starting_date, r.disabled_at, c.email, c.full_name, m.serial_number, m.model FROM rentings AS r\s
                INNER JOIN clients AS c
                ON r.client_id = c.id
                INNER JOIN machines AS m
                on r.machine_id = m.id
                """;

        if (isDisabled) sql += "WHERE disabled_at < ?;";

        var rentingList = new ArrayList<Renting>();

        try (
                var connection = database.openConnection();
                var statement = connection.prepareStatement(sql))
        {

            if (isDisabled)
                statement.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            var resultSet = statement.executeQuery();

            while (resultSet.next()) {
                var id = resultSet.getLong("id");
                var startingDate = resultSet.getTimestamp("starting_date");
                var disabledAt = resultSet.getTimestamp("disabled_at");


                var clientEmail = resultSet.getString("email");
                var clientFullName = resultSet.getString("full_name");

                var client = new Client();
                client.setEmail(clientEmail);
                client.setFullName(clientFullName);

                var machineModel = resultSet.getString("model");
                var machineSerialNumber = resultSet.getString("serial_number");

                var machine = new Machine();
                machine.setModel(machineModel);
                machine.setSerialNumber(machineSerialNumber);

                var renting = new Renting(id, client, machine, startingDate, disabledAt);
                rentingList.add(renting);
            }
            resultSet.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return rentingList.stream().toList();

    }
}
