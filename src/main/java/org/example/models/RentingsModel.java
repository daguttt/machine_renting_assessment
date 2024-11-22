package org.example.models;

import org.example.entities.Renting;
import org.example.exceptions.CouldnCreateRentingException;
import org.example.persistence.Database;

import java.sql.PreparedStatement;
import java.sql.SQLException;

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
}
