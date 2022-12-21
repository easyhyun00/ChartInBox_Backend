package team2.chartBox.schedul.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "moviechart")
@Entity
@Getter @Setter
@AllArgsConstructor
public class MovieChart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MVCHARTID")
    private Integer mvChartId;

    @Column(name = "MVCHARTNAME")
    private String mvChartName;

    @Column(name = "MVCHARTAGE")
    private String mvChartAge;

    @Column(name = "MVCHARTIMG")
    private String mvChartImg;

    @Column(name = "MVCHARTRANK")
    private Integer mvChartRank;

    @Column(name = "MVCHARTCATEGORY")
    private String mvChartCategory;

    @Column(name = "MOVIEID")
    private String movieId;

    public MovieChart() {
    }

    public MovieChart(String mvChartName, String mvChartAge, String mvChartImg, Integer mvChartRank, String mvChartCategory) {
        this.mvChartName = mvChartName;
        this.mvChartAge = mvChartAge;
        this.mvChartImg = mvChartImg;
        this.mvChartRank = mvChartRank;
        this.mvChartCategory = mvChartCategory;
        this.movieId = null;
    }
}
