package com.grupo4.restaurante.services;

import jakarta.annotation.PostConstruct;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


import java.util.Objects;
import java.util.concurrent.CompletableFuture;
/**
 * Servicio encargado de la lógica de envío de correos electrónicos.
 * Utiliza JavaMailSender de Spring para interactuar con el servidor SMTP.
 * Proporciona un mé to-do para enviar mensajes de texto plano y HTML.
 *
 * @autor David De La Puente Enriquez - KirgoDev
 * @version 1.0
 * @since 2025-07-23
 */

@RequiredArgsConstructor
@Service
public class EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);
    private final JavaMailSender mailSender;

    // Obtiene la dirección de correo desde la que se enviarán los emails.
    // Configurada en application.properties (spring.mail.username).
    @Getter
    @Value("${spring.mail.username:no-reply@example.com}")
    private String fromEmail;

    @PostConstruct
    private void validateConfig() {
        if (fromEmail == null || fromEmail.isBlank()) {
            logger.warn("No se ha configurado spring.mail.username - se usará '{}'", fromEmail);
        }
    }

    /*
     * Envía un correo electrónico de texto plano.
     *
     * @param to Dirección del correo del destinatario.
     * @param subject Asunto del correo.
     * @param text Cuerpo del mensaje del correo.
     * @return true si el correo se envió con éxito, false en caso contrario.
     */
    @Async
    public void sendSimpleEmail(String to, String subject, String text) {
        if (to == null || to.isBlank()){
            logger.warn("sendSimpleEmail: destinatario vacío, no se envía correo.");
            return;
        }
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(to.trim().toLowerCase());
            message.setSubject(subject);
            message.setText(text);
            mailSender.send(message);
            logger.info("Correo de texto plano enviado con éxito a '{}' con asunto '{}'", to, subject);
        } catch (MailException e) {
            logger.error("Error al enviar el correo de texto plano a '{}' con asunto '{}' : {}", to, subject, e.getMessage());
        }
    }
    /*
     * Envío fire-and-forget (void).
     *
     * @param to Dirección de correo del destinatario.
     * @param subject Asunto del correo.
     * @param text Cuerpo del mensaje del correo.
     * @return true si el correo se envió con éxito, false en caso contrario.
     */
    @Async
    public void sendHtmlEmailAsync(String to, String subject, String htmlBody) {
        try {
            sendHtmlEmailInternal(to, subject, htmlBody);
            logger.info("Correo HTML enviado con éxito a '{}' con asunto '{}'", to, subject);

        } catch (MessagingException | MailException e) {
            // Captura cualquier excepción relacionada con el envío de correo.
            logger.error("Error al enviar correo HTML a '{}' con asunto '{}'", to, subject, e);
        }
    }

    /**
     * Envío con resultado: devuelve CompletableFuture para poder comprobar éxito/fallo.
     */
    @Async
    public CompletableFuture<Void> sendHtmlEmailAsyncResult(String to, String subject, String htmlBody) {
        try {
            sendHtmlEmailInternal(to, subject, htmlBody);
            logger.info("sendHtmlEmailAsyncWithResult: enviado correctamente a '{}' con asunto '{}'", to, subject);
            return CompletableFuture.completedFuture(null);
        } catch (Exception e) {
            logger.error("sendHtmlEmailAsyncWithResult: error enviado a '{}' con asunto '{}'", to, subject, e);
            return CompletableFuture.failedFuture(e);
        }
    }

    // helper que realiza el envío real (centraliza validaciones)
    private void sendHtmlEmailInternal(String to, String subject, String htmlBody) throws MessagingException {
        if (to == null || to.isBlank()) {
            throw new IllegalArgumentException("Destinatario vacío");
        }
        Objects.requireNonNull(subject, "Subject no puede ser null");
        Objects.requireNonNull(htmlBody, "Body no puede ser null");

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setFrom(fromEmail);
        helper.setTo(to.trim().toLowerCase());
        helper.setSubject(subject);
        helper.setText(htmlBody, true);
        mailSender.send(message);
    }
    /*
     * Envía un correo de confirmación/respuesta al remitente del formulario de contacto.
     * @param toEmail El email del usuario que envió el formulario.
     * @param userName El nombre del usuario.
     * @param userMessage El mensaje original del usuario.
     * @return true si el correo se envió con éxito, false en caso contrario.
     */
    public void sendContactConfirmationEmail(String toEmail, String userName, String userMessage) {
        String subject = "Confirmación de tu mensaje a Restaurante Grupo 4";
        String body = String.format(
                        """
                            Hola %s%n%n
                            Hemos recibido tu contacto con el siguiente contenido:
                            %n%n"%s"%n%n
                            Agradecemos tu contacto y nos pondremos en contacto contigo lo antes posible.
                            %n%nSaludos cordiales,%n
                            El equipo de Restaurante Grupo 4
                        """,
                userName == null ? "" : userName, userMessage == null ? "" : userMessage);
        sendSimpleEmail(toEmail, subject, body);
    }
}
