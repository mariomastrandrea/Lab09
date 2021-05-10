
package it.polito.tdp.borders;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.borders.model.CountriesModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController 
{	
    @FXML 
    private ResourceBundle resources;

    @FXML 
    private URL location;

    @FXML 
    private TextField txtAnno; 

    @FXML 
    private TextArea txtResult; 
    
    @SuppressWarnings("unused")
	private CountriesModel model;


    @FXML
    void doCalcolaConfini(ActionEvent event) 
    {

    }

    @FXML 
    void initialize() 
    {
        assert txtAnno != null : "fx:id=\"txtAnno\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
    }
    
    public void setModel(CountriesModel model) 
    {
    	this.model = model;
    }
}
