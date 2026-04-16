package com.mycompany.themeparkproject;

import java.util.Scanner;
public class Reservation {
    private final String customerName;
    private final String ticketType;
    private final int roomNumber;
    private final String contact;
    
    private Reservation(Builder builder){
        this.customerName = builder.customerName;
        this.ticketType = builder.ticketType;
        this.roomNumber = builder.roomNumber;
        this.contact = builder.contact;
    }
    
    public String getCustomerName(){
        return customerName;
    }
    
    public String getTicketType(){
        return ticketType;
    }
    
    public int getRoomNumber(){
        return roomNumber;
    }
    
    public String getContact(){
        return contact;
    }
    
    public static class Builder {
        private String customerName;
        private String ticketType;
        private int roomNumber;
        private String contact;
        
        public Builder setCustomerName(String customerName){
            this.customerName = customerName;
            return this;
        }
        
        public Builder setTicketType(String ticketType){
            this.ticketType = ticketType;
            return this;
        }
        
        public Builder setRoomNumber(int roomNumber){
            this.roomNumber = roomNumber;
            return this;
        }
        
        public Builder setContact(String contact){
            this.contact = contact;
            return this;
        }
        
        public Reservation build(){
            return new Reservation(this);
        }
    }
    
    public void chooseTicket() {
        Scanner scan = new Scanner(System.in);
        int choice;
        System.out.println("\n=== TICKET BOOTH ===");
        System.out.println("[1] Standard Pass");
        System.out.println("[2] VIP Pass");
        System.out.println("[0] Exit");
        System.out.print("Select: ");
        choice = scan.nextInt();
        
        switch(choice){
            case 1 -> addCustomerStandard();
            case 2 -> addCustomerVip();
            case 0 -> System.out.println("Thank you for visiting!");
            default -> System.out.println("Invalid choice, Please try again.");
        }
    }
    
    public void addCustomerStandard(){
        Scanner scan = new Scanner(System.in);
        Repository repo = new Repository();
        Services service = new Services();
    
        System.out.println("\n=== CUSTOMER DETAILS ===");
        System.out.print("Name: ");
        String customerName = scan.nextLine();
        System.out.print("Contact: ");
        String contact = scan.nextLine();
    
        Reservation reserve = new Reservation.Builder()
                .setCustomerName(customerName)
                .setTicketType("Standard")
                .setContact(contact)
                .build();
                
        repo.addTicket(reserve);

        System.out.println("\n=== RESERVATION CONFIRMED ===");
        System.out.println("Customer Name : " + customerName);
        System.out.println("Ticket Type   : Standard");
        System.out.println("Contact       : " + contact);
        System.out.println("Please remember your Customer ID for Front Desk check-in!");

        service.frontDesk();
    }
    
    public void addCustomerVip(){
        Scanner scan = new Scanner(System.in);
        Repository repo = new Repository();
        Services service = new Services();
        
        System.out.println("\n=== CUSTOMER DETAILS ===");
        System.out.print("Name: ");
        String customerName = scan.nextLine();
        System.out.print("Contact: ");
        String contact = scan.nextLine();
            
        Reservation reserve = new Reservation.Builder()
                .setCustomerName(customerName)
                .setTicketType("VIP")
                .setContact(contact)
                .build();
                
        repo.addTicket(reserve);

        System.out.println("\n=== RESERVATION CONFIRMED ===");
        System.out.println("Customer Name : " + customerName);
        System.out.println("Ticket Type   : VIP");
        System.out.println("Contact       : " + contact);
        System.out.println("Please remember your Customer ID for Front Desk check-in!");

        service.bookRoom();
        service.frontDesk();
    }
}
