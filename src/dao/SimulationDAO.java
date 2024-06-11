package dao;

import model.WeatherData;

import java.util.*;

public class SimulationDAO implements WeatherDAO {


    private Map< Double[], String> locations;

    public SimulationDAO() {
        locations = getSimulatedPlaces();
    }

    @Override
    public WeatherData getWeatherData( Double longitude, Double latitude){

        String place = getPlaceByCoords(longitude, latitude);

        int[] temperature = getSimulatedTemperatureValues();
        int[] rain = getSimulatedRainValues();

        WeatherData data = new WeatherData(longitude, latitude, temperature, rain, place);
        return data ;
    }

    private int[] getSimulatedTemperatureValues(){
        int[] temperatures = new int[24];
        Random random = new Random();

        int tValue = random.nextInt(30) - 10;


        for (int i = 0; i < temperatures.length; i++) {
            temperatures[i] = tValue;

            if( i< 15)
                tValue+= random.nextInt(3);
            else
                tValue--;
        }

        return temperatures;
    }

    private int[] getSimulatedRainValues(){
        int[] rain = new int[24];
        Random random = new Random();

        for (int i = 0; i < rain.length; i++) {
            rain[i] = random.nextInt(100);
        }

        return rain;
    }

    private String getPlaceByCoords(Double longitude, Double latitude){
        Double[] coords = new Double[]{longitude, latitude};

        String place = locations.get( coords );
        if(place == null || place.isEmpty())
            return "Unbekannter Ort";
        else
            return place;
    }

    private Map<Double[], String> getSimulatedPlaces(){
        Map< Double[], String> locations = new HashMap<>();
        locations.put(new Double[]{52.5200, 13.4050},"Berlin");
        locations.put(new Double[]{48.8566, 2.3522}, "Paris" );
        locations.put(new Double[]{51.5074, -0.1278}, "London");
        locations.put(new Double[]{40.7128, -74.0060}, "New York");
        locations.put(new Double[]{35.6895, 139.6917},"Tokyo");
        locations.put(new Double[]{-33.8688, 151.2093}, "Sydney");
        locations.put(new Double[]{-22.9068, -43.1729}, "Rio de Janeiro");
        locations.put(new Double[]{-33.9249, 18.4241}, "Cape Town");
        locations.put(new Double[]{55.7558, 37.6176}, "Moscow");
        locations.put(new Double[]{25.276987, 55.296249}, "Dubai");

        return locations;
    }
}
