package com.example.webForDB.services.reports;

import com.example.webForDB.login.DBConnectHelper;
import com.example.webForDB.models.reports.StationWithMeasuredUnits;
import jakarta.servlet.http.HttpServletResponse;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StationWithMeasuredUnitsService {
    private DBConnectHelper dbConnectHelper;

    @Autowired
    public StationWithMeasuredUnitsService(DBConnectHelper dbConnectHelper) {
        this.dbConnectHelper = dbConnectHelper;
    }

    public List<StationWithMeasuredUnits> findAllFields() {
        List<StationWithMeasuredUnits> fields = new ArrayList<>();
        try {
            if (dbConnectHelper.openConnection()) {
                Connection connection = DBConnectHelper.getConnection();
                String query = "WITH Station_spec AS (" +
                        "SELECT Station.name_ as name_, MIN(Measurment.time_) as time_, Measured_unit.title as title " +
                        "FROM Measured_unit " +
                        "INNER JOIN Measurment ON Measured_unit.id_measured_unit = Measurment.id_measured_unit " +
                        "INNER JOIN Station ON Measurment.id_station = Station.id_station " +
                        "GROUP BY name_, title" +
                        ") " +
                        "SELECT name_, MIN(time_), STRING_AGG(title, ', ') " +
                        "FROM Station_spec " +
                        "GROUP BY name_ " +
                        "ORDER BY name_;";

                try (PreparedStatement preparedStatement = connection.prepareStatement(query);
                     ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        StationWithMeasuredUnits field = StationWithMeasuredUnits.builder()
                                .stationName(resultSet.getString(1))
                                .time(resultSet.getString(2))
                                .measuredUnits(resultSet.getString(3))
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

    public void exportJasperReport(HttpServletResponse response) throws IOException, JRException {
        List<StationWithMeasuredUnits> fields = findAllFields();

        File file = ResourceUtils.getFile("classpath:station_with_mes_units_report.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(fields);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("createdBy", "Maksym Zakomirnyi");
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
        JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
    }
}
