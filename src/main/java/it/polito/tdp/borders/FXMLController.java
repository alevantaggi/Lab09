
package it.polito.tdp.borders;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import it.polito.tdp.borders.model.Country;
import it.polito.tdp.borders.model.Model;
import it.polito.tdp.borders.model.VertexCar;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;

public class FXMLController {

	private Model model;
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtAnno"
    private TextField txtAnno; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader
    
    @FXML
    private ComboBox<Country> cmbCountries;

    @FXML
    void doCalcolaConfini(ActionEvent event) {
    	txtResult.setText("");
    	String inserimento= txtAnno.getText();
    	Integer anno=0;
    	
    	try {
    		anno=Integer.parseInt(inserimento);
			
		} catch (Exception e) {
			txtResult.setText("Inserisci un anno valido");
			return;
		}
    	
    	if(anno<1816 || anno>2016) {
    		txtResult.setText("Inserisci un anno compreso tra il 1816 e il 2016, estremi inclusi");
    		return;
    	}
    	
    	this.model.creaGrafo(anno);
    	
    	for(VertexCar v: this.model.getAllCharacteristics())
    		txtResult.appendText(""+v.getCountry()+"-> confina con: "+v.getGradoVertcice()+" stati e può raggiungere: "+v.getComponenteConnessa()+" stati\n");
    
    	txtAnno.setText("");
    }
    
    @FXML
    void searchStatiRaggiungibili(ActionEvent event) {
    	Country paese= cmbCountries.getValue();
    	txtResult.setText("");
    	
    	if(!this.model.isGrafoPopolato()) {
    		txtResult.setText("Prima di cercare gli stati raggiungibili è necessario inserire un anno e cliccare sul bottone: Calcola confini");
    		return;
    	}
    	
    	if(paese!= null) {
    		List<Country> risultato= new ArrayList<>(this.model.getAllRaggiungibili(paese));
    		
    		if(!risultato.isEmpty()) {
    			for(Country c: risultato)
    				txtResult.appendText(""+c+"\n");
    		}
    		else
    			txtResult.setText("Nessuno Stato raggiungibile");
    		return;
    	}
    	txtResult.setText("Selezionare uno Stato");
    }
    
    @FXML
    void initialize() {
        assert cmbCountries != null : "fx:id=\"cmbCountries\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtAnno != null : "fx:id=\"txtAnno\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    	cmbCountries.getItems().addAll(this.model.getListaCountry());
    }
}
