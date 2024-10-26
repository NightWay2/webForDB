package com.example.webForDB.services;

import com.example.webForDB.models.Measured_Unit;
import com.example.webForDB.repositories.MeasuredUnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MeasuredUnitService {
    private MeasuredUnitRepository repository;

    @Autowired
    public MeasuredUnitService(MeasuredUnitRepository repository) {
        this.repository = repository;
    }

    public List<Measured_Unit> findAllMeasuredUnits() {
        return repository.findAll();
    }
}
