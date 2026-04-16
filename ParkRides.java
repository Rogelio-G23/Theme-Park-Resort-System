package com.mycompany.themeparkproject;

public class ParkRides {
    private final int rideId;
    private final String ride;
    private final String rideStatus;
    
    private ParkRides(Builder builder){
        this.rideId = builder.rideId;
        this.ride = builder.ride;
        this.rideStatus = builder.rideStatus;
    }
    
    public int getRideId(){
        return rideId;
    }
    public String getRide(){
        return ride;
    }
    public String getRideStatus(){
        return rideStatus;
    }
    
    public static class Builder{
        private int rideId;
        private String ride;
        private String rideStatus;
        
        public Builder setRideId(int rideId){
            this.rideId = rideId;
            return this;
        }
        public Builder setRide(String ride){
            this.ride = ride;
            return this;
        }
        public Builder setRideStatus(String rideStatus){
            this.rideStatus = rideStatus;
            return this;
        }
        public ParkRides build(){
            return new ParkRides(this);
        }
    }
}
