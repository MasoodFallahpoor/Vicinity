package ir.fallahpoor.vicinity.data.entity;

import com.google.gson.annotations.SerializedName;

public class LocationEntity {

    @SerializedName("address")
    private String address;
    @SerializedName("lat")
    private double latitude;
    @SerializedName("lng")
    private double longitude;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

}
