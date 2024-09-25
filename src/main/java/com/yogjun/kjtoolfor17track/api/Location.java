package com.yogjun.kjtoolfor17track.api;

public class Location {
  private String name;
  private String description;
  private double lat;
  private double lng;

  public Location(String name, String description, double lat, double lng) {
    this.name = name;
    this.description = description;
    this.lat = lat;
    this.lng = lng;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public double getLat() {
    return lat;
  }

  public double getLng() {
    return lng;
  }
}
