package com.example.webForDB.models.modelsWithoutId;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MeasurementClear {
    private String station_name;
    private String id_measurement;
    private String value;
    private String measured_unit_name;
    private String time;
}
