package team2.chartBox.curation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team2.chartBox.curation.entity.Curation;

import java.util.List;

public interface CurationRepository extends JpaRepository<Curation, Long> {
    List<Curation> findAllByCurationCategory(String curationCategory);
}

