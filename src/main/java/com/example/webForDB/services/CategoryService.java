package com.example.webForDB.services;

import com.example.webForDB.login.DBConnectHelper;
import com.example.webForDB.models.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {
    private DBConnectHelper dbConnectHelper;

    @Autowired
    public CategoryService(DBConnectHelper dbConnectHelper) {
        this.dbConnectHelper = dbConnectHelper;
    }

    public List<Category> findAllCategories() {
        List<Category> categories = new ArrayList<>();
        try {
            if (dbConnectHelper.openConnection()) {
                Connection connection = DBConnectHelper.getConnection();
                String query = "select ID_Category, Designation from category";

                try (PreparedStatement preparedStatement = connection.prepareStatement(query);
                     ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        Category category = Category.builder()
                                .id_category(resultSet.getString("ID_Category"))
                                .designation(resultSet.getString("Designation"))
                                .build();

                        categories.add(category);
                    }
                } catch (SQLException ignored) {
                } finally {
                    dbConnectHelper.closeConnection();
                }
            }
        } catch (SQLException | ClassNotFoundException ignored) {
        }

        return categories;
    }
}
