package com.example.webForDB.services;

import com.example.webForDB.models.modelsEdit.OptimalValueEdit;
import com.example.webForDB.repositories.OptimalValueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OptimalValueService {
    private OptimalValueRepository repository;

    @Autowired
    public OptimalValueService(OptimalValueRepository repository) {
        this.repository = repository;
    }

    public List<OptimalValueEdit> findAllOptimalValues() {
        return repository.findAll();
    }
}
