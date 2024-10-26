package com.example.webForDB.services;

import com.example.webForDB.models.modelsEdit.FavoriteEdit;
import com.example.webForDB.repositories.FavoriteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoriteService {
    private FavoriteRepository repository;

    @Autowired
    public FavoriteService(FavoriteRepository repository) {
        this.repository = repository;
    }

    public List<FavoriteEdit> findAllFavorites() {
        return repository.findAll();
    }
}
