package com.example.webForDB.services.tables;

import com.example.webForDB.login.DBConnectHelper;
import com.example.webForDB.models.tables.MqttServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class MqttServerService {
    private DBConnectHelper dbConnectHelper;

    @Autowired
    public MqttServerService(DBConnectHelper dbConnectHelper) {
        this.dbConnectHelper = dbConnectHelper;
    }

    public List<MqttServer> findAllMqttServers() {
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
