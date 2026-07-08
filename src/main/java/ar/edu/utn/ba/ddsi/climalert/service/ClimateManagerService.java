package ar.edu.utn.ba.ddsi.climalert.service;

import ar.edu.utn.ba.ddsi.climalert.client.WeatherApiGateway;
import ar.edu.utn.ba.ddsi.climalert.client.dto.WeatherApiResponseDto;
import ar.edu.utn.ba.ddsi.climalert.domain.ClimateData;
import ar.edu.utn.ba.ddsi.climalert.repository.ClimateDataRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClimateManagerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClimateManagerService.class);
    private final WeatherApiGateway weatherApiGateway;
    private final ClimateDataRepository climateDataRepository;
    private final ObjectMapper objectMapper;

    public ClimateManagerService(WeatherApiGateway weatherApiGateway, 
                                 ClimateDataRepository climateDataRepository,
                                 ObjectMapper objectMapper) {
        this.weatherApiGateway = weatherApiGateway;
        this.climateDataRepository = climateDataRepository;
        this.objectMapper = objectMapper;
    }

    @Transactional
    public void fetchAndSaveCurrentClimate() {
        WeatherApiResponseDto response = weatherApiGateway.fetchCurrentWeather();
        
        if (response != null && response.getCurrent() != null && response.getLocation() != null) {
            ClimateData data = new ClimateData();
            data.setLocationName(response.getLocation().getName());
            data.setTemperature(response.getCurrent().getTempC());
            data.setHumidity(response.getCurrent().getHumidity());
            
            try {
                data.setRawData(objectMapper.writeValueAsString(response));
            } catch (JsonProcessingException e) {
                LOGGER.warn("No se pudo serializar la respuesta en bruto");
            }
            
            climateDataRepository.save(data);
            LOGGER.info("Datos climaticos guardados para {}", data.getLocationName());
        } else {
            LOGGER.warn("Respuesta vacia o invalida de Weather API");
        }
    }
}
