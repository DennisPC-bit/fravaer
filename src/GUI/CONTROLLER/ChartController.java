package GUI.CONTROLLER;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.layout.BorderPane;
import java.net.URL;
import java.util.ResourceBundle;

public class ChartController implements Initializable {

    //AbsenceManager ab =  new AbsenceManager();

    @FXML
    private BorderPane borderPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        CategoryAxis xA = new CategoryAxis();
        xA.setLabel("Fag");

        NumberAxis yA = new NumberAxis();
        yA.setLabel("Procent fravær");

        BarChart barChart = new BarChart(xA,yA);

        XYChart.Series data = new XYChart.Series();
        data.setName("Fravær");

        //provided data 


            //AbsenceManager ab =  new AbsenceManager();
            //ArrayList<Class> absence = ab.getStudentAbsence("madsq");

            /*for(Class sub : absence)

                data.getData().add(new XYChart.Data(sub.getName(),sub.getAbsence()));


             */
        barChart.getData().add(data);
        borderPane.setCenter(barChart);
    }

    @FXML
    private void handleShowBarChart(ActionEvent actionEvent) {
        CategoryAxis xA = new CategoryAxis();
        xA.setLabel("Dage");

        NumberAxis yA = new NumberAxis();
        yA.setLabel("Procent fravær");

        BarChart barChart = new BarChart(xA,yA);

        XYChart.Series data = new XYChart.Series();
        data.setName("Fravær");

        //provided data
        yA.setAutoRanging(false);
        yA.setLayoutY(100);

        data.getData().add(new XYChart.Data("Mandag",(int)(Math.random() * 50 + 1)));
        data.getData().add(new XYChart.Data("Tirsdag",(int)(Math.random() * 50 + 1)));
        data.getData().add(new XYChart.Data("Onsdag",(int)(Math.random() * 50 + 1)));
        data.getData().add(new XYChart.Data("Torsdag",(int)(Math.random() * 50 + 1)));
        data.getData().add(new XYChart.Data("Fredag",(int)(Math.random() * 50 + 1)));






        barChart.getData().add(data);

        // add barChar to borderPane
        borderPane.setCenter(barChart);


    }

    @FXML
    private void handleShowToTalBarChart(ActionEvent actionEvent) {
        CategoryAxis xA = new CategoryAxis();
        xA.setLabel("FAG");

        NumberAxis yA = new NumberAxis();
        yA.setLabel("Total Procent Fravær");

        BarChart barChart = new BarChart(xA,yA);

        XYChart.Series data = new XYChart.Series();
        data.setName("Total Fravær");

        //provided data
        yA.setAutoRanging(false);
        yA.setLayoutY(100);

        data.getData().add(new XYChart.Data("SCO",(int)(Math.random() * 50 + 1)));
        data.getData().add(new XYChart.Data("ITO",(int)(Math.random() * 50 + 1)));
        data.getData().add(new XYChart.Data("DBO",(int)(Math.random() * 50 + 1)));
        data.getData().add(new XYChart.Data("SDE",(int)(Math.random() * 50 + 1)));




        barChart.getData().add(data);

        // add barChar to borderPane
        borderPane.setCenter(barChart);


    }

    @FXML
    private void handleShowPieChart(ActionEvent actionEvent) {

        // create data
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("SCO2",10),
                new PieChart.Data("SDE2",15),
                new PieChart.Data("DBO2",20)
        );
        // create piechart objct
        PieChart pieChart = new PieChart(pieChartData);
        pieChart.setTitle("Fravær");
        pieChart.setClockwise(true);
        pieChart.setLabelLineLength(50);
        pieChart.setLabelsVisible(true);
        pieChart.setStartAngle(180);

        borderPane.setCenter(pieChart);
    }

    @FXML
    private void handleClose(ActionEvent actionEvent) {
        System.exit(0);
    }

    @FXML
    private void handleUpdateData(ActionEvent actionEvent) {
    }
}
