package com.harrishjoshi.microservices.notification.event;

public record OrderPlacedEvent(
        String orderNumber,
        String email,
        String firstName,
        String lastName
) {
}
