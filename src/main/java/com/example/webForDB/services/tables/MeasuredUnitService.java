package com.example.webForDB.services.tables;

import com.example.webForDB.login.DBConnectHelper;
import com.example.webForDB.models.tables.Measured_Unit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class MeasuredUnitService {
    private DBConnectHelper dbConnectHelper;

    @Autowired
    public MeasuredUnitService(DBConnectHelper dbConnectHelper) {
        this.dbConnectHelper = dbConnectHelper;
    }

    public List<Measured_Unit> findAllMeasuredUnits() {
        List<Measured_Unit> measuredUnits = new ArrayList<>();
        try {
            if (dbConnectHelper.openConnection()) {
                Connection connection = DBConnectHelper.getConnection();
                String query = "select * from measured_unit";

                try (PreparedStatement preparedStatement = connection.prepareStatement(query);
                     ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        Measured_Unit measuredUnit = Measured_Unit.builder()
                                .id_measured_unit(resultSet.getString("id_measured_unit"))
                                .title(resultSet.getString("title"))
                                .unit(resultSet.getString("unit"))
                                .build();

                        measuredUnits.add(measuredUnit);
                    }
                } catch (SQLException ignored) {
                } finally {
                    dbConnectHelper.closeConnection();
                }
            }
        } catch (SQLException | ClassNotFoundException ignored) {
        }

        return measuredUnits;
    }
}
