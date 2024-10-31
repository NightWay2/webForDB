package com.example.webForDB.models.tables.modelsEdit;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MeasurementEdit {
    private String station_name;
    private String id_measurement;
    private String value;
    private String measured_unit_name;
    private String time;
}
