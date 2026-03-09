package com.example.payments;

import java.util.*;

public class FastPayAdapter implements PaymentGateway{
    private final FastPayClient client;

    public FastPayAdapter(FastPayClient client) {
        this.client = Objects.requireNonNull(client, "FastPayClient cannot be null");
    }

    @Override
    public String charge(String customerId, int amountCents){
        return client.payNow(customerId, amountCents);
    }
}
