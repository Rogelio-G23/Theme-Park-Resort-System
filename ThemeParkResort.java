package com.mycompany.themeparkproject;

import java.util.Scanner;
public class ThemeParkProject {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        Reservation booking = new Reservation.Builder().build();
        Services service = new Services();
        
        System.out.println("WELCOME TO THE THEME PARK RESORT!");
        
        String reserve = "";
        
        do{
            System.out.print("Would you like a reservation? (yes/no): ");
            reserve = scan.nextLine();
            
            if(reserve.equalsIgnoreCase("yes")){
                booking.chooseTicket();
            }else if(reserve.equalsIgnoreCase("no")){
                service.frontDesk();
            }
        }while(!reserve.equalsIgnoreCase("yes") && !reserve.equalsIgnoreCase("no"));
    }
}