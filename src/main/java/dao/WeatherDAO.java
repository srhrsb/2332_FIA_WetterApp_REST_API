package dao;

import model.WeatherData;

public interface WeatherDAO {

    WeatherData getWeatherData( Double longitude, Double latitude );



}
