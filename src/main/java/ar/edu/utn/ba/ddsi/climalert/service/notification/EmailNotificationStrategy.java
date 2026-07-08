package ar.edu.utn.ba.ddsi.climalert.service.notification;

import ar.edu.utn.ba.ddsi.climalert.domain.ClimateData;
import ar.edu.utn.ba.ddsi.climalert.domain.ClimateWarning;
import ar.edu.utn.ba.ddsi.climalert.domain.DeliveryStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailNotificationStrategy implements WarningNotificationStrategy {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailNotificationStrategy.class);
    private final JavaMailSender mailSender;
    private final List<String> recipients;
    private final boolean enabled;

    public EmailNotificationStrategy(
            JavaMailSender mailSender,
            @Value("${climalert.notification.recipients}") List<String> recipients,
            @Value("${climalert.notification.enabled}") boolean enabled) {
        this.mailSender = mailSender;
        this.recipients = recipients;
        this.enabled = enabled;
    }

    @Override
    public void notifyWarning(ClimateWarning warning, ClimateData data) {
        if (!enabled) {
            LOGGER.warn("Notificaciones por email deshabilitadas. Se habria enviado a: {}", recipients);
            warning.setDeliveryStatus(DeliveryStatus.NOT_SENT);
            warning.setNotificationDetails("Notifications disabled");
            return;
        }

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(recipients.toArray(new String[0]));
            message.setSubject("[Climalert] Alerta Critica de Clima en " + data.getLocationName());
            
            String body = String.format(
                    "Alerta Climatica Generada\n\n" +
                    "Ubicacion: %s\n" +
                    "Fecha y Hora: %s\n" +
                    "Temperatura: %s C\n" +
                    "Humedad: %s %%\n",
                    data.getLocationName(), data.getFetchTime(), data.getTemperature(), data.getHumidity()
            );
            
            message.setText(body);
            mailSender.send(message);

            warning.setDeliveryStatus(DeliveryStatus.SENT);
            warning.setNotificationDetails("Email sent to " + recipients.size() + " recipients");
            LOGGER.info("Email de alerta enviado exitosamente");

        } catch (Exception e) {
            LOGGER.error("Error al enviar el email de alerta", e);
            warning.setDeliveryStatus(DeliveryStatus.ERROR);
            warning.setNotificationDetails("Error sending email: " + e.getMessage());
        }
    }
}
