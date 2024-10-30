package com.example.webForDB.services;

import com.example.webForDB.login.DBConnectHelper;
import com.example.webForDB.models.modelsEdit.MqttMessageUnitEdit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class MqttMessageUnitService {
    private DBConnectHelper dbConnectHelper;

    @Autowired
    public MqttMessageUnitService(DBConnectHelper dbConnectHelper) {
        this.dbConnectHelper = dbConnectHelper;
    }

    public List<MqttMessageUnitEdit> findAllMqttMessagesUnits() {
        List<MqttMessageUnitEdit> mqttMessageUnits = new ArrayList<>();
        try {
            if (dbConnectHelper.openConnection()) {
                Connection connection = DBConnectHelper.getConnection();
                String query = "select s.name_, mu.title, mmu.message_, mmu.order_ " +
                        "from station s, measured_unit mu, mqtt_message_unit mmu " +
                        "where s.id_station = mmu.id_station and mu.id_measured_unit = mmu.id_measured_unit";

                try (PreparedStatement preparedStatement = connection.prepareStatement(query);
                     ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        MqttMessageUnitEdit mqttMessageUnit = MqttMessageUnitEdit.builder()
                                .station_name(resultSet.getString("name_"))
                                .measured_unit_title(resultSet.getString("title"))
                                .message(resultSet.getString("message_"))
                                .order(resultSet.getString("order_"))
                                .build();

                        mqttMessageUnits.add(mqttMessageUnit);
                    }
                } catch (SQLException ignored) {
                } finally {
                    dbConnectHelper.closeConnection();
                }
            }
        } catch (SQLException | ClassNotFoundException ignored) {
        }

        return mqttMessageUnits;
    }
}
