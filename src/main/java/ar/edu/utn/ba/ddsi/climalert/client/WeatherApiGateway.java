package ar.edu.utn.ba.ddsi.climalert.client;

import ar.edu.utn.ba.ddsi.climalert.client.dto.WeatherApiResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class WeatherApiGateway {

    private static final Logger LOGGER = LoggerFactory.getLogger(WeatherApiGateway.class);

    private final RestTemplate restTemplate;
    private final String apiUrl;
    private final String apiKey;
    private final String location;

    public WeatherApiGateway(
            RestTemplate restTemplate,
            @Value("${weather.api.url}") String apiUrl,
            @Value("${weather.api.key}") String apiKey,
            @Value("${weather.api.location}") String location) {
        this.restTemplate = restTemplate;
        this.apiUrl = apiUrl;
        this.apiKey = apiKey;
        this.location = location;
    }

    public WeatherApiResponseDto fetchCurrentWeather() {
        String url = String.format("%s?key=%s&q=%s", apiUrl, apiKey, location);
        LOGGER.info("Obteniendo clima desde {}", url.replace(apiKey, "HIDDEN_KEY"));
        
        try {
            return restTemplate.getForObject(url, WeatherApiResponseDto.class);
        } catch (Exception e) {
            LOGGER.error("Error obteniendo datos del clima desde la API", e);
            return null;
        }
    }
}
