package com.suza.safety_map_backend.Service;


import com.suza.safety_map_backend.Entity.AccidentHotspot;
import com.suza.safety_map_backend.Entity.CrimeReport;
import com.suza.safety_map_backend.Repository.AccidentHotspotRepository;
import com.suza.safety_map_backend.Repository.CrimeReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CrimeReportService {
    private final CrimeReportRepository crimeReportRepository;

    @Autowired
    private AccidentHotspotRepository accidentHotspotRepository;

    public CrimeReportService(CrimeReportRepository crimeReportRepository) {
        this.crimeReportRepository = crimeReportRepository;
    }

    public CrimeReport submit(CrimeReport report) {
        return crimeReportRepository.save(report);
    }

    public List<CrimeReport> getAllApproved() {
        return crimeReportRepository.findAll()
                .stream()
                .filter(CrimeReport::isApproved)
                .toList();
    }

    public CrimeReport approve(Long id) {
        CrimeReport report = crimeReportRepository.findById(id).orElseThrow();
        report.setApproved(true);
        return crimeReportRepository.save(report);
    }

    // Find crime reports near a student’s location
    public List<CrimeReport> getNearbyCrimes(double lng, double lat, double radius) {
        return crimeReportRepository.findNearbyCrimes(lng, lat, radius);
    }

    public List<AccidentHotspot> getUnsafeAreas() {
        return accidentHotspotRepository.findAll();
    }


//    private void checkAutoHostile(Point location) {
//        long count = repository.countReportsWithin100m(location);
//        if (count >= 6) {
//            repository.findAll().stream()
//                    .filter(r -> !r.isApproved() && // Check distance in PostGIS
//                            // Add spatial query in JPA or native SQL
//                            true) // Replace with ST_DWithin check
//                    .forEach(r -> {
//                        r.setApproved(true);
//                        r.setRating(5);
//                        repository.save(r);
//                    });
//        }
//    }
}