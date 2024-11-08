package com.example.webForDB.models.reports;

import lombok.Data;

@Data
public class Report1Model extends UniversalReportModel {
    private String parameter;
    private String station;
    private double value;

    @Override
    public void setParam(int count, String value) {
        switch (count) {
            case 1 -> parameter = value;
            case 2 -> station = value;
            case 3 -> {
                value = value.replace(",", ".");
                this.value = Double.parseDouble(value);
            }
        }
    }
}
