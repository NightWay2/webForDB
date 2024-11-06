package com.example.webForDB.models.tables;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StationEdit {
    private String id_station;
    private String city;
    private String name;
    private String status;
    private String coordinates;
    private String server_url;
}
