package com.mycompany.themeparkproject;


public class TicketPayment extends PaymentFramework {

    private Repository repo;
    private Reservation reservation;

    public TicketPayment(double amount,
                         double discountRate,
                         String paymentMethod,
                         Reservation reservation) {

        this.amount = amount;
        this.discountRate = discountRate;
        this.paymentMethod = paymentMethod;
        this.reservation = reservation;

        repo = new Repository();
    }

    @Override
    protected boolean validatePaymentMethod() {

        return paymentMethod.equalsIgnoreCase("Cash")
                || paymentMethod.equalsIgnoreCase("GCash")
                || paymentMethod.equalsIgnoreCase("Card");
    }

    @Override
    protected void finalizeTransaction() {

        double vatInclusive = applyVatRate(amount);
        double finalAmount = applyDiscount(vatInclusive);

        repo.savePayment(
                reservation.getCustomerName(),
                reservation.getTicketType(),
                paymentMethod,
                finalAmount
        );

        System.out.println("Payment successfully saved to database.");
    }
}
======================== end of ticketPayment class ===================

package com.mycompany.themeparkproject;


public class TicketPayment extends PaymentFramework {

    private Repository repo;
    private Reservation reservation;

    public TicketPayment(double amount,
                         double discountRate,
                         String paymentMethod,
                         Reservation reservation) {

        this.amount = amount;
        this.discountRate = discountRate;
        this.paymentMethod = paymentMethod;
        this.reservation = reservation;

        repo = new Repository();
    }

    @Override
    protected boolean validatePaymentMethod() {

        return paymentMethod.equalsIgnoreCase("Cash")
                || paymentMethod.equalsIgnoreCase("GCash")
                || paymentMethod.equalsIgnoreCase("Card");
    }

    @Override
    protected void finalizeTransaction() {

        double vatInclusive = applyVatRate(amount);
        double finalAmount = applyDiscount(vatInclusive);

        repo.savePayment(
                reservation.getCustomerName(),
                reservation.getTicketType(),
                paymentMethod,
                finalAmount
        );

        System.out.println("Payment successfully saved to database.");
    }
}