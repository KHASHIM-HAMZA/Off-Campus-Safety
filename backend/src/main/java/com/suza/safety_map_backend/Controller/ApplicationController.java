package com.suza.safety_map_backend.Controller;

import com.suza.safety_map_backend.Entity.HostelApplication;
import com.suza.safety_map_backend.Repository.HostelApplicationRepository;
import com.suza.safety_map_backend.Service.ApplicationService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/applications")
public class ApplicationController {
    private final ApplicationService applicationService;

    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @PostMapping("/apply")
    public HostelApplication apply(@RequestBody HostelApplication application) {
        return applicationService.apply(application);
    }

    @GetMapping
    public List<HostelApplication> getAll() {
        return applicationService.getAll();
    }

    @PutMapping("/{id}/status")
    public HostelApplication updateStatus(@PathVariable Long id, @RequestParam HostelApplication.Status status) {
        return applicationService.updateStatus(id, status);
    }
}
