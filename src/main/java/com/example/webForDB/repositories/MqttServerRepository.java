package com.example.webForDB.repositories;

import com.example.webForDB.login.DBConnectHelper;
import com.example.webForDB.models.MqttServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MqttServerRepository {
    private DBConnectHelper dbConnectHelper;

    @Autowired
    public MqttServerRepository(DBConnectHelper dbConnectHelper) {
        this.dbConnectHelper = dbConnectHelper;
    }

    public List<MqttServer> findAll() {
        List<MqttServer> mqttServers = new ArrayList<>();
        try {
            if (dbConnectHelper.openConnection()) {
                Connection connection = DBConnectHelper.getConnection();
                String query = "select * from mqtt_server";

                try (PreparedStatement preparedStatement = connection.prepareStatement(query);
                     ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        MqttServer mqttServer = MqttServer.builder()
                                .id_server(resultSet.getString("id_server"))
                                .url(resultSet.getString("url"))
                                .status(resultSet.getString("status"))
                                .build();

                        mqttServers.add(mqttServer);
                    }
                } catch (SQLException ignored) {
                } finally {
                    dbConnectHelper.closeConnection();
                }
            }
        } catch (SQLException | ClassNotFoundException ignored) {
        }

        return mqttServers;
    }
}
