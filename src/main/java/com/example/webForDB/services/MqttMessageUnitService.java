package com.example.webForDB.services;

import com.example.webForDB.models.modelsEdit.MqttMessageUnitEdit;
import com.example.webForDB.repositories.MqttMessageUnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MqttMessageUnitService {
    private MqttMessageUnitRepository repository;

    @Autowired
    public MqttMessageUnitService(MqttMessageUnitRepository repository) {
        this.repository = repository;
    }

    public List<MqttMessageUnitEdit> findAllMqttMessagesUnits() {
        return repository.findAll();
    }
}
