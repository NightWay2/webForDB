package com.example.webForDB.models.reports;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StationWithMeasuredUnits {
    private String serverName;
    private String time;
    private String measuredUnits;
}
