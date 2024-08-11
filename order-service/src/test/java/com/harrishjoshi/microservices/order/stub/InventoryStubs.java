package com.harrishjoshi.microservices.order.stub;

import lombok.experimental.UtilityClass;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

@UtilityClass
public class InventoryStubs {

    public void stubInventoryCall(String skuCode, Integer quantity) {
        stubFor(get(urlEqualTo("/api/v1/inventory?skuCode=" + skuCode + "&quantity=" + quantity))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("true")));
    }
}
