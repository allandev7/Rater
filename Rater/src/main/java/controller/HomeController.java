package controller;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.AnchorPane;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import model.Empresa;
import model.Padroes;
import model.Entrevistador;

public class HomeController {
	@FXML LineChart<String , Number> lineChart;
	@FXML PieChart pieChartLeft;
	@FXML PieChart pieChartRight;
	@FXML AnchorPane pane;
	private static int haentrevista;
	public static int getHaentrevista() {
		return haentrevista;
	}

	public static void setHaentrevista(int haentrevista) {
		HomeController.haentrevista = haentrevista;
	}

	Empresa e = new Empresa();
	Padroes p = new Padroes();
	Entrevistador EN = new Entrevistador();
	public void initialize() throws SQLException {
		
		
		lineChart.getData().clear();
		XYChart.Series<String, Number> series = new XYChart.Series<String, Number>();
		series.getData().add(new XYChart.Data<String, Number>("Jan", e.numeroEntrevistaMes().get(0)));
		series.getData().add(new XYChart.Data<String, Number>("Feb", e.numeroEntrevistaMes().get(1)));
		series.getData().add(new XYChart.Data<String, Number>("Mar", e.numeroEntrevistaMes().get(2)));
		series.getData().add(new XYChart.Data<String, Number>("Abr", e.numeroEntrevistaMes().get(3)));
		series.getData().add(new XYChart.Data<String, Number>("Mai", e.numeroEntrevistaMes().get(4)));
		series.getData().add(new XYChart.Data<String, Number>("Jun", e.numeroEntrevistaMes().get(5)));
		series.getData().add(new XYChart.Data<String, Number>("Jul", e.numeroEntrevistaMes().get(6)));
		series.getData().add(new XYChart.Data<String, Number>("Ago", e.numeroEntrevistaMes().get(7)));
		series.getData().add(new XYChart.Data<String, Number>("Oct", e.numeroEntrevistaMes().get(8)));
		series.getData().add(new XYChart.Data<String, Number>("Set", e.numeroEntrevistaMes().get(9)));
		series.getData().add(new XYChart.Data<String, Number>("Nov", e.numeroEntrevistaMes().get(10)));
		series.getData().add(new XYChart.Data<String, Number>("Dec", e.numeroEntrevistaMes().get(11)));

		series.setName("Número de entrevistas");
		lineChart.getData().add(series);
		lineChart.setLegendVisible(false);
		
		
		
		ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
		
		for(int i =0; i<EN.carregarEntrevistadores("").size(); i++) {		
			pieChartData.add(new PieChart.Data(EN.carregarEntrevistadores("").get(i)+": " +  e.carregarNumEntrevistaEntrevistadores().get(i) , e.carregarNumEntrevistaEntrevistadores().get(i)));

		}
		
	
		
		pieChartLeft.setData(pieChartData);
		pieChartLeft.setLegendVisible(false);
		pieChartLeft.setLabelLineLength(5);
				
		ObservableList<PieChart.Data> list = FXCollections.observableArrayList();
		
		for(int i =0 ;i< p.carregarCargos().size(); i++) {
			list.add(new PieChart.Data(p.carregarCargos().get(i)+ ": "+ e.carregarNumEntrevistaCargo().get(i) , e.carregarNumEntrevistaCargo().get(i)));
		}
		

		
		pieChartRight.setData(list);
		pieChartRight.setLabelLineLength(5);
		pieChartRight.setLegendVisible(false);
		//pieChartRight.setMinSize(292.0, 343.0);

		pieChartRight.setMinSize(392.0, 300.0);
		
		
				
	}
}
