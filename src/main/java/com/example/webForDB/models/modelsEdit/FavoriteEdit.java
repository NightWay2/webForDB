package com.example.webForDB.models.modelsEdit;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FavoriteEdit {
    private String user_name;
    private String station_name;
    private String station_coordinates;
}