package ar.edu.utn.ba.ddsi.climalert.service;

import ar.edu.utn.ba.ddsi.climalert.domain.ClimateData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class WarningRulesChecker {

    private final double maxTemperature;
    private final double minHumidity;

    public WarningRulesChecker(
            @Value("${climalert.alert.max-temperature}") double maxTemperature,
            @Value("${climalert.alert.min-humidity}") double minHumidity) {
        this.maxTemperature = maxTemperature;
        this.minHumidity = minHumidity;
    }

    public boolean isWarningRequired(ClimateData data) {
        return data.getTemperature() > maxTemperature && data.getHumidity() > minHumidity;
    }
}
