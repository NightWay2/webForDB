package com.example.webForDB.repositories;

import com.example.webForDB.login.DBConnectHelper;
import com.example.webForDB.models.modelsEdit.FavoriteEdit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class FavoriteRepository {
    private DBConnectHelper dbConnectHelper;

    @Autowired
    public FavoriteRepository(DBConnectHelper dbConnectHelper) {
        this.dbConnectHelper = dbConnectHelper;
    }

    public List<FavoriteEdit> findAll() {
        List<FavoriteEdit> favorites = new ArrayList<>();
        try {
            if (dbConnectHelper.openConnection()) {
                Connection connection = DBConnectHelper.getConnection();
                String query = "select f.user_name, s.name_, s.coordinates from favorite f, station s " +
                        "where s.id_station = f.id_station";

                try (PreparedStatement preparedStatement = connection.prepareStatement(query);
                     ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        FavoriteEdit favorite = FavoriteEdit.builder()
                                .user_name(resultSet.getString("user_name"))
                                .station_name(resultSet.getString("name_"))
                                .station_coordinates(resultSet.getString("coordinates"))
                                .build();

                        favorites.add(favorite);
                    }
                } catch (SQLException ignored) {
                } finally {
                    dbConnectHelper.closeConnection();
                }
            }
        } catch (SQLException | ClassNotFoundException ignored) {
        }

        return favorites;
    }
}
