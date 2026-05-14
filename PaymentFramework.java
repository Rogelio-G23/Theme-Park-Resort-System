package com.mycompany.themeparkproject;

public abstract class PaymentFramework {

    protected double amount;
    protected double discountRate;
    protected double balance;
    protected String paymentMethod;

    protected final double VAT_RATE = 0.12;


    protected abstract boolean validatePaymentMethod();

    protected abstract void finalizeTransaction();


    protected double applyVatRate(double amount) {
        return amount * (1 + VAT_RATE);
    }

    protected double applyDiscount(double amount) {
        return amount * (1 - discountRate);
    }


    public void processInvoice() {
        System.out.println("=== Processing Invoice ===");
        System.out.println("Original amount: PHP " + String.format("%.2f", amount));

        // validation
        if (!validatePaymentMethod()) {
            System.out.println("Payment validation failed. Transaction aborted.");
            return;
        }
        System.out.println("Payment validated successfully.");
        System.out.println();

        // vat-inclusive computation
        double vatInclusiveAmount = applyVatRate(amount);
        double vatAdded           = vatInclusiveAmount - amount;
        System.out.println("VAT (" + (int)(VAT_RATE * 100) + "%): +PHP " + String.format("%.2f", vatAdded));
        System.out.println("VAT-inclusive total: PHP " + String.format("%.2f", vatInclusiveAmount));

        // give discount
        double finalAmount   = applyDiscount(vatInclusiveAmount);
        double discountSaved = vatInclusiveAmount - finalAmount;
        System.out.println("Discount (" + (int)(discountRate * 100) + "%): -PHP " + String.format("%.2f", discountSaved));
        System.out.println("Total due: PHP " + String.format("%.2f", finalAmount));
        System.out.println();

        // finalize transaction
        finalizeTransaction();
        System.out.println("=== Invoice Complete ===");
        System.out.println();
    }
