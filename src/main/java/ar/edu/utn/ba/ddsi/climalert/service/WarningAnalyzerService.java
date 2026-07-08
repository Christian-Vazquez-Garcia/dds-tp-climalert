package ar.edu.utn.ba.ddsi.climalert.service;

import ar.edu.utn.ba.ddsi.climalert.domain.ClimateData;
import ar.edu.utn.ba.ddsi.climalert.domain.ClimateWarning;
import ar.edu.utn.ba.ddsi.climalert.repository.ClimateDataRepository;
import ar.edu.utn.ba.ddsi.climalert.repository.ClimateWarningRepository;
import ar.edu.utn.ba.ddsi.climalert.service.notification.WarningNotificationStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class WarningAnalyzerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(WarningAnalyzerService.class);
    
    private final ClimateDataRepository dataRepository;
    private final ClimateWarningRepository warningRepository;
    private final WarningRulesChecker rulesChecker;
    private final WarningNotificationStrategy notificationStrategy;

    public WarningAnalyzerService(
            ClimateDataRepository dataRepository,
            ClimateWarningRepository warningRepository,
            WarningRulesChecker rulesChecker,
            WarningNotificationStrategy notificationStrategy) {
        this.dataRepository = dataRepository;
        this.warningRepository = warningRepository;
        this.rulesChecker = rulesChecker;
        this.notificationStrategy = notificationStrategy;
    }

    @Transactional
    public void analyzeAndAlert() {
        Optional<ClimateData> latestDataOpt = dataRepository.findTopByOrderByFetchTimeDesc();

        if (latestDataOpt.isEmpty()) {
            LOGGER.info("No hay datos climaticos disponibles para analizar");
            return;
        }

        ClimateData latestData = latestDataOpt.get();

        if (!rulesChecker.isWarningRequired(latestData)) {
            LOGGER.info("El clima es normal: {}C, {}% humedad", latestData.getTemperature(), latestData.getHumidity());
            return;
        }

        if (warningRepository.existsByClimateDataId(latestData.getId())) {
            LOGGER.info("Ya se habia generado una alerta para el registro de datos id {}", latestData.getId());
            return;
        }

        ClimateWarning warning = new ClimateWarning();
        warning.setClimateData(latestData);
        
        warning = warningRepository.save(warning);
        
        notificationStrategy.notifyWarning(warning, latestData);
        
        warningRepository.save(warning);
        LOGGER.warn("Alerta creada para el registro de datos id {} con estado {}", latestData.getId(), warning.getDeliveryStatus());
    }
}
