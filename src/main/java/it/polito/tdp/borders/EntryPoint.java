package it.polito.tdp.borders;

import javafx.application.Application;
import it.polito.tdp.borders.model.CountriesModel;
import it.polito.tdp.borders.model.reachablecountries.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class EntryPoint extends Application 
{
    @Override
    public void start(Stage stage) throws Exception 
    {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Scene_lab09.fxml"));
    	Parent root = loader.load();
        Scene scene = new Scene(root);
         
        FXMLController controller = loader.getController();
        
        //strategy pattern
        ReachableCountriesStrategy searchStrategy = new IterativeReachableCountries(); //change here
        CountriesModel model = new CountriesModel(searchStrategy);
        controller.setModel(model);
        
        stage.setTitle("Lab09 - Countries");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        launch(args);
    }

}
