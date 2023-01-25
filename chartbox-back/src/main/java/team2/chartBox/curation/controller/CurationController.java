package team2.chartBox.curation.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import team2.chartBox.curation.dto.CurationDto;
import team2.chartBox.curation.dto.CurationResponse;
import team2.chartBox.curation.service.CurationService;

import java.util.List;

@RestController
@Slf4j
@CrossOrigin(origins = "http://localhost:3000")
public class CurationController {

    @Autowired
    public CurationService curationService;

    @GetMapping("/curation/snow")
    public ResponseEntity curationSnow() {
        List<CurationDto> list = curationService.getCurationCategory("눈오는날");
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/curation/cold")
    public ResponseEntity curationCold() {
        CurationResponse curationCold = curationService.getCurationCold();
        return ResponseEntity.ok().body(curationCold);
    }

    @GetMapping("/curation/rain")
    public ResponseEntity curationRain() {
        List<CurationDto> list = curationService.getCurationCategory("비오는날");
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/curation/homeDate")
    public ResponseEntity curationHomeDate() {
        CurationResponse curationHomeDate = curationService.getCurationHomeDate();
        return ResponseEntity.ok().body(curationHomeDate);
    }

    @GetMapping("/curation/pick1")
    public ResponseEntity curationPick() {
        CurationResponse curationPick1 = curationService.getCurationPick1();
        return ResponseEntity.ok().body(curationPick1);
    }
}
