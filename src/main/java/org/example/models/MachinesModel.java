package org.example.models;

import org.example.MySQLErrorCodes;
import org.example.entities.Client;
import org.example.entities.Machine;
import org.example.enums.MachineStatus;
import org.example.exceptions.CouldnCreateMachineException;
import org.example.exceptions.CouldntCreateClientException;
import org.example.persistence.Database;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MachinesModel {
    private final Database database;

    public MachinesModel(Database database) {
        this.database = database;
    }

    public Machine create(Machine baseMachine) {
        var sql = """
                INSERT INTO machines (model, serial_number, status)\s
                    VALUES (?, ?, ?);
                """;

        try (
                var connection = database.openConnection();
                var statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS))
        {
            statement.setString(1, baseMachine.getModel());
            statement.setString(2, baseMachine.getSerialNumber());
            statement.setString(3, baseMachine.getStatus().name());

            statement.execute();

            var resultSet = statement.getGeneratedKeys();

            if (resultSet.next()) {
                long givenMachineId = resultSet.getLong(1);
                baseMachine.setId(givenMachineId);
            } else throw new CouldnCreateMachineException("Couldn't register machine");

            resultSet.close();

            return baseMachine;
        } catch (SQLException e) {
            if (e.getErrorCode() == MySQLErrorCodes.UNIQUE_VIOLATION.getCode())
                throw new CouldnCreateMachineException(String.format("Machine with serial number '%s' already exists", baseMachine.getSerialNumber()));
            throw new RuntimeException(e);
        }

    }

    public Optional<Machine> findOneBySerialNumber(String serialNumber) {
        var sql = """
                SELECT * FROM machines WHERE serial_number = ? LIMIT 1;
                """;
        Machine machine = null;
        try (
                var con = database.openConnection();
                var statement = con.prepareStatement(sql)
        ) {
            statement.setString(1, serialNumber);
            var result = statement.executeQuery();
            if (result.next()) {
                machine = new Machine(
                        result.getLong("id"),
                        result.getString("model"),
                        result.getString("serial_number"),
                        MachineStatus.valueOf(result.getString("status"))
                );
            }
            result.close();
            return Optional.ofNullable(machine);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public int count() {
        var sql = """
                SELECT COUNT(*) as machines_count FROM machines;
                """;
        int machinesCount = 0;
        try (
                var con = database.openConnection();
                var statement = con.createStatement()
        ) {
            var result = statement.executeQuery(sql);
            if (result.next()) {
                machinesCount = result.getInt("machines_count");
            }
            result.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return machinesCount;
    }

    public List<Machine> findAll(int page, int pageSize) {
        var sql = """
                SELECT * FROM machines LIMIT ? OFFSET ?;
                """;

        var machineList = new ArrayList<Machine>();

        try (
                var connection = database.openConnection();
                var statement = connection.prepareStatement(sql))
        {

            statement.setInt(1, pageSize);
            statement.setInt(2, (page - 1) * pageSize);
            var resultSet = statement.executeQuery();

            while (resultSet.next()) {
                var id = resultSet.getLong("id");
                var model = resultSet.getString("model");
                var serial_number = resultSet.getString("serial_number");
                var status = resultSet.getString("status");

                var machine = new Machine(id, model, serial_number, MachineStatus.valueOf(status));
                machineList.add(machine);
            }
            resultSet.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return machineList.stream().toList();
    }
}
