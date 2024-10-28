package com.example.webForDB.services;

import com.example.webForDB.models.modelsEdit.MeasurementEdit;
import com.example.webForDB.repositories.MeasurementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MeasurementService {
    private MeasurementRepository repository;

    @Autowired
    public MeasurementService(MeasurementRepository repository) {
        this.repository = repository;
    }

    public List<MeasurementEdit> findMeasurementsWithPagination(int offset, int limit) {
        return repository.findAllWithPagination(offset, limit);
    }

    public int countAllMeasurements() {
        return repository.countAll();
    }

}
