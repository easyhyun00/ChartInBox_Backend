package team2.chartBox.curation.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;

@Getter @Setter
public class CurationDto {

    private String movieId;
    private String movieTitle;
    private String moviePoster;
    private String movieAge;

    public CurationDto() {
    }
}
