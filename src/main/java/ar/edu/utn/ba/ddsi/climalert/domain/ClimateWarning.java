package ar.edu.utn.ba.ddsi.climalert.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "climate_warning")
@Getter
@Setter
public class ClimateWarning {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "climate_data_id")
    private ClimateData climateData;

    private LocalDateTime warningTime;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus;
    
    private String notificationDetails;

    @PrePersist
    protected void onCreate() {
        this.warningTime = LocalDateTime.now();
        if (this.deliveryStatus == null) {
            this.deliveryStatus = DeliveryStatus.NOT_SENT;
        }
    }
}
