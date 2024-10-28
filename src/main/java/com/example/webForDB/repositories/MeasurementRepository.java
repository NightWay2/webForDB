package com.example.webForDB.repositories;

import com.example.webForDB.login.DBConnectHelper;
import com.example.webForDB.models.modelsEdit.MeasurementEdit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MeasurementRepository {

    private DBConnectHelper dbConnectHelper;

    @Autowired
    public MeasurementRepository(DBConnectHelper dbConnectHelper) {
        this.dbConnectHelper = dbConnectHelper;
    }

    public List<MeasurementEdit> findAllWithPagination(int offset, int limit) {
        List<MeasurementEdit> measurements = new ArrayList<>();
        try {
            if (dbConnectHelper.openConnection()) {
                Connection connection = DBConnectHelper.getConnection();
                String query = "select s.Name_, m.ID_Measurment, Value_, mu.Unit, m.Time_ " +
                        "from measurment m, station s, measured_unit mu " +
                        "where s.id_station = m.id_station and " +
                        "mu.id_measured_unit = m.id_measured_unit " +
                        "order by m.time_ DESC, m.id_measurment " +
                        "limit ? offset ?";

                try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                    preparedStatement.setInt(1, limit);
                    preparedStatement.setInt(2, offset);
                    ResultSet resultSet = preparedStatement.executeQuery();

                    while (resultSet.next()) {
                        MeasurementEdit measurement = MeasurementEdit.builder()
                                .station_name(resultSet.getString("Name_"))
                                .id_measurement(resultSet.getString("ID_Measurment"))
                                .value(resultSet.getString("Value_"))
                                .measured_unit_name(resultSet.getString("Unit"))
                                .time(resultSet.getString("Time_"))
                                .build();

                        measurements.add(measurement);
                    }
                } finally {
                    dbConnectHelper.closeConnection();
                }
            }
        } catch (SQLException | ClassNotFoundException ignored) {
        }

        return measurements;
    }

    public int countAll() {
        int count = 0;
        try {
            if (dbConnectHelper.openConnection()) {
                Connection connection = DBConnectHelper.getConnection();
                String query = "select count(*) from measurment";
                try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                    ResultSet resultSet = preparedStatement.executeQuery();
                    if (resultSet.next()) {
                        count = resultSet.getInt(1);
                    }
                } finally {
                    dbConnectHelper.closeConnection();
                }
            }
        } catch (SQLException | ClassNotFoundException ignored) {
        }
        return count;
    }

}

