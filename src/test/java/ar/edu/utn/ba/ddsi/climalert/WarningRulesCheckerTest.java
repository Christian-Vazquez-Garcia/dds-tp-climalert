package ar.edu.utn.ba.ddsi.climalert;

import ar.edu.utn.ba.ddsi.climalert.domain.ClimateData;
import ar.edu.utn.ba.ddsi.climalert.service.WarningRulesChecker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class WarningRulesCheckerTest {

    private WarningRulesChecker rulesChecker;

    @BeforeEach
    void setUp() {
        // Inicializamos el checker con las reglas del TP (Temp > 35, Humedad > 60)
        rulesChecker = new WarningRulesChecker(35.0, 60.0);
    }

    @Test
    void shouldReturnTrueWhenBothTemperatureAndHumidityAreAboveMax() {
        ClimateData data = new ClimateData();
        data.setTemperature(36.0); // > 35
        data.setHumidity(70.0);    // > 60
        
        assertTrue(rulesChecker.isWarningRequired(data), "Debe saltar alerta si AMBAS pasan el limite");
    }

    @Test
    void shouldReturnFalseWhenOnlyTemperatureIsAboveMax() {
        ClimateData data = new ClimateData();
        data.setTemperature(36.0); // > 35
        data.setHumidity(50.0);    // < 60
        
        assertFalse(rulesChecker.isWarningRequired(data), "No debe saltar alerta porque la humedad es baja");
    }

    @Test
    void shouldReturnFalseWhenOnlyHumidityIsAboveMax() {
        ClimateData data = new ClimateData();
        data.setTemperature(30.0); // < 35
        data.setHumidity(70.0);    // > 60
        
        assertFalse(rulesChecker.isWarningRequired(data), "No debe saltar alerta porque la temperatura es baja");
    }

    @Test
    void shouldReturnFalseWhenEverythingIsNormal() {
        ClimateData data = new ClimateData();
        data.setTemperature(30.0);
        data.setHumidity(50.0);
        
        assertFalse(rulesChecker.isWarningRequired(data), "No debe saltar alerta si todo esta en orden");
    }
}
