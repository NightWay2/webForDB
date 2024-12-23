package com.example.webForDB.services.tables;

import com.example.webForDB.login.DBConnectHelper;
import com.example.webForDB.models.tables.StationEdit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class StationService {
    private DBConnectHelper dbConnectHelper;

    @Autowired
    public StationService(DBConnectHelper dbConnectHelper) {
        this.dbConnectHelper = dbConnectHelper;
    }

    public List<StationEdit> findAllStations() {
        List<StationEdit> stations = new ArrayList<>();
        try {
            if (dbConnectHelper.openConnection()) {
                Connection connection = DBConnectHelper.getConnection();
                String query = "select s.id_station, s.city, s.name_, s.status, s.coordinates, ms.url " +
                        "from station s, mqtt_server ms where s.id_server = ms.id_server";

                try (PreparedStatement preparedStatement = connection.prepareStatement(query);
                     ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        StationEdit station = StationEdit.builder()
                                .id_station(resultSet.getString("id_station"))
                                .city(resultSet.getString("city"))
                                .name(resultSet.getString("name_"))
                                .status(resultSet.getString("status"))
                                .coordinates(resultSet.getString("coordinates"))
                                .server_url(resultSet.getString("url"))
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
}
