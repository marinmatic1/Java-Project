package controller;
import com.sun.org.apache.xml.internal.security.Init;
import com.sun.org.glassfish.external.arc.Taxonomy;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Korisnik;
import model.Mjesto;
import model.Stan;
import model.Ugovor;

import java.net.URL;
import java.util.ResourceBundle;
public class AdminUgovor implements Initializable {

    @FXML
    TableColumn vOpis;

    @FXML
    TableColumn vImeKlijenta;

    @FXML
    TableColumn vImeVlasnika;

    @FXML
    TableColumn vDatum;

    @FXML
    TableColumn vStanje;

    @FXML
    Button administracijaBtn;

    @FXML
    Button izbrisiBtn;

    @FXML
    TableView tableUgovor;

    Ugovor ugovor = new Ugovor();



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        



        this.vOpis.setCellValueFactory(new PropertyValueFactory<>("opis"));
        this.vDatum.setCellValueFactory(new PropertyValueFactory<>("datum"));
        this.vImeVlasnika.setCellValueFactory(new PropertyValueFactory<>("stan"));
        this.vStanje.setCellValueFactory(new PropertyValueFactory<>("potvrda"));
        this.vImeKlijenta.setCellValueFactory(new PropertyValueFactory<>("imeKlijenta"));

        popuniUgovore();

    }
    private void popuniUgovore(){
        ObservableList<Ugovor> u = (ObservableList<Ugovor>) ugovor.select();
        this.tableUgovor.setItems(u);
    }

}