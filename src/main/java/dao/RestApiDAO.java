package dao;

import model.WeatherData;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class RestApiDAO implements WeatherDAO{

    Consumer<WeatherData> onSuccessCallback;

    @Override
    public void getWeatherData(Double longitude, Double latitude, Consumer<WeatherData> onSuccessCallback) {
        this.onSuccessCallback = onSuccessCallback;
        sendRequest(longitude, latitude);
    }

    private void sendRequest( Double longitude, Double latitude){
        String uriString = "https://api.open-meteo.com/v1/forecast?latitude="
                + latitude + "&longitude=" +  longitude +
                "&hourly=temperature_2m,rain&timezone=Europe%2FBerlin&forecast_days=1";

        try{
            //get Request an open-meteo
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder(URI.create(uriString)).build();
            CompletableFuture<HttpResponse<String>> future = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
            future.thenAccept(this::handleApiResponse );
        }
        catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    private void handleApiResponse( HttpResponse<String> response){

        if(response.statusCode()==200){
            System.out.println( response.body() );
            var weatherData = getWeatherDataFromJson(response.body());
            System.out.println(weatherData);
            onSuccessCallback.accept(weatherData);

        }
        else{
            System.err.println("API response failed");
        }
    }

    private WeatherData getWeatherDataFromJson( String json ){

        try{
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(json);

            Double longitude = (Double) jsonObject.get("longitude");
            Double latitude = (Double) jsonObject.get("latitude");

           JSONObject hourly = (JSONObject) jsonObject.get("hourly");

            JSONArray temperatureArray = (JSONArray) hourly.get("temperature_2m");
            JSONArray rainArray = (JSONArray) hourly.get("rain");

            Double[] temperature = new Double[temperatureArray.size()];
            temperatureArray.toArray( temperature);

            Double[] rain = new Double[rainArray.size()];
            rainArray.toArray( rain );

            return new WeatherData(longitude,latitude,temperature, rain, "unbekannt");

        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }




}
