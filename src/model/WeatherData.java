package model;

import java.time.LocalDateTime;
import java.util.Date;

public class WeatherData{

    private String place;
    private LocalDateTime timestamp;
    private Double longitude;
    private Double latitude;
    private int[] temperature;
    private int[] rain;

    public WeatherData(Double longitude, Double latitude, int[] temperature, int[] rain, String place) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.temperature = temperature;
        this.rain = rain;
        this.place = place;
        this.timestamp = LocalDateTime.now();
    }

    //region Getter
    public Double getLongitude() {
        return longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public int[] getTemperature() {
        return temperature;
    }

    public int[] getRain() {
        return rain;
    }

    public String getPlace() {
        return place;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    //endregion


}
