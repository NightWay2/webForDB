package com.example.webForDB.models.tables.modelsEdit;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MqttMessageUnitEdit {
    private String station_name;
    private String measured_unit_title;
    private String message;
    private String order;
}
