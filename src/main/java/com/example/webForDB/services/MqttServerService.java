package com.example.webForDB.services;

import com.example.webForDB.models.MqttServer;
import com.example.webForDB.repositories.MqttServerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MqttServerService {
    private MqttServerRepository repository;

    @Autowired
    public MqttServerService(MqttServerRepository repository) {
        this.repository = repository;
    }

    public List<MqttServer> findAllMqttServers() {
        return repository.findAll();
    }
}
