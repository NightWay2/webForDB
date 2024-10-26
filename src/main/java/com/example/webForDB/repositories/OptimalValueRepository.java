package com.example.webForDB.repositories;

import com.example.webForDB.login.DBConnectHelper;
import com.example.webForDB.models.modelsEdit.OptimalValueEdit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class OptimalValueRepository {
    private DBConnectHelper dbConnectHelper;

    @Autowired
    public OptimalValueRepository(DBConnectHelper dbConnectHelper) {
        this.dbConnectHelper = dbConnectHelper;
    }

    public List<OptimalValueEdit> findAll() {
        List<OptimalValueEdit> optimalValues = new ArrayList<>();
        try {
            if (dbConnectHelper.openConnection()) {
                Connection connection = DBConnectHelper.getConnection();
                String query = "select c.designation, mu.title, o.bottom_border, o.upper_border, mu.unit " +
                        "from optimal_value o, category c, measured_unit mu " +
                        "where o.id_category = c.id_category and mu.id_measured_unit = o.id_measured_unit";

                try (PreparedStatement preparedStatement = connection.prepareStatement(query);
                     ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        OptimalValueEdit optimalValue = OptimalValueEdit.builder()
                                .designation(resultSet.getString("designation"))
                                .title(resultSet.getString("title"))
                                .bottom_border(resultSet.getString("bottom_border"))
                                .upper_border(resultSet.getString("upper_border"))
                                .unit(resultSet.getString("unit"))
                                .build();

                        optimalValues.add(optimalValue);
                    }
                } catch (SQLException ignored) {
                } finally {
                    dbConnectHelper.closeConnection();
                }
            }
        } catch (SQLException | ClassNotFoundException ignored) {
        }

        return optimalValues;
    }
}
