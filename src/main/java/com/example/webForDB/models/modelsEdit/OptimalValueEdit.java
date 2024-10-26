package com.example.webForDB.models.modelsEdit;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OptimalValueEdit {
    private String designation;
    private String title;
    private String bottom_border;
    private String upper_border;
    private String unit;
}
