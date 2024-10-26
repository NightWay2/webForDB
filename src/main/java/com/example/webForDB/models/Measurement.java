package com.example.webForDB.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Measurement {
    private String id_measurement;
    private String time;
    private String value;
    private String id_station;
    private String id_measured_unit;
}
