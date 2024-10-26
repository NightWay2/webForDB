package com.example.webForDB.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MqttServer {
    private String id_server;
    private String url;
    private String status;
}
