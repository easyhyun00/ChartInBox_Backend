package team2.chartBox.curation.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import team2.chartBox.curation.dto.CurationDto;
import team2.chartBox.curation.dto.CurationInfo;
import team2.chartBox.curation.dto.CurationResponse;
import team2.chartBox.curation.service.CurationService;

import java.util.List;

@RestController
@Slf4j
@CrossOrigin(origins = "http://localhost:3000")
public class CurationController {

    @Autowired
    public CurationService curationService;

    @GetMapping("/curation/cold")
    public ResponseEntity curationCold() {
        CurationResponse curationResponse = new CurationResponse();
        CurationInfo curationCold = curationService.getCurationCold();
        curationResponse.setCurationInfo(curationCold);
        curationResponse.setCurationMovie(curationService.getCurationCategory(curationCold.getCurationCategory()));
        return ResponseEntity.ok().body(curationResponse);
    }

    @GetMapping("/curation/homeDate")
    public ResponseEntity curationHomeDate() {
        CurationResponse curationResponse = new CurationResponse();
        CurationInfo curationHomeDate = curationService.getCurationHomeDate();
        curationResponse.setCurationInfo(curationHomeDate);
        curationResponse.setCurationMovie(curationService.getCurationCategory(curationHomeDate.getCurationCategory()));
        return ResponseEntity.ok().body(curationResponse);
    }

    @GetMapping("/curation/pick1")
    public ResponseEntity curationPick() {
        CurationResponse curationResponse = new CurationResponse();
        CurationInfo curationPick1 = curationService.getCurationPick1();
        curationResponse.setCurationInfo(curationPick1);
        curationResponse.setCurationMovie(curationService.getCurationCategory(curationPick1.getCurationCategory()));
        return ResponseEntity.ok().body(curationResponse);
    }

    @GetMapping("/curation/pick2")
    public ResponseEntity curationPick2() {
        CurationResponse curationResponse = new CurationResponse();
        CurationInfo curationPick2 = curationService.getCurationPick2();
        curationResponse.setCurationInfo(curationPick2);
        curationResponse.setCurationMovie(curationService.getCurationCategory(curationPick2.getCurationCategory()));
        return ResponseEntity.ok().body(curationResponse);
    }

    @GetMapping("/curation/pick3")
    public ResponseEntity curationPick3() {
        CurationResponse curationResponse = new CurationResponse();
        CurationInfo curationPick3 = curationService.getCurationPick3();
        curationResponse.setCurationInfo(curationPick3);
        curationResponse.setCurationMovie(curationService.getCurationCategory(curationPick3.getCurationCategory()));
        return ResponseEntity.ok().body(curationResponse);
    }

    @GetMapping("/curation/sad")
    public ResponseEntity curationSad() {
        CurationResponse curationResponse = new CurationResponse();
        CurationInfo curationSad = curationService.getCurationSad();
        curationResponse.setCurationInfo(curationSad);
        curationResponse.setCurationMovie(curationService.getCurationCategory(curationSad.getCurationCategory()));
        return ResponseEntity.ok().body(curationResponse);
    }
}
