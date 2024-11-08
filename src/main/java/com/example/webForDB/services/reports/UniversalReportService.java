package com.example.webForDB.services.reports;

import com.example.webForDB.login.DBConnectHelper;
import com.example.webForDB.models.reports.Report1Model;
import com.example.webForDB.models.reports.Report234Model;
import com.example.webForDB.models.reports.UniversalReportModel;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Builder;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UniversalReportService {
    private DBConnectHelper dbConnectHelper;

    @Autowired
    public UniversalReportService(DBConnectHelper dbConnectHelper) {
        this.dbConnectHelper = dbConnectHelper;
    }

    // using for all reports
    public List<UniversalReportModel> findAllParams(String query, int typeOfModel, int countOfParams) {
        List<UniversalReportModel> listOfParams = new ArrayList<>();
        try {
            if (dbConnectHelper.openConnection()) {
                Connection connection = DBConnectHelper.getConnection();

                try (PreparedStatement preparedStatement = connection.prepareStatement(query);
                     ResultSet resultSet = preparedStatement.executeQuery()) {

                    while (resultSet.next()) {
                        UniversalReportModel params = switch (typeOfModel) {
                            case 1 -> new Report1Model();
                            case 2, 3, 4 -> new Report234Model(); // todo
                            default -> null;
                        };

                        for (int i = 0; i < countOfParams; i++) {
                            params.setParam(i + 1, resultSet.getString(i + 1));
                        }

                        listOfParams.add(params);
                    }
                } catch (SQLException ignored) { }
                finally {
                    dbConnectHelper.closeConnection();
                }
            }
        } catch (SQLException | ClassNotFoundException ignored) { }

        return listOfParams;
    }

    // using only for report1
    public void exportJasperReport1(HttpServletResponse response, String startTime, String endTime)
            throws IOException, JRException {

        LocalDateTime startDateTime = LocalDateTime.parse(startTime);
        LocalDateTime endDateTime = LocalDateTime.parse(endTime);

        String parameterStartTime = startTime.substring(0, startTime.indexOf("T")) + " " +
                startTime.substring(startTime.indexOf("T") + 1);
        String parameterEndTime = endTime.substring(0, endTime.indexOf("T")) + " " +
                endTime.substring(endTime.indexOf("T") + 1);

        List<UniversalReportModel> fields = findAllParams(
                "select unit, station, max(value_) " +
                "from report1 " +
                "where time_ between '" + startDateTime + "' and '"+ endDateTime + "' " +
                "group by unit, station " +
                "order by station, unit", 1, 3);

        File file = ResourceUtils.getFile("classpath:report1.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(fields);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("startTime", parameterStartTime);
        parameters.put("endTime", parameterEndTime);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
        JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
    }

    // using for report2
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

    // inner record
    @Builder
    public record Station(String id, String name) {}

    // using for report2
    public List<Station> findAllStationsForReport(int report) {
        List<Station> stations = new ArrayList<>();
        try {
            if (dbConnectHelper.openConnection()) {
                Connection connection = DBConnectHelper.getConnection();
                String query = "select s.id_station, s.name_ " +
                        "from station s, report" + report + " r " +
                        "where r.id_station = s.id_station " +
                        "group by s.name_, s.id_station;";

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

    // using only for report2
    public void exportJasperReport2(HttpServletResponse response, String stationId)
            throws IOException, JRException {

        List<UniversalReportModel> fields = findAllParams(
                "select category, count_of " +
                "from report2 where id_station = '" + stationId + "' " +
                "group by category, count_of", 2, 2);

        String stationName = findStationNameById(stationId);

        File file = ResourceUtils.getFile("classpath:report2.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(fields);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("stationName", stationName);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
        JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
    }

    // using only for report3
    public void exportJasperReport3(HttpServletResponse response, String stationId)
            throws IOException, JRException {

        List<UniversalReportModel> fields = findAllParams(
                "select category, count_of " +
                        "from report3 where id_station = '" + stationId + "' " +
                        "group by category, count_of", 3, 2);

        String stationName = findStationNameById(stationId);

        File file = ResourceUtils.getFile("classpath:report3.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(fields);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("stationName", stationName);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
        JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
    }
}
