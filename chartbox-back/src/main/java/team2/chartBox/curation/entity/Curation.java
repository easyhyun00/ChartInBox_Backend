package team2.chartBox.curation.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "curation")
@Entity
@Getter @Setter
@AllArgsConstructor
public class Curation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "MOVIEID")
    private String movieId;

    @Column(name = "MOVIETITLE")
    private String movieTitle;

    @Column(name = "MOVIEPOSTER")
    private String moviePoster;

    @Column(name = "MOVIEAGE")
    private String movieAge;

    @Column(name = "CURATIONCATEGORY")
    private String curationCategory;

    public Curation() {
    }
}
