package com.suza.safety_map_backend.Controller;

import com.suza.safety_map_backend.Entity.AccidentHotspot;
import com.suza.safety_map_backend.Repository.AccidentHotspotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/hotspots")
public class AccidentHotspotController {

    @Autowired
    private AccidentHotspotRepository repository;

    @GetMapping("/all")
    public List<AccidentHotspot> getAll() {
        return repository.findAll();
    }
}
