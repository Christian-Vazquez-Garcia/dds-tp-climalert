package ar.edu.utn.ba.ddsi.climalert.repository;

import ar.edu.utn.ba.ddsi.climalert.domain.ClimateData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClimateDataRepository extends JpaRepository<ClimateData, Long> {
    
    // Trae el ultimo registro guardado
    Optional<ClimateData> findTopByOrderByFetchTimeDesc();
}
