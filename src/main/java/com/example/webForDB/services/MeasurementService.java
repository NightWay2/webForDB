package com.example.webForDB.services;

import com.example.webForDB.models.Measurement;
import com.example.webForDB.models.modelsWithoutId.MeasurementClear;
import com.example.webForDB.repositories.MeasurementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MeasurementService {
    private MeasurementRepository measurementRepository;

    @Autowired
    public MeasurementService(MeasurementRepository measurementRepository) {
        this.measurementRepository = measurementRepository;
    }

    public List<Measurement> findAllMeasurements() {
        return measurementRepository.findAll();
    }

    public List<MeasurementClear> findAllClear() {
        return measurementRepository.findAllWithoutId();
    }
}
