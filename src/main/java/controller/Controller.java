/**
 *Uebungsprojekt zur Verwendung von Interfaces anhand von
 *3 Data Access Objects:
 *  1. zur zufälligen Simulation der Wetterdaten für Testzwecke
 *  2. zum Auslesen aus Dateien via CSV-Parser
 *  3. zum Laden via REST-API (als JSON)
 *
 *Uebungsziel/Ergebnisse:
 *  1. Umgang mit enums
 *  2. Schreiben/Lesen/Parsen von Dateien
 *  3. Verwendung einer REST-API
 *  4. Wiederholung MVC-Pattern
 */

package controller;
import dao.CsvDAO;
import dao.RestApiDAO;
import dao.SimulationDAO;
import dao.WeatherDAO;
import model.WeatherData;
import view.MainView;

import java.awt.event.ActionEvent;

public class Controller {

    MainView view;
    WeatherDAO weatherDB;
    CsvDAO weatherFiles;

    public Controller( MainView view) {
        this.view = view;
        this.weatherDB = new RestApiDAO();
        this.weatherFiles = new CsvDAO();

        view.addGetDataButtonHandler(this::getWeatherData);
        view.addSaveCsvButtonHandler(this::saveAsCsv);
        view.addLoadCsvButtonHandler(this::loadAsCsv);

    }

    public static void main(String[] args) {
        new Controller( new MainView() );
    }

    //region Get Weather Data

    /**
     * Wetterdaten anfordern (ausgelöst durch den Event des "Wetterdaten holen" - Button)
     * @param event
     */
    private void getWeatherData(ActionEvent event){
        System.out.println("Get Weather Data");

        var coords = view.getCurrentCoords();
        WeatherData data = weatherDB.getWeatherData(coords[0], coords[1]);
        view.updateWeatherData( data.getTimestamp(), data.getPlace(), data.getLongitude(), data.getLatitude(), data.getTemperature(), data.getRain() );
    }

    /**
     * Wetterdaten anfordern (ausgelöst durch den Event des "Laden CSV" - Button)
     * @param event
     */
    private void loadAsCsv(ActionEvent event){
        System.out.println("Load Weather Data from CSV");

        String file = view.getCSVLoadFile();

        if(file.isEmpty()){
            view.showErrorMessage("Dateiname darf nicht leer sein");
            return;
        }

        if(!file.endsWith(".csv")){
            view.showErrorMessage("Nur CSV Dateien sind erlaubt");
            return;
        }
        var data  = weatherFiles.loadWeatherDataFromFile(file);

        if(data == null){
            view.showErrorMessage("Datei konnte nicht geladen werden");
            return;
        }

        view.updateWeatherData( data.getTimestamp(), data.getPlace(), data.getLongitude(), data.getLatitude(), data.getTemperature(), data.getRain() );
    }

    //endregion

    //region Save Weather Data
    /**
     * Wetterdaten als CSV File speichern, ausgelöst durch den Event des "Speichern CSV" - Button
     * @param event
     */
    private void saveAsCsv(ActionEvent event){
        System.out.println("Save Weather Data as CSV");

        var tableData = view.getTableData();
        var place = view.getPlace();
        var timeStamp = view.getTimeStamp();
        var coords = view.getCurrentCoords();
        var path = view.getCSVSavePath();
        if(path.isEmpty()) return;

        if(!path.endsWith(".csv")){
            path+=".csv";
        }

        var success = weatherFiles.saveWeatherDataToFile(tableData, path,  timeStamp, place, coords[0], coords[1]);
        if(!success){
            view.showErrorMessage("Die Datei konnte nicht gespeichert werden");
        }
    }
    //endregion

}