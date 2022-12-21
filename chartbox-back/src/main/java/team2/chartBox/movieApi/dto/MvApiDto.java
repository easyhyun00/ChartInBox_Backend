package team2.chartBox.movieApi.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MvApiDto {
    private String mvTitle;
    private String mvTitleEng;
    private String mvTitleOrg;
    private String mvPlot;
    private String mvGenre;
    private String mvRuntime;
    private String mvRating;
    private String mvNation;
    private String mvProdYear;
    private String mvPoster;
    private String[] mvActor;
    private String[] mvDirector;

    public MvApiDto() {
    }

    public MvApiDto(String mvTitle, String mvTitleEng, String mvTitleOrg, String mvPlot, String mvGenre, String mvRuntime, String mvRating, String mvNation, String mvProdYear, String mvPoster, String[] mvActor, String[] mvDirector) {
        this.mvTitle = mvTitle;
        this.mvTitleEng = mvTitleEng;
        this.mvTitleOrg = mvTitleOrg;
        this.mvPlot = mvPlot;
        this.mvGenre = mvGenre;
        this.mvRuntime = mvRuntime;
        this.mvRating = mvRating;
        this.mvNation = mvNation;
        this.mvProdYear = mvProdYear;
        this.mvPoster = mvPoster;
        this.mvActor = mvActor;
        this.mvDirector = mvDirector;
    }
}


