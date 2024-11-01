package com.example.webForDB.models.reports;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MeasurementBetweenTimeLimits {
    private String measuredUnit;
    private String results;
}
