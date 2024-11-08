package com.example.webForDB.models.reports;

import lombok.Data;

@Data
public class Report234Model extends UniversalReportModel {
    private String designation;
    private int countOf;

    @Override
    public void setParam(int count, String value) {
        switch (count) {
            case 1 -> designation = value;
            case 2 -> countOf = Integer.parseInt(value);
        }
    }
}
