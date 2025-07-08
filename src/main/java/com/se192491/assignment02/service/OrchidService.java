package com.se192491.assignment02.service;


import com.se192491.assignment02.pojo.Orchid;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface OrchidService {
    public void save(Orchid orchid);
    public Orchid findById(int id);
    public List<Orchid> findAll();
    public Orchid update(int id,Orchid orchid);
    public void delete(int id) throws IOException;
    public Boolean upload(MultipartFile imageFile, int id) throws IOException;
    public Page<Orchid> findAllPaginated(int pageNo, int pageSize);
}
