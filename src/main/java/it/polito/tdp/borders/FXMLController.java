package it.polito.tdp.borders;

import java.net.URL;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import it.polito.tdp.borders.model.CountriesModel;
import it.polito.tdp.borders.model.Country;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.KeyEvent;

public class FXMLController 
{
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField annoTextField;
    private final int MAX_CHARS_ANNO = 4;
    private final int MIN_YEAR = 1816;
    private final int MAX_YEAR = 2016;

    @FXML
    private Button calcolaConfiniButton;

    @FXML
    private ComboBox<Country> statoComboBox;

    @FXML
    private Button calcolaStatiRaggiungibiliButton;
    
    @FXML
    private TextArea resultTextArea;
    
	private CountriesModel model;


    @FXML
    void handleAnnoTyping(KeyEvent event) 
    {
    	String input = this.annoTextField.getText();
    	
    	if(input.length() == 4)
    		this.calcolaConfiniButton.setDisable(false);
    	else
    		this.calcolaConfiniButton.setDisable(true);
    }

    @FXML
    void handleCalcolaConfini(ActionEvent event) 
    {
    	String input = this.annoTextField.getText();
    	int year = 0;
    	
    	try
		{
			year = Integer.parseInt(input);
		}
		catch(NumberFormatException nfe)
		{
			nfe.printStackTrace();
			this.resultTextArea.setText("Input error: you must enter an integer number");
			return;
		}
    	
    	if(year < MIN_YEAR || year > MAX_YEAR)
    	{
			this.resultTextArea.setText("Errore: inserisci un anno compreso tra " + MIN_YEAR + " e " + MAX_YEAR);
			return;
    	}
    	
    	//correct input here
    	this.model.createCountriesGraphUntil(year);
    	
    	Map<Country,Integer> countriesByNumContiguities = this.model.getCountryContiguities(); 
    	
    	if(countriesByNumContiguities.isEmpty())
    	{
    		this.resultTextArea.setText("Non esistono stati confinanti fino all'anno " + year);
    		return;
    	}
    	
    	int numConnectedComponents = this.model.computeConnectedComponents();

    	String resultText = this.printCountriesAndConnectedComponents(countriesByNumContiguities, 
    																	numConnectedComponents, year);
    	
    	this.resultTextArea.setText(resultText);
    	
    	//update UI
    	this.statoComboBox.setDisable(false);
    	
    	this.statoComboBox.setValue(null);
    	this.statoComboBox.getItems().clear();
    	
    	List<Country> countries = new LinkedList<>(countriesByNumContiguities.keySet());
    	countries.sort((c1,c2) -> c1.getName().compareTo(c2.getName()));
    	this.statoComboBox.getItems().addAll(countries);
    }

    private String printCountriesAndConnectedComponents(Map<Country, Integer> countriesByNumContiguities,
			int numConnectedComponents, int year)
	{
    	StringBuilder sb = new StringBuilder();
    	
    	int numCountries = countriesByNumContiguities.size();
    	String countriesWord = numCountries == 1 ? "nazione" : "nazioni";
    	
    	sb.append("Trovate ").append(numCountries).append(" ").append(countriesWord);
    	sb.append(" fino all'anno ").append(year).append(":\n\n");
    	
    	for(var pair : countriesByNumContiguities.entrySet())
    	{
    		String country = pair.getKey().toString();
    		int numNeighboringCountries = pair.getValue();
    		
    		sb.append("- ").append(country).append("  -->  #");
    		sb.append(numNeighboringCountries).append(" nazioni confinanti\n");
    	}
    	    	
    	sb.append("\nNumero di componenti connesse: ").append(numConnectedComponents);
    	//sb.append("\nNumero totale di archi: ").append(this.model.getNumEdges());
    	
    	return sb.toString();
	}
    
    @FXML
    void handleCountrySelection(ActionEvent event) 
    {
    	Country selectedCountry = this.statoComboBox.getValue();
    	
    	if(selectedCountry == null)
    		this.calcolaStatiRaggiungibiliButton.setDisable(true);
    	else
    		this.calcolaStatiRaggiungibiliButton.setDisable(false);
    }
    
    @FXML
    void handleCalcolaStatiRaggiungibili(ActionEvent event) 
    {
    	Country selectedCountry = this.statoComboBox.getValue();
    	
    	Collection<Country> reachableCountries = this.model.computeReachableCountriesFrom(selectedCountry);
    	
    	if(reachableCountries.size() == 1)
    	{
    		this.resultTextArea.setText("La nazione " + selectedCountry.toString() + " è completamente isolata!");
    		return;
    	}
    	
    	String resultText = this.printReachableCountries(selectedCountry, reachableCountries);
    	
    	this.resultTextArea.setText(resultText);
    }

    
	private String printReachableCountries(Country selectedCountry, Collection<Country> reachableCountries)
	{
		StringBuilder sb = new StringBuilder();
		
		sb.append("Gli stati raggiungibili a partire dallo stato ").append(selectedCountry);
		sb.append(" sono ").append(reachableCountries.size()).append(":\n\n");
		
		for(Country c : reachableCountries)
		{
			sb.append("- ").append(c.toString()).append("\n");
		}
		
		if(!reachableCountries.isEmpty())
			//delete the last newLine character 
			sb.deleteCharAt(sb.length() - 1);
		
		return sb.toString();
	}

	@FXML
    void initialize() 
    {
		assert annoTextField != null : "fx:id=\"annoTextField\" was not injected: check your FXML file 'Scene_lab09.fxml'.";
        assert calcolaConfiniButton != null : "fx:id=\"calcolaConfiniButton\" was not injected: check your FXML file 'Scene_lab09.fxml'.";
        assert statoComboBox != null : "fx:id=\"statoComboBox\" was not injected: check your FXML file 'Scene_lab09.fxml'.";
        assert calcolaStatiRaggiungibiliButton != null : "fx:id=\"calcolaStatiRaggiungibiliButton\" was not injected: check your FXML file 'Scene_lab09.fxml'.";
        assert resultTextArea != null : "fx:id=\"resultTextArea\" was not injected: check your FXML file 'Scene_lab09.fxml'.";
    
        this.annoTextField.setTextFormatter(new TextFormatter<>(change -> 
        {
        	String input = change.getText();
        	
        	if(input == null || input.isEmpty())
        		return change;
        	
        	int insertedChars = this.annoTextField.getText().length();
        	int allowedChars = MAX_CHARS_ANNO - insertedChars;
        	
        	if(input.length() > allowedChars)
        		input = input.substring(0,allowedChars);
        	
        	if(!input.matches("[\\d]+"))
        		input = input.replaceAll("[\\D]+", "");
        	
        	if(insertedChars == 0 && input.matches("(0)+(.)*"))
        		input = input.replaceFirst("(0)+", "");
        	
        	change.setText(input);
        	
        	return change;
        }));
    }
    
    public void setModel(CountriesModel model) 
    {
    	this.model = model;
    }
}

