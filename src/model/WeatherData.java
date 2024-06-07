package model;

import java.time.LocalDateTime;
import java.util.Date;

public class WeatherData{

    private String place;
    private LocalDateTime timestamp;
    private float longitude;
    private float latitude;
    private int[] temperature;
    private int[] rain;

    public WeatherData(float longitude, float latitude, int[] temperature, int[] rain, String place) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.temperature = temperature;
        this.rain = rain;
        this.place = this.place;
        this.timestamp = LocalDateTime.now();
    }


    //region Getter
    public float getLongitude() {
        return longitude;
    }

    public float getLatitude() {
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
