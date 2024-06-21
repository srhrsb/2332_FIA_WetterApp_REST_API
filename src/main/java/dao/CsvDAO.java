package dao;

import model.WeatherData;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;


public class CsvDAO {

    private final String csvSeparator =",";

    /**
     * Speichert die aktuellen Wetterdaten als CSV File
     * @param saveData Object[][] Tabellendaten
     * @param savePath String Pfad
     * @param timestamp Zeitstempel
     * @param place Ort
     * @param longitude Longitude Koordinate
     * @param latitude Latitude Koordinate
     * @return
     */
    public boolean saveWeatherDataToFile( Object[][] saveData, String savePath, String timestamp, String place, Double longitude, Double latitude ){
        try {
            FileWriter csv = new FileWriter(savePath);
            for (int i = 0; i < saveData.length; i++) {

                for (int j = 0; j < saveData[i].length; j++) {
                    if(j<saveData[i].length-1)
                        csv.write(saveData[i][j].toString().replace(",",".") + csvSeparator);
                    else
                        csv.write(saveData[i][j].toString().replace(",","."));
                }

                csv.write("\n");
            }

            csv.write(timestamp+csvSeparator+place+csvSeparator+longitude+csvSeparator+latitude);
            csv.close();
            return true;

        }catch(IOException e) {
            System.err.println( e );
        }
        return false;
    }

    /**
     * Lädt die aktuellen Wetterdaten aus CSV File
     * @param file Dateiname
     * @return Weatherdata Wetterdatenobjekt
     */
    public WeatherData loadWeatherDataFromFile(String file ){
        try {
            String csv = Files.readString( Path.of(file) );
            String[] lines = csv.split("[\\r\\n]+");
            var temperature = new Double[24];
            var rain = new Double[24];

            int i;
            for (i = 0; i < temperature.length; i++) {
                String[] columns = lines[i].split( csvSeparator );
                temperature[i] = Double.parseDouble( columns[1] );
                rain[i] = Double.parseDouble( columns[2] );
            }

            String[] lastLine = lines[lines.length-1].split( csvSeparator );

            return new WeatherData( Double.parseDouble(lastLine[2]),
                    Double.parseDouble(lastLine[3]),
                    temperature, rain, lastLine[1],
                    LocalDateTime.parse(lastLine[0]));

        }
        catch ( IOException error){
            System.out.println("Fehler: "+ error);
        }
        return null;
    }
}
