package com.se192491.assignment02.service;

import com.se192491.assignment02.pojo.Orchid;
import com.se192491.assignment02.repository.OrchidRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrchidServiceImpl implements OrchidService {
    @Autowired
    private OrchidRepository orchidRepository;

    @Override
    public void save(Orchid orchid) {
        orchidRepository.save(orchid);
    }

    @Override
    public Orchid findById(int id) {
        return orchidRepository.findById(id)
                .orElse(null);
    }

    @Override
    public List<Orchid> findAll() {
        return orchidRepository.findAll();
    }

    @Override
    public Orchid update(int id, Orchid orchid) {
        Orchid oldOrchid = findById(id);
        if (oldOrchid == null) {
            return null;
        }
        oldOrchid.setOrchidDescription(orchid.getOrchidDescription());
        oldOrchid.setOrchidName(orchid.getOrchidName());
        oldOrchid.setOrchidUrl(orchid.getOrchidUrl());
        oldOrchid.setCategory(orchid.getCategory());
        oldOrchid.setNatural(orchid.isNatural());
        oldOrchid.setPrice(orchid.getPrice());
        return orchidRepository.save(oldOrchid);
    }

    @Override
    public void delete(int id) {
        orchidRepository.deleteById(id);
    }
}
