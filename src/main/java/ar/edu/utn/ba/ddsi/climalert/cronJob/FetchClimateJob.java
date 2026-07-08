package ar.edu.utn.ba.ddsi.climalert.cronJob;

import ar.edu.utn.ba.ddsi.climalert.service.ClimateManagerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class FetchClimateJob {

    private static final Logger LOGGER = LoggerFactory.getLogger(FetchClimateJob.class);
    private final ClimateManagerService climateManagerService;

    public FetchClimateJob(ClimateManagerService climateManagerService) {
        this.climateManagerService = climateManagerService;
    }

    // Ejecuta cada 5 minutos
    @Scheduled(cron = "0 */5 * * * *")
    public void fetchClimate() {
        LOGGER.info("Iniciando tarea programada: FetchClimateJob (cada 5 min)");
        climateManagerService.fetchAndSaveCurrentClimate();
    }
}
