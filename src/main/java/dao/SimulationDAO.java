package dao;

import model.WeatherData;

import java.util.*;
import java.util.function.Consumer;

public class SimulationDAO implements WeatherDAO {


    private Map< Double[], String> locations;

    public SimulationDAO() {
        locations = getSimulatedPlaces();
    }

    /**
     * Simulierte Wetterdaten anfordern
     * @param longitude
     * @param latitude
     * @return
     */
    @Override
    public void getWeatherData(Double longitude, Double latitude, Consumer<WeatherData> onSuccessCallback){

        String place = getPlaceByCoords(longitude, latitude);

       Double[] temperature = getSimulatedTemperatureValues();
       Double[] rain = getSimulatedRainValues();

        WeatherData data = new WeatherData(longitude, latitude, temperature, rain, place);
        onSuccessCallback.accept(data);
    }

    /**
     * Simulierte Temperaturdaten für 24h erzeugen
     * @return Double[] temperaturen
     */
    private Double[] getSimulatedTemperatureValues(){
        Double[] temperatures = new Double[24];
        Random random = new Random();

        Double tValue = random.nextDouble(30) - 10;


        for (int i = 0; i < temperatures.length; i++) {
            temperatures[i] = tValue;

            if( i< 15)
                tValue+= random.nextDouble(3);
            else
                tValue-=random.nextDouble(3);;
        }

        return temperatures;
    }

    /**
     * Simulierte Niederschlagshöhe für 24h erzeugen
     * @return Double[] temperaturen
     */
    private Double[] getSimulatedRainValues(){
        Double[] rain = new Double[24];
        Random random = new Random();

        int chance = random.nextInt(4);
        if(chance > 2){
            //schweres Regenwetter
            for (int i = 0; i < rain.length; i++) {
                rain[i] = random.nextDouble(25);
            }
        }
        else if(chance>1)
            //leichtes Regenwetter
            for (int i = 0; i < rain.length; i++) {
                rain[i] = random.nextDouble(5);
        }
        else{
            //kein Regenwetter
            Arrays.fill(rain, 0d);
        }

        return rain;
    }

    /**
     * Gibt Ortsname anhand der Koordinaten zurück
     * @param longitude
     * @param latitude
     * @return
     */
    private String getPlaceByCoords(Double longitude, Double latitude){
        Double[] coords = new Double[]{longitude, latitude};

        String place = locations.get( coords );
        if(place == null || place.isEmpty())
            return "Unbekannter Ort";
        else
            return place;
    }

    /**
     * Erstellt Map mit Koordinaten und zugeordneten Ortsnamen für Simulationszwecke
     * @return Map< Double[], String>
     */
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
