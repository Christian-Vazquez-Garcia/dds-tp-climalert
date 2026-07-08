package ar.edu.utn.ba.ddsi.climalert.cronJob;

import ar.edu.utn.ba.ddsi.climalert.service.WarningAnalyzerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class AnalyzeWarningJob {

    private static final Logger LOGGER = LoggerFactory.getLogger(AnalyzeWarningJob.class);
    private final WarningAnalyzerService warningAnalyzerService;

    public AnalyzeWarningJob(WarningAnalyzerService warningAnalyzerService) {
        this.warningAnalyzerService = warningAnalyzerService;
    }

    @Scheduled(cron = "0 * * * * *")
    public void analyzeWarning() {
        LOGGER.info("Iniciando tarea programada: AnalyzeWarningJob (cada 1 min)");
        warningAnalyzerService.analyzeAndAlert();
    }
}
