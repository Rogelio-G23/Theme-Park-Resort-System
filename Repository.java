package com.mycompany.themeparkproject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Repository{ 

// CONNECTION TO DATABASE
    private Connection connection;
    
    private Connection getConnection(){
        return connection;
    }
    
    public Repository() {
        try {
            Class.forName("org.sqlite.JDBC");
            String url = "jdbc:sqlite:C:/Users/Rogelio/Documents/NetBeansProjects/ThemeParkProject/src/ThemePark_Database.db";
            connection = DriverManager.getConnection(url);
            System.out.println("Connected to database!");
        } catch (ClassNotFoundException e) {
            System.out.println("SQLite driver not found. Make sure sqlite-jdbc.jar is in your Libraries.");
        } catch (SQLException e) {
            System.out.println("Connection failed: " + e.getMessage());
        }
    }

    
// ADD TICKET INTO THE DATABASE
    // ADD TICKET INTO THE DATABASE
public void addTicket(Reservation reserve) {
    try {
        String sql = "INSERT INTO TicketDatabase (Customer_Name, Ticket_Type, Contact, Booking_Date)VALUES (?, ?, ?, ?)";
        PreparedStatement stmt = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        stmt.setString(1, reserve.getCustomerName());
        stmt.setString(2, reserve.getTicketType());
        stmt.setString(3, reserve.getContact());
        stmt.setString(4, reserve.getBookingDate());
        stmt.executeUpdate();

        ResultSet keys = stmt.getGeneratedKeys();
        if (keys.next()) {
            System.out.println("Ticket added successfully!");
            System.out.println("Your Customer ID: " + keys.getInt(1));
        }
 
        stmt.close();
    } catch (SQLException e) {
        System.out.println("Error inserting: " + e.getMessage());
    }
}

    
// ADD ROOM BOOKING INTO DATABASE
    public void addRoom(Reservation reserve){
        try {
            String sql = "INSERT INTO RoomDatabase(Room_Number, Customer_Name, Contact, Booking_Date) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = getConnection().prepareStatement(sql);
            stmt.setInt(1, reserve.getRoomNumber());
            stmt.setString(2, reserve.getCustomerName());
            stmt.setString(3, reserve.getContact());
            stmt.setString(4, reserve.getBookingDate());
            stmt.executeUpdate();
            stmt.close();
            System.out.println("Booking confirmed!");
        } catch (SQLException e) {
            System.out.println("Error inserting: " + e.getMessage());
        }
    }
    
    // CHECK IF ROOM IS ALREADY TAKEN
public boolean isRoomTaken(int roomNumber) {
    String sql = "SELECT COUNT(*) FROM RoomDatabase WHERE Room_Number = ?";
    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
        stmt.setInt(1, roomNumber);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return rs.getInt(1) > 0;
        }
    } catch (SQLException e) {
        System.out.println("Query failed: " + e.getMessage());
    }
    return false;
}

    
// FIND CUSTOMER BY ID
    public Reservation findCustomerById(int customerId) {
        String sql = "SELECT Customer_Name, Ticket_Type, Contact FROM TicketDatabase WHERE Customer_Id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, customerId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Reservation.Builder()
                        .setCustomerName(rs.getString("Customer_Name"))
                        .setTicketType(rs.getString("Ticket_Type"))
                        .setContact(rs.getString("Contact"))
                        .build();
            } else {
                System.out.println("No reservation found with that ID.");
                return null;
            }
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            return null;
        }
    }

    
// RIDE REPOSITORY AND STATUS KEEPING 
    public void takeRide(){
        String sql = "SELECT Ride_Id, Ride, Ride_Status FROM RideDatabase";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("============================== THEME PARK RIDES =================================");
            while (rs.next()) {
                int rideId     = rs.getInt("Ride_Id");
                String ride    = rs.getString("Ride");
                String rideStatus = rs.getString("Ride_Status");

                ParkRides ridePark = new ParkRides.Builder()
                                            .setRideId(rideId)
                                            .setRide(ride)
                                            .setRideStatus(rideStatus)
                                            .build();

                System.out.println("Ride Id: " + ridePark.getRideId() +
                    "\t | Ride Name: " + ridePark.getRide() +
                    "\t | Ride Status: " + ridePark.getRideStatus());
            }
            System.out.println("================================================================================");
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
        }
    }

    
// CHECK RIDE STATUS BY ID
    public void checkRideStatus(int rideId) {
        String sql = "SELECT Ride, Ride_Status FROM RideDatabase WHERE Ride_Id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, rideId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String ride   = rs.getString("Ride");
                String status = rs.getString("Ride_Status");

                System.out.println("\n=== RIDE STATUS ===");
                System.out.println("Ride: " + ride);

                switch (status) {
                    case "Open"        -> System.out.println("Status: The ride is open and fully operational. Enjoy the ride!");
                    case "Closed"      -> System.out.println("Status: The ride is closed as of the moment.");
                    case "Maintenance" -> System.out.println("Status: This ride is currently under maintenance.");
                    default            -> System.out.println("Status: Unknown status — " + status);
                }
            } else {
                System.out.println("No ride found with ID: " + rideId);
            }

        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
        }
    }
    
    public void savePayment(String customerName, String ticketType, String paymentMethod, double totalAmount) {

    String sql = "INSERT INTO PaymentDatabase(Customer_Name, Ticket_Type, Payment_Method, Total_Amount, Payment_Date) VALUES (?, ?, ?, ?, ?)";

    try (PreparedStatement stmt = connection.prepareStatement(sql)) {

        stmt.setString(1, customerName);
        stmt.setString(2, ticketType);
        stmt.setString(3, paymentMethod);
        stmt.setDouble(4, totalAmount);
        
        stmt.setString(5,java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        stmt.executeUpdate();

    } catch (SQLException e) {
        System.out.println("Payment insert failed: " + e.getMessage());
    }
}
}
