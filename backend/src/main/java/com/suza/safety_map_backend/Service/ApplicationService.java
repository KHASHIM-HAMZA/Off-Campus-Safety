package com.suza.safety_map_backend.Service;

import com.suza.safety_map_backend.Entity.HostelApplication;
import com.suza.safety_map_backend.Repository.HostelApplicationRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ApplicationService {
    private final HostelApplicationRepository applicationRepository;

    public ApplicationService(HostelApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }

    public HostelApplication apply(HostelApplication application) {
        return applicationRepository.save(application);
    }

    public List<HostelApplication> getAll() {
        return applicationRepository.findAll();
    }

    public HostelApplication updateStatus(Long id, HostelApplication.Status status) {
        HostelApplication app = applicationRepository.findById(id).orElseThrow();
        app.setStatus(status);
        return applicationRepository.save(app);
    }
}
