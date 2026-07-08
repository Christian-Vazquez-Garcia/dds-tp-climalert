package ar.edu.utn.ba.ddsi.climalert.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WeatherApiResponseDto {
    private LocationDto location;
    private CurrentDto current;
}
