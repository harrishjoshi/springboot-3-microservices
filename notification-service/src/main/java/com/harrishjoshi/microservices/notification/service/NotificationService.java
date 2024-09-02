package com.harrishjoshi.microservices.notification.service;

import com.harrishjoshi.microservices.notification.event.OrderPlacedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificationService {

    private final JavaMailSender javaMailSender;

    public NotificationService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendNotification(OrderPlacedEvent orderPlacedEvent) {
        log.info("OrderPlacedEvent: {}", orderPlacedEvent);
        var messagePreparator = (MimeMessagePreparator) mimeMessage -> {
            var messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("mailtrap@example.com");
            messageHelper.setTo(orderPlacedEvent.email());
            messageHelper.setSubject(String.format("Your Order with OrderNumber %s is placed successfully", orderPlacedEvent.orderNumber()));
            messageHelper.setText(String.format("""
                            Hi %s, %s
                            
                            Your order with order number %s is now placed successfully.
                            
                            Best Regards,
                            XYZ
                            """,
                    orderPlacedEvent.firstName(),
                    orderPlacedEvent.lastName(),
                    orderPlacedEvent.orderNumber()));
        };

        try {
            javaMailSender.send(messagePreparator);
            log.info("Email sent successfully to: {}", orderPlacedEvent.email());
        } catch (MailException e) {
            log.error("Error while sending email: {}", e.getMessage());
        }
    }
}