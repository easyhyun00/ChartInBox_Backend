package team2.chartBox.movieApi.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MvSearchDto {
    private String searchTitle;
    private Object searchList;

    public MvSearchDto() {
    }
}
