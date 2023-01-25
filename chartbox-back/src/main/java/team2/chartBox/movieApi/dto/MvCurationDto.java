package team2.chartBox.movieApi.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MvCurationDto {
    private String curationTitle;
    private String curationPoster;
    private String curationUrl;

    public MvCurationDto() {
    }
}
