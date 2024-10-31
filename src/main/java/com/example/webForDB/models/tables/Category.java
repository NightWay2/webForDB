package com.example.webForDB.models.tables;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Category {
    private String id_category;
    private String designation;
}
