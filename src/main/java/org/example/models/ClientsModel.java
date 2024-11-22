package org.example.models;

import org.example.MySQLErrorCodes;
import org.example.entities.Client;
import org.example.exceptions.CouldntCreateClientException;
import org.example.persistence.Database;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;

public class ClientsModel {
    private final Database database;

    public ClientsModel(Database database) {
        this.database = database;
    }

    public Optional<Client> findOneByEmail(String email) {
        var sql = """
                SELECT * FROM clients WHERE email = ? LIMIT 1
                """;
        Client client = null;
        try (
                var con = database.openConnection();
                var statement = con.prepareStatement(sql)
        ) {
            statement.setString(1, email);
            var result = statement.executeQuery();
            if (result.next()) {
                client = new Client(
                        result.getLong("id"),
                        result.getString("full_name"),
                        result.getString("email"),
                        result.getString("phone_number"),
                        result.getString("address")
                );
            }
            result.close();
            return Optional.ofNullable(client);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Client create(Client baseClient) {
        var sql = """
                INSERT INTO clients (full_name, email, phone_number, address)\s
                    VALUES (?, ?, ?, ?);
                """;

        try (
                var connection = database.openConnection();
                var statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS))
        {
            statement.setString(1, baseClient.getFullName());
            statement.setString(2, baseClient.getEmail());
            statement.setString(3, baseClient.getPhoneNumber());
            statement.setString(4, baseClient.getAddress());

            statement.execute();

            var resultSet = statement.getGeneratedKeys();

            if (resultSet.next()) {
                long givenClientId = resultSet.getLong(1);
                baseClient.setId(givenClientId);
            } else throw new CouldntCreateClientException("Couldn't create client");

            resultSet.close();

            return baseClient;
        } catch (SQLException e) {
            if (e.getErrorCode() == MySQLErrorCodes.UNIQUE_VIOLATION.getCode())
                throw new CouldntCreateClientException("Email already exists");
            throw new RuntimeException(e);
        }

    }
}
