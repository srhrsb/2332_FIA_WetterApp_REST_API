package dao;

import model.WeatherData;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

public class RestApiDAO implements WeatherDAO{

    @Override
    public WeatherData getWeatherData(Double longitude, Double latitude) {
        sendRequest(longitude, latitude);
        return null;
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
        System.out.println( response.body() );


    }

}
