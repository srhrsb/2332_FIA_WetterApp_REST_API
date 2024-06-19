package model;

import java.time.LocalDateTime;
import java.util.Date;

public class WeatherData{

    private String place;
    private LocalDateTime timestamp;
    private Double longitude;
    private Double latitude;
    private Double[] temperature;
    private Double[] rain;

    public WeatherData(Double longitude, Double latitude, Double[] temperature, Double[] rain, String place) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.temperature = temperature;
        this.rain = rain;
        this.place = place;
        this.timestamp = LocalDateTime.now();
    }

    public WeatherData(Double longitude, Double latitude, Double[] temperature, Double[] rain, String place, LocalDateTime timestamp) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.temperature = temperature;
        this.rain = rain;
        this.place = place;
        this.timestamp = timestamp;
    }

    //region Getter
    public Double getLongitude() {
        return longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double[] getTemperature() {
        return temperature;
    }

    public Double[] getRain() {
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
