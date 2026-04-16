package com.mycompany.themeparkproject;
import java.util.Scanner;

public class Services {

// FRONT DESK
    // FRONT DESK
public void frontDesk() {
    Scanner scan = new Scanner(System.in);
    int choice;
    Repository repo = new Repository();
    do {
        System.out.println("\n=== FRONT DESK SERVICE ===");
        System.out.println("[1] RESERVED");
        System.out.println("[2] WALK-IN");
        System.out.println("[0] EXIT");
        System.out.print("Select: ");
        choice = scan.nextInt();

        switch (choice) {
            case 1 -> {
                System.out.print("\nEnter your Customer ID: ");
                int customerId = scan.nextInt();
                Reservation found = repo.findCustomerById(customerId);

                if (found != null) {
                    System.out.println("\n=== WELCOME BACK! ===");
                    System.out.println("Customer Name : " + found.getCustomerName());
                    System.out.println("Ticket Type   : " + found.getTicketType());
                    System.out.println("Contact       : " + found.getContact());

                    if (found.getTicketType().equalsIgnoreCase("VIP")) {
                        System.out.println(">> Redirecting to VIP Menu...");
                        vipMenu();
                    } else if (found.getTicketType().equalsIgnoreCase("Standard")) {
                        System.out.println(">> Redirecting to Standard Menu...");
                        standardMenu();
                    }
                }
            }
            case 2 -> {
                Scanner walkScan = new Scanner(System.in);
                Repository walkRepo = new Repository();

                System.out.println("\n=== WALK-IN ===");
                System.out.println("[1] Standard Pass");
                System.out.println("[2] VIP Pass");
                System.out.print("Select: ");
                int walkChoice = walkScan.nextInt();

                System.out.println("\n=== CUSTOMER DETAILS ===");
                System.out.print("Name: ");
                walkScan.nextLine();
                String walkName = walkScan.nextLine();
                System.out.print("Contact: ");
                String walkContact = walkScan.nextLine();

                if (walkChoice == 1) {
                    Reservation walkReserve = new Reservation.Builder()
                            .setCustomerName(walkName)
                            .setTicketType("Standard")
                            .setContact(walkContact)
                            .build();
                    walkRepo.addTicket(walkReserve);

                    System.out.println("\n=== WALK-IN CONFIRMED ===");
                    System.out.println("Customer Name : " + walkName);
                    System.out.println("Ticket Type   : Standard");
                    System.out.println("Contact       : " + walkContact);
                    System.out.println("Your Customer ID is shown above. Keep it for reference!");
                    standardMenu();

                } else if (walkChoice == 2) {
                    Reservation walkReserve = new Reservation.Builder()
                            .setCustomerName(walkName)
                            .setTicketType("VIP")
                            .setContact(walkContact)
                            .build();
                    walkRepo.addTicket(walkReserve);

                    System.out.println("\n=== WALK-IN CONFIRMED ===");
                    System.out.println("Customer Name : " + walkName);
                    System.out.println("Ticket Type   : VIP");
                    System.out.println("Contact       : " + walkContact);
                    System.out.println("Your Customer ID is shown above. Keep it for reference!");
                    bookRoom();
                    vipMenu();

                } else {
                    System.out.println("Invalid choice.");
                }
            }
            case 0 -> System.out.println("Exiting the program...");
            default -> System.out.println("Invalid choice, Please try again.");
        }
    } while (choice != 0);
}

    
// ROOM BOOKING
    // ROOM BOOKING
public void bookRoom() {
    Scanner scan = new Scanner(System.in);
    Repository repo = new Repository();

    System.out.println("\n=== BOOKING ROOM ===");
    System.out.print("Name: ");
    String customerName = scan.nextLine();
    System.out.print("Contact: ");
    String contact = scan.nextLine();

    // Show available rooms 1-20
    System.out.println("\nAvailable Rooms (1 - 20):");
    System.out.println("Note: Rooms already booked will be marked as [TAKEN]");
    for (int i = 1; i <= 20; i++) {
        if (repo.isRoomTaken(i)) {
            System.out.print("[" + i + " TAKEN] ");
        } else {
            System.out.print("[" + i + "] ");
        }
    }
    System.out.println();

    int roomNumber;
    do {
        System.out.print("\nEnter Room Number (1-20): ");
        roomNumber = scan.nextInt();

        if (roomNumber < 1 || roomNumber > 20) {
            System.out.println("Invalid room number. Please choose between 1 and 20.");
        } else if (repo.isRoomTaken(roomNumber)) {
            System.out.println("Room " + roomNumber + " is already taken. Please choose another room.");
        } else {
            break;
        }
    } while (true);

    Reservation reserve = new Reservation.Builder()
            .setCustomerName(customerName)
            .setContact(contact)
            .setRoomNumber(roomNumber)
            .build();
    repo.addRoom(reserve);
}

    
// RIDES OF THE PARK
    public void useRide() {
        Scanner scan = new Scanner(System.in);
        Repository repo = new Repository();

        repo.takeRide();

        System.out.print("\nEnter Ride ID to check: ");
        int rideId = scan.nextInt();

        repo.checkRideStatus(rideId);
    }

    
// VIP MENU
    public void vipMenu(){
        Scanner scan = new Scanner(System.in);
        int choice;
        
        do {
            System.out.println("\n*** VIP MENU ***");
            System.out.println("[1] Rides");
            System.out.println("[2] Room");
            System.out.println("[0] Exit");
            System.out.print("Select: ");
            choice = scan.nextInt();
            
            switch(choice){
                case 1 -> useRide();
                case 2 -> System.out.println("Room: Enjoy your stay.");
                case 0 -> System.out.println("Goodbye!");
                default -> System.out.println("Invalid choice, Please try again.");
            }
        } while(choice != 0);
    }

    
// STANDARD MENU
    public void standardMenu(){
        Scanner scan = new Scanner(System.in);
        int choice;
        
        do {
            System.out.println("\n--- STANDARD MENU ---");
            System.out.println("[1] Rides");
            System.out.println("[0] Exit");
            System.out.print("Select: ");
            choice = scan.nextInt();
            
            switch(choice){
                case 1 -> useRide();
                case 0 -> System.out.println("Goodbye!");
                default -> System.out.println("Invalid choice, Please try again.");
            }
        } while(choice != 0);
    }
}
