package com.suza.safety_map_backend.Controller;

import com.suza.safety_map_backend.Entity.CrimeReport;
import com.suza.safety_map_backend.Service.CrimeReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/crimes")
public class CrimeReportController {
    @Autowired
    CrimeReportService crimeReportService;


    @PostMapping("/report")
    public CrimeReport submit(@RequestBody CrimeReport report) {
        return crimeReportService.submit(report);
    }

    @GetMapping("all")
    public List<CrimeReport> getAllApproved() {
        return crimeReportService.getAllApproved();
    }

    @PutMapping("/{id}/approve")
    public CrimeReport approve(@PathVariable Long id) {
        return crimeReportService.approve(id);
    }

    // GET /api/crimes/nearby?lng=39.50&lat=-6.20&radius=1000
    @GetMapping("/nearby")
    public ResponseEntity<List<CrimeReport>> getNearbyCrimes(
            @RequestParam double lng,
            @RequestParam double lat,
            @RequestParam(defaultValue = "1000") double radius) {
        return ResponseEntity.ok(crimeReportService.getNearbyCrimes(lng, lat, radius));
    }
}
