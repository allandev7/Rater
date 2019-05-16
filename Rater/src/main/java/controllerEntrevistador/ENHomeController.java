package controllerEntrevistador;

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

public class ENHomeController {
	@FXML LineChart<String , Number> lineChart;
	@FXML PieChart pieChartLeft;
	@FXML PieChart pieChartRight;
	@FXML AnchorPane pane;
	private static int haentrevista;
	
	
	Padroes p = new Padroes();
	Entrevistador EN = new Entrevistador();
	
	public void initialize() throws SQLException {
	
		lineChart.getData().clear();
		XYChart.Series<String, Number> series = new XYChart.Series<String, Number>();
		series.getData().add(new XYChart.Data<String, Number>("Jan", EN.carregarNumEntrevistaMesEN().get(0)));
		series.getData().add(new XYChart.Data<String, Number>("Feb", EN.carregarNumEntrevistaMesEN().get(1)));
		series.getData().add(new XYChart.Data<String, Number>("Mar", EN.carregarNumEntrevistaMesEN().get(2)));
		series.getData().add(new XYChart.Data<String, Number>("Abr", EN.carregarNumEntrevistaMesEN().get(3)));
		series.getData().add(new XYChart.Data<String, Number>("Mai", EN.carregarNumEntrevistaMesEN().get(4)));
		series.getData().add(new XYChart.Data<String, Number>("Jun", EN.carregarNumEntrevistaMesEN().get(5)));
		series.getData().add(new XYChart.Data<String, Number>("Jul", EN.carregarNumEntrevistaMesEN().get(6)));
		series.getData().add(new XYChart.Data<String, Number>("Ago", EN.carregarNumEntrevistaMesEN().get(7)));
		series.getData().add(new XYChart.Data<String, Number>("Oct", EN.carregarNumEntrevistaMesEN().get(8)));
		series.getData().add(new XYChart.Data<String, Number>("Set", EN.carregarNumEntrevistaMesEN().get(9)));
		series.getData().add(new XYChart.Data<String, Number>("Nov", EN.carregarNumEntrevistaMesEN().get(10)));
		series.getData().add(new XYChart.Data<String, Number>("Dec", EN.carregarNumEntrevistaMesEN().get(11)));

		series.setName("Número de entrevistas");
		lineChart.getData().add(series);
		lineChart.setLegendVisible(false);
		
		
		
		ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
		
		for(int i =0 ;i< p.carregarCargos().size(); i++) {
			pieChartData.add(new PieChart.Data(p.carregarCargos().get(i)+ ": "+ EN.carregarNumEntrevistaCargo().get(i) , EN.carregarNumEntrevistaCargo().get(i)));
		}
		

		
	
		
		pieChartLeft.setData(pieChartData);
		pieChartLeft.setLegendVisible(false);
		pieChartLeft.setLabelLineLength(5);
				
		ObservableList<PieChart.Data> list = FXCollections.observableArrayList();
		
		//list.add(new PieChart.Data(p.carregarCargos().get(i)+ ": "+ e.carregarNumEntrevistaCargo().get(i) , e.carregarNumEntrevistaCargo().get(i)));
		
		list.add(new PieChart.Data("Contratados", EN.carregarEntrevistaAce()));
		list.add(new PieChart.Data("Recusados", EN.carregarEntrevistaRec()));
		list.add(new PieChart.Data("Em espera", EN.carregarEntrevistaEsp()));

		
		pieChartRight.setData(list);
		pieChartRight.setLabelLineLength(5);
		pieChartRight.setLegendVisible(false);
		//pieChartRight.setMinSize(292.0, 343.0);

		pieChartRight.setMinSize(392.0, 300.0);
		
		
				

	}
	
	
}
