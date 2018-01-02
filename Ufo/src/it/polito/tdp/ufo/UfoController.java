package it.polito.tdp.ufo;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.ufo.bean.FasciaOraria;
import it.polito.tdp.ufo.bean.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;


public class UfoController {
	
	Model model;

	    @FXML // ResourceBundle that was given to the FXMLLoader
	    private ResourceBundle resources;

	    @FXML // URL location of the FXML file that was given to the FXMLLoader
	    private URL location;

	    @FXML // fx:id="boxShape"
	    private ComboBox<String> boxShape; // Value injected by FXMLLoader

	    @FXML // fx:id="boxOra"
	    private ComboBox<FasciaOraria> boxOra; // Value injected by FXMLLoader
	    

	    @FXML // fx:id="txtDistance"
	    private TextField txtDistance; // Value injected by FXMLLoader

	    @FXML // fx:id="txtResult"
	    private TextArea txtResult; // Value injected by FXMLLoader

	    @FXML
	    void doCreaGrafo(ActionEvent event) {
	    	
	    	FasciaOraria fo = boxOra.getValue();	 
			if(boxOra.getValue()==  null)
			  { txtResult.setText("Nessuna ora selezionata");
			  		return;}
			
			String shape  = boxShape.getValue();	 
			if(boxShape.getValue()==  null)
			  { txtResult.setText("Nessuna shape selezionata");
			  		return;}
			
			String ks = txtDistance.getText();
			if(ks.equals(" ")|| ks==null|| ks.length()<1  ){    // || !ks.matches("[a-zA-Z]+")
				 txtResult.appendText("ERRORE: inserire una stringa \n");
				 return;
			 }
			double k = 0.0 ;
			try {	k = Integer.parseInt(ks);
			} catch (NumberFormatException e) {
				txtResult.appendText("Errore: devi inserire un numero \n");
				return ;
			}

		    model.creaGrafo(fo,shape,k);
		    
//			Integer best= model.getBest();
//			txtResult.appendText(" la circoscrizione con il maggior bilancio demografioco è :   "+ best.toString());

	    }
	    

	    @FXML // This method is called by the FXMLLoader when initialization is complete
	    void initialize() {
	        assert boxShape != null : "fx:id=\"boxShape\" was not injected: check your FXML file 'ufo.fxml'.";
	        assert boxOra != null : "fx:id=\"boxOra\" was not injected: check your FXML file 'ufo.fxml'.";
	        assert txtDistance != null : "fx:id=\"txtDistance\" was not injected: check your FXML file 'ufo.fxml'.";
	        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'ufo.fxml'.";

	    }
	    
	public void setModel(Model model) {
	 this.model=model;	
	 this.boxOra.getItems().addAll(model.getFasceOrarie());
	 this.boxShape.getItems().addAll(model.getAllShape());

	// this.boxAnno.getItems().addAll(model.getAnni());		
	}

}
