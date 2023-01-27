package team2.chartBox.curation.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class CurationResponse {
    private Object curationMovie;
    private Object curationInfo;

    public CurationResponse() {
    }
}
