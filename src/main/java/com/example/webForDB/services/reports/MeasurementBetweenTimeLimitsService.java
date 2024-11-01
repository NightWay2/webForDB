package com.example.webForDB.services.reports;

import com.example.webForDB.login.DBConnectHelper;
import com.example.webForDB.models.reports.MeasurementBetweenTimeLimits;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Builder;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public void exportJasperReport(HttpServletResponse response, String stationId, String startTime,
                                   String endTime) throws IOException, JRException {

        LocalDateTime startDateTime = LocalDateTime.parse(startTime);
        LocalDateTime endDateTime = LocalDateTime.parse(endTime);

        String parameterStartTime = startTime.substring(0, startTime.indexOf("T")) + " " +
                startTime.substring(startTime.indexOf("T") + 1);
        String parameterEndTime = endTime.substring(0, endTime.indexOf("T")) + " " +
                endTime.substring(endTime.indexOf("T") + 1);

        List<MeasurementBetweenTimeLimits> fields = findAllFields(stationId, startDateTime, endDateTime);

        String stationName = findStationNameById(stationId);

        File file = ResourceUtils.getFile("classpath:measurement_by_station_and_time_report.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(fields);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("stationName", stationName);
        parameters.put("startTime", parameterStartTime);
        parameters.put("endTime", parameterEndTime);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
        JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
    }
}
