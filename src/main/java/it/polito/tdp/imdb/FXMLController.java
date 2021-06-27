/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.imdb;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.imdb.model.Director;
import it.polito.tdp.imdb.model.Model;
import it.polito.tdp.imdb.model.Vicino;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnAdiacenti"
    private Button btnAdiacenti; // Value injected by FXMLLoader

    @FXML // fx:id="btnCercaAffini"
    private Button btnCercaAffini; // Value injected by FXMLLoader

    @FXML // fx:id="boxAnno"
    private ComboBox<Integer> boxAnno; // Value injected by FXMLLoader

    @FXML // fx:id="boxRegista"
    private ComboBox<Director> boxRegista; // Value injected by FXMLLoader

    @FXML // fx:id="txtAttoriCondivisi"
    private TextField txtAttoriCondivisi; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	txtResult.clear();
    	
    	Integer anno = boxAnno.getValue();
    	if(anno == null) {
    		txtResult.setText("Errore selezionare un anno");
    		return;
    	}
    	
    	this.model.creaGrafo(anno);
    	
    	txtResult.appendText("GRAFO CREATO \n");
    	txtResult.appendText("#VERTICI: "+this.model.nVertici()+"\n");
    	txtResult.appendText("#ARCHI: "+this.model.nArchi()+"\n");
    	
    	for(Director d : model.getGrafoDirector(anno) ) {
    		boxRegista.getItems().add(d);
    	}
    }

    @FXML
    void doRegistiAdiacenti(ActionEvent event) {
    	Director d = boxRegista.getValue();
    	if(d == null) {
    		txtResult.appendText("Errore nessun regista selezionato");
    		return;
    	}
    	List<Vicino> adiacente = model.getVicini(d);
    	txtResult.appendText("Registi adiacenti a: "+d+"\n");
    	if(adiacente.size() == 0) {
    		txtResult.appendText("NESSUNO \n");
    	}else {
    		for(Vicino v : adiacente) {
    			txtResult.appendText(v.getIdD()+" "+v.getPeso()+"\n");
    		}
    	}
    }

    @FXML
    void doRicorsione(ActionEvent event) {
    	
    	String attcond = txtAttoriCondivisi.getText();
    	
    	try {
    		int c = Integer.parseInt(attcond);
    		
    		Director d = boxRegista.getValue();
    		if(d == null) {
    			txtResult.appendText("Selezionare regista");
    			return;
    		}
    		
    		List<Director> lista = new ArrayList<>(model.getPercorso(c, d));
    		
    		for(Director di : lista) {
    			txtResult.appendText(di.toString()+"\n");
    		}
    		txtResult.appendText(model.getPeso()+"\n");
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnAdiacenti != null : "fx:id=\"btnAdiacenti\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCercaAffini != null : "fx:id=\"btnCercaAffini\" was not injected: check your FXML file 'Scene.fxml'.";
        assert boxAnno != null : "fx:id=\"boxAnno\" was not injected: check your FXML file 'Scene.fxml'.";
        assert boxRegista != null : "fx:id=\"boxRegista\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtAttoriCondivisi != null : "fx:id=\"txtAttoriCondivisi\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
   public void setModel(Model model) {
    	
    	this.model = model;
    	
    	for(int i = 2004; i<2007; i++) {
    		boxAnno.getItems().add(i);
    	}
    }
    
}
