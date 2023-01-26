package team2.chartBox.freeBoard.dto;

import lombok.Getter;
import lombok.Setter;
import team2.chartBox.movieApi.dto.MvApiDto;

@Getter @Setter
public class MovieInfoDto {
    private String mvPoster;
    private String mvTitle;
    private String mvGenre;
    private String mvRuntime;
    private String mvRating;

    public MovieInfoDto() {
    }

    public MovieInfoDto(MvApiDto mvApiDto) {
        this.mvPoster = mvApiDto.getMvPoster();
        this.mvTitle = mvApiDto.getMvTitle();
        this.mvGenre = mvApiDto.getMvGenre();
        this.mvRuntime = mvApiDto.getMvRuntime();
        this.mvRating = mvApiDto.getMvRating();
    }
}
