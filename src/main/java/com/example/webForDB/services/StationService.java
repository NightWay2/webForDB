package com.example.webForDB.services;

import com.example.webForDB.models.modelsEdit.StationEdit;
import com.example.webForDB.repositories.StationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StationService {
    private StationRepository stationRepository;

    @Autowired
    public StationService(StationRepository stationRepository) {
        this.stationRepository = stationRepository;
    }

    public List<StationEdit> findAllStations() {
        return stationRepository.findAll();
    }
}
