package ar.edu.utn.ba.ddsi.climalert.service.notification;

import ar.edu.utn.ba.ddsi.climalert.domain.ClimateData;
import ar.edu.utn.ba.ddsi.climalert.domain.ClimateWarning;

public interface WarningNotificationStrategy {
    void notifyWarning(ClimateWarning warning, ClimateData data);
}
