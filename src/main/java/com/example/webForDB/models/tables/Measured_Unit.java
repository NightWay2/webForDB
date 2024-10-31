package com.example.webForDB.models.tables;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Measured_Unit {
    private String id_measured_unit;
    private String title;
    private String unit;
}
