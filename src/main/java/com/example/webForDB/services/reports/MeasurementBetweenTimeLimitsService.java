package com.example.webForDB.services.reports;

import com.example.webForDB.login.DBConnectHelper;
import com.example.webForDB.models.reports.MeasurementBetweenTimeLimits;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class MeasurementBetweenTimeLimitsService {
    private DBConnectHelper dbConnectHelper;

    @Autowired
    public MeasurementBetweenTimeLimitsService(DBConnectHelper dbConnectHelper) {
        this.dbConnectHelper = dbConnectHelper;
    }

    @Builder
    public record Station(String id, String name) {}

    public String findStationNameById(String stationId) {
        String stationName = null;

        try {
            if (dbConnectHelper.openConnection()) {
                Connection connection = DBConnectHelper.getConnection();
                String query = "select name_ from station where id_station = ?";

                try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                    preparedStatement.setString(1, stationId);
                    ResultSet resultSet = preparedStatement.executeQuery();

                    while (resultSet.next()) {
                        stationName = resultSet.getString("name_");
                    }
                } catch (SQLException ignored) {
                } finally {
                    dbConnectHelper.closeConnection();
                }
            }
        }  catch (SQLException | ClassNotFoundException ignored) {
        }

        return stationName;
    }

    public List<Station> findAllStations() {
        List<Station> stations = new ArrayList<>();
        try {
            if (dbConnectHelper.openConnection()) {
                Connection connection = DBConnectHelper.getConnection();
                String query = "select * from station";

                try (PreparedStatement preparedStatement = connection.prepareStatement(query);
                     ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        Station station = Station.builder()
                                .id(resultSet.getString("id_station"))
                                .name(resultSet.getString("name_"))
                                .build();

                        stations.add(station);
                    }
                } catch (SQLException ignored) {
                } finally {
                    dbConnectHelper.closeConnection();
                }
            }
        } catch (SQLException | ClassNotFoundException ignored) {
        }

        return stations;
    }

    public List<MeasurementBetweenTimeLimits> findAllFields(String stationId, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        List<MeasurementBetweenTimeLimits> fields = new ArrayList<>();
        try {
            if (dbConnectHelper.openConnection()) {
                Connection connection = DBConnectHelper.getConnection();
                String query = "WITH Measurement_spec AS (" +
                        "SELECT " +
                        "measured_unit.title AS title, " +
                        "REPLACE(measurment.value_, ',', '.')::float AS value_ " +
                        "FROM Measurment " +
                        "INNER JOIN Station ON Station.id_station = Measurment.id_station " +
                        "INNER JOIN Measured_Unit ON Measured_Unit.id_measured_unit = Measurment.id_measured_unit " +
                        "WHERE Station.id_station = ? " +
                        "AND Measurment.time_ BETWEEN ? AND ? " +
                        "), " +
                        "Aggregated AS (" +
                        "SELECT " +
                        "title, " +
                        "MIN(value_) AS min_value, " +
                        "ROUND(AVG(value_::numeric), 2) AS avg_value, " +
                        "MAX(value_) AS max_value " +
                        "FROM Measurement_spec " +
                        "GROUP BY title " +
                        ") " +
                        "SELECT " +
                        "title, " +
                        "'min = ' || min_value || ', avg = ' || avg_value || ', max = ' || max_value AS aggregated_values " +
                        "FROM Aggregated;";

                try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                    preparedStatement.setString(1, stationId);
                    preparedStatement.setTimestamp(2, Timestamp.valueOf(startDateTime));
                    preparedStatement.setTimestamp(3, Timestamp.valueOf(endDateTime));
                    ResultSet resultSet = preparedStatement.executeQuery();

                    while (resultSet.next()) {
                        MeasurementBetweenTimeLimits field = MeasurementBetweenTimeLimits.builder()
                                .measuredUnit(resultSet.getString(1))
                                .results(resultSet.getString(2))
                                .build();

                        fields.add(field);
                    }
                } catch (SQLException ignored) {
                } finally {
                    dbConnectHelper.closeConnection();
                }
            }
        } catch (SQLException | ClassNotFoundException ignored) {
        }

        return fields;
    }
}
