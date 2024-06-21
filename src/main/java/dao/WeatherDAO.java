package dao;

import model.WeatherData;

import java.util.function.Consumer;

public interface WeatherDAO {

    void getWeatherData(Double longitude, Double latitude, Consumer<WeatherData> onSuccessDAO);



}
