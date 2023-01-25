package team2.chartBox.curation.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CurationInfo {
    private String curationLink;
    private String curationTitle;
    private String curationText;
    private String curationCategory;

    public CurationInfo() {
    }
}
