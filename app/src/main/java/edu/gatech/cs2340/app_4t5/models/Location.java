package edu.gatech.cs2340.app_4t5.models;

public class Location {
    private String key;
    private String name;
    private String lat;
    private String longitude;
    private String address;
    private String city;
    private String state;
    private String zip;
    private String type;
    private String phone;
    private String website;

    public Location(String key, String name, String lat, String longitude,
                    String address, String city, String state, String zip,
                    String type, String phone, String website) {
        this.key = key;
        this.name = name;
        this.lat = lat;
        this.longitude = longitude;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.type = type;
        this.phone = phone;
        this.website = website;
    }


}
