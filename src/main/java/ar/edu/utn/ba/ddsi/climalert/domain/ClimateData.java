package ar.edu.utn.ba.ddsi.climalert.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "climate_data")
@Getter
@Setter
public class ClimateData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String locationName;
    private double temperature;
    private double humidity;
    
    private LocalDateTime fetchTime;
    
    // Store JSON response for debugging if needed, but not required for email
    @Column(columnDefinition = "TEXT")
    private String rawData;

    @PrePersist
    protected void onCreate() {
        this.fetchTime = LocalDateTime.now();
    }
}
