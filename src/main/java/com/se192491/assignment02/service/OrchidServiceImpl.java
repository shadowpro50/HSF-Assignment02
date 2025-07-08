package com.se192491.assignment02.service;

import com.se192491.assignment02.pojo.Orchid;
import com.se192491.assignment02.repository.OrchidRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
        oldOrchid.setCategory(orchid.getCategory());
        oldOrchid.setNatural(orchid.isNatural());
        oldOrchid.setPrice(orchid.getPrice());
        return orchidRepository.save(oldOrchid);
    }

    @Override
    public void delete(int id) throws IOException {
        Orchid orchid = findById(id);
        String oldImageUrl = orchid.getOrchidUrl();
        if (oldImageUrl != null && !oldImageUrl.isBlank()) {
            Path oldImagePath = Paths.get("images/", Paths.get(oldImageUrl).getFileName().toString());

            // Delete the old image file if it exists
            if (Files.exists(oldImagePath)) {
                Files.delete(oldImagePath);
            }
        }
        orchidRepository.deleteById(id);
    }

    @Override
    public Boolean upload(MultipartFile imageFile, int id) throws IOException {
        Orchid orchid = findById(id);

        if (!imageFile.isEmpty()) {
            String oldImageUrl = orchid.getOrchidUrl();
            if (oldImageUrl != null && !oldImageUrl.isBlank()) {
                // Convert URL to path (assumes you're storing in "images/uploads/")
                Path oldImagePath = Paths.get("images/", Paths.get(oldImageUrl).getFileName().toString());

                // Delete the old image file if it exists
                if (Files.exists(oldImagePath)) {
                    Files.delete(oldImagePath);
                }
            }

            String fileName = imageFile.getOriginalFilename();
            Path path = Paths.get("images/", fileName);
            Files.write(path, imageFile.getBytes());

            orchid.setOrchidUrl("/images/" + fileName);
        } else {
            orchid.setOrchidUrl("none");
        }
        save(orchid);
        return true;
    }

    @Override
    public Page<Orchid> findAllPaginated(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        return orchidRepository.findAll(pageable);
    }
}
