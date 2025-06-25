package com.grupo4.restaurante.services;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * Servicio encargado de la lógica de envío de correos electrónicos.
 * Utiliza JavaMailSender de Spring para interactuar con el servidor SMTP.
 * Proporciona un mé to-do para enviar mensajes de texto plano.
 *
 * @autor David De La Puente Enriquez - KirgoDev
 * @version 1.0
 * @since 2025-06-25
 */

@RequiredArgsConstructor
@Service
public class EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);
    private final JavaMailSender mailSender;
    // Obtiene la dirección de correo desde la que se enviarán los emails.
    // Configurada en application.properties (spring.mail.username).
    @Getter
    @Value("${spring.mail.username}")
    private String fromEmail;

    /*
     * Envía un correo electrónico de texto plano.
     *
     * @param to Dirección de correo del destinatario.
     * @param subject Asunto del correo.
     * @param text Cuerpo del mensaje del correo.
     * @return true si el correo se envió con éxito, false en caso contrario.
     */
    public boolean sendSimpleEmail(String to, String subject, String text) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);

            mailSender.send(message);
            logger.info("Correo enviado con éxito a '{}' con asunto '{}'", to, subject);
            return true;
        } catch (MailException e) {
            // Captura cualquier excepción relacionada con el envío de correo.
            logger.error("Error al enviar correo a '{}' con asunto '{}' : {}", to, subject, e.getMessage());
            return false;
        }
    }

    /*
     * Envía un correo de confirmación/respuesta al remitente del formulario de contacto.
     * @param toEmail El email del usuario que envió el formulario.
     * @param userName El nombre del usuario.
     * @param userMessage El mensaje original del usuario.
     * @return true si el correo se envió con éxito, false en caso contrario.
     */
    public boolean sendContactConfirmationEmail(String toEmail, String userName, String userMessage) {
        String subject = "Confirmación de tu mensaje a Restaurante Grupo 4";
        String body = String.format(
                """
                        Hola %s,\s
                        
                        Hemos recibido tu contacto con el siguiente contenido:
                        
                        "%s\\n
                        Agradecemos tu contacto y nos pondremos en contacto contigo lo antes posible.
                        
                        Saludos cordiales,
                        El equipo de Restaurante Grupo 4""",
                userName, userMessage
        );
        return sendSimpleEmail(toEmail, subject, body);
    }
}
