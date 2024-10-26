package com.example.webForDB.services;

import com.example.webForDB.models.modelsEdit.StationEdit;
import com.example.webForDB.repositories.StationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StationService {
    private StationRepository repository;

    @Autowired
    public StationService(StationRepository repository) {
        this.repository = repository;
    }

    public List<StationEdit> findAllStations() {
        return repository.findAll();
    }
}
