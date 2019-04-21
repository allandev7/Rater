package controllerEntrevistador;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;

public class ENHomeController {
	@FXML LineChart<String , Number> lineChart;
	@FXML PieChart pieChartLeft;
	@FXML PieChart pieChartRight;
	
	
	public void initialize() {
		lineChart.getData().clear();
		lineChart.setCreateSymbols(false);
		XYChart.Series<String, Number> series = new XYChart.Series<String, Number>();
		series.getData().add(new XYChart.Data<String, Number>("Jan", 200));
		series.getData().add(new XYChart.Data<String, Number>("Feb", 500));
		series.getData().add(new XYChart.Data<String, Number>("Mar", 300));
		series.getData().add(new XYChart.Data<String, Number>("Abr", 100));
		series.getData().add(new XYChart.Data<String, Number>("Mai", 400));
		series.getData().add(new XYChart.Data<String, Number>("Jun", 150));
		series.getData().add(new XYChart.Data<String, Number>("Jul", 400));
		series.getData().add(new XYChart.Data<String, Number>("Ago", 250));
		series.getData().add(new XYChart.Data<String, Number>("Oct", 1000));
		series.setName("Número de entrevistas");
		lineChart.getData().add(series);
		lineChart.setLegendVisible(false);
		
		ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                new PieChart.Data("Allan", 13),
                new PieChart.Data("Davi", 25),
                new PieChart.Data("Daniel", 10),
                new PieChart.Data("Thiago", 22),
                new PieChart.Data("Marcelo", 30),
        		new PieChart.Data("Rafael",15));
				pieChartLeft.setData(pieChartData);
				pieChartLeft.setLegendVisible(false);
				pieChartLeft.setLabelLineLength(5);
				
		ObservableList<PieChart.Data> list = FXCollections.observableArrayList(
                new PieChart.Data("Programador", 30),
		        new PieChart.Data("Administrador", 45),
                new PieChart.Data("Marketeiro", 30),
	       		new PieChart.Data("Analista",25));			
				pieChartRight.setData(list);
				pieChartRight.setLabelLineLength(5);
				pieChartRight.setLegendVisible(false);
				//pieChartRight.setMinSize(292.0, 343.0);

				pieChartRight.setMinSize(392.0, 300.0);
	}
}
