package ar.edu.utn.ba.ddsi.climalert.repository;

import ar.edu.utn.ba.ddsi.climalert.domain.ClimateWarning;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClimateWarningRepository extends JpaRepository<ClimateWarning, Long> {
    
    // Verifica si ya existe una alerta para ese registro de clima
    boolean existsByClimateDataId(Long climateDataId);
}
