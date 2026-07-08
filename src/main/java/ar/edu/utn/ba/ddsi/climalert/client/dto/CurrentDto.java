package ar.edu.utn.ba.ddsi.climalert.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CurrentDto {
    @JsonProperty("temp_c")
    private double tempC;
    
    private double humidity;
}
