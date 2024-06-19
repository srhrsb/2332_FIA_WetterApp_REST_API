package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.DecimalFormat;
import java.time.LocalDateTime;

public class MainView extends JFrame {

    private JTextField longText, latText, placeTf, timeStampTf;
    private JButton getButton, saveCSVButton, loadCSVButton;
    private final JFileChooser fileChooser = new JFileChooser();
    private final DecimalFormat formatter = new DecimalFormat("#.##");

    //table
    private JTable weatherTable;
    private DefaultTableModel tableModel;
    private JScrollPane weatherScrollPane;
    private Object[][] weatherData;

    public MainView() {
        setSize(500,600);
        setTitle("Koordinaten Wetter");
        setDefaultCloseOperation( DISPOSE_ON_CLOSE );
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.setFileFilter(
                new FileNameExtensionFilter("CSV - Dateien", "csv"));
        addUI();
        setVisible(true);
    }

    /**
     * Erstellt die Elemente der Benutzeroberfläche
     * und fügt dieses dem Fenster hinzu
     */
    public void addUI(){
        JPanel coordsPanel = new JPanel();
        JPanel resultPanel = new JPanel();
        JPanel bottomPanel = new JPanel();
        setLayout(new BorderLayout());

        coordsPanel.setLayout(new GridLayout(4,2));
        coordsPanel.setBorder(new EmptyBorder(5,5,5,5));

        add(coordsPanel, BorderLayout.NORTH);
        add(resultPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        timeStampTf = new JTextField();
        timeStampTf.setEnabled(false);

        placeTf = new JTextField();
        longText = new JTextField();
        latText = new JTextField();

        JLabel timeStamp = new JLabel("Zeitstempel:");
        JLabel place = new JLabel("Ort:");
        JLabel longitude = new JLabel("Longitude:");
        JLabel latitude = new JLabel("Latitude:");

        coordsPanel.add(timeStamp);
        coordsPanel.add(timeStampTf);
        coordsPanel.add(place);
        coordsPanel.add(placeTf);
        coordsPanel.add(longitude);
        coordsPanel.add(longText);
        coordsPanel.add(latitude);
        coordsPanel.add(latText);


        String[] names = {"Uhrzeit", "Temperatur", "Niederschlag"};
        weatherData = getDefaultValues();
        tableModel = new DefaultTableModel(weatherData, names);
        weatherTable = new JTable(tableModel);
        weatherScrollPane = new JScrollPane(weatherTable);
        resultPanel.add(weatherScrollPane);

        getButton = new JButton("Wetterdaten holen");
        saveCSVButton = new JButton("Speichern (CSV)");
        saveCSVButton.setEnabled(false);
        loadCSVButton = new JButton("Laden (CSV)");


        bottomPanel.add(getButton);
        bottomPanel.add(loadCSVButton);
        bottomPanel.add(saveCSVButton);
    }

    /**
     * Erstellt die Daten der Tabelle für den Start
     * @return Object[][] Daten
     */
    private Object[][] getDefaultValues(){
        Object[][] data = new String[24][3];

        for(int i = 0; i<24; i++){
            data[i][0] = i+" Uhr";
        }

        return data;
    }

    /**
     * Holt die eingegebenen Koordinaten von den Textfeldern
     * @return Double[] Koordinaten
     */
    public Double[] getCurrentCoords(){
        Double longitude = 0d;
        Double latitude = 0d;
        try {
            longitude = Double.parseDouble(longText.getText());
            latitude = Double.parseDouble(latText.getText());

        } catch (Exception e) {
            System.out.println("Error: " + e);
        }

        return new Double[]{longitude, latitude};
    }

    /**
     * Gibt die Eingabe des Textfeldes Timestamp zurück
     * @return String Zeitstempel
     */
    public String getTimeStamp(){
        return timeStampTf.getText();
    }

    /**
     * Gibt den eingegeben Ortsnamen zurück
     * @return String Ortsname
     */
    public String getPlace(){
        return placeTf.getText();
    }

    /**
     * Aktualisiert die Tabelle mit neuen Wetterdaten
     * @param timeStamp Zeitstempel
     * @param place Ortsname
     * @param longitude Longitude
     * @param latitude Latitude
     * @param temperature Double[] Temperaturen 24h
     * @param rain Double[] Niederschlag 24h
     */
    public void updateWeatherData(LocalDateTime timeStamp, String place, Double longitude, Double latitude, Double[] temperature, Double[] rain){
        for (int i = 0; i < temperature.length; i++) {
            weatherTable.getModel().setValueAt( formatter.format(temperature[i]), i, 1 );
        }

        for (int j = 0; j < rain.length; j++) {
            weatherTable.getModel().setValueAt( formatter.format(rain[j]), j, 2 );
        }

        String timeString = timeStamp.toString();
        timeStampTf.setText(timeString);
        placeTf.setText(place);
        longText.setText(String.valueOf(longitude));
        latText.setText(String.valueOf(latitude));

        saveCSVButton.setEnabled(true);
    }

    /**
     * Gibt die aktuell eingetragenen Wetterdaten zurück
     * @return Object[][] Tabellendaten
     */
    public Object[][] getTableData () {
        int nRow = tableModel.getRowCount(), nCol = tableModel.getColumnCount();
        Object[][] tableData = new Object[nRow][nCol];

        for (int i = 0 ; i < nRow ; i++)
            for (int j = 0 ; j < nCol ; j++)
                tableData[i][j] = tableModel.getValueAt(i,j);

        return tableData;
    }

    /**
     * Öffnet Dateidialog zum Speichern und gibt eingegebenen bzw. gewählten Filenamen/Pfad zurück
     * @return String Dateipfad
     */
    public String getCSVSavePath(){
        int success = fileChooser.showSaveDialog(this);
        String path = "";

        if(success == JFileChooser.APPROVE_OPTION){
            File file = fileChooser.getSelectedFile();
            path = file.getPath();
        }

        return path;
    }

    /**
     * Öffnet Dateidialog zum Laden und gibt eingegebenen bzw. gewählten Filenamen/Pfad zurück
     * @return String Dateipfad
     */
    public String getCSVLoadFile(){
        int success = fileChooser.showOpenDialog(this);
        String path = "";

        if(success == JFileChooser.APPROVE_OPTION){
            File file = fileChooser.getSelectedFile();
            path = file.getPath();
        }

        return path;
    }

    //region Messages

    /**
     * Zeigt Infofenster an
     * @param message Infotext
     */
    public void showInfoMessage(String message) {
        JOptionPane.showMessageDialog(this, message,
                "Information", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Zeigt Fehlerfenster an
     * @param message Infotext
     */
    public void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message,
                "Error", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Zeigt Zustimmungsfenster an
     * @param message Infotext
     */
    public boolean confirmDialog(String message) {
        return JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(this,
                message, "Frage", JOptionPane.YES_NO_OPTION);
    }
    //endregion


    //region Button Handler
    /**
     * Fügt dem "Wetterdaten holen" Button den Eventlistener hinzu für onclick
     * @param listener
     */
    public void addGetDataButtonHandler(ActionListener listener){
        getButton.addActionListener( listener );
    }

    /**
     * Fügt dem Speicher-Button den Eventlistener hinzu für onclick
     * @param listener
     */
    public void addSaveCsvButtonHandler(ActionListener listener){
        saveCSVButton.addActionListener( listener );
    }

    /* Fügt dem Laden-Button den Eventlistener hinzu für onclick
     * @param listener
     */
    public void addLoadCsvButtonHandler(ActionListener listener){
        loadCSVButton.addActionListener( listener );
    }
    //endregion

}
