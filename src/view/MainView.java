package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;

public class MainView extends JFrame {

    private JTextField longText, latText, placeTf, timeStampTf;
    private JButton getButton, saveCSVButton, loadCSVButton;

    //table
    private JTable weatherTable;
    private DefaultTableModel tableModel;
    private JScrollPane weatherScrollPane;
    private Object[][] weatherData;

    public MainView() {
        setSize(500,600);
         setTitle("Koordinaten Wetter");
         setDefaultCloseOperation( DISPOSE_ON_CLOSE );
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
        loadCSVButton.setEnabled(false);

        bottomPanel.add(getButton);
        bottomPanel.add(loadCSVButton);
        bottomPanel.add(saveCSVButton);
    }

    /**
     * Erstellt die Daten der Tabelle für den Start
     * @return - Daten
     */
    private Object[][] getDefaultValues(){
        Object[][] data = new String[24][3];

        for(int i = 0; i<24; i++){
            data[i][0] = i+" Uhr";
        }

        return data;
    }


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
