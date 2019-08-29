package controller;

import com.sun.org.apache.bcel.internal.generic.TABLESWITCH;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import model.Database;
import model.Korisnik;
import model.Stan;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import model.Ugovor;

import java.net.URL;
import java.util.ResourceBundle;
public class StrUgovor implements Initializable{
    @FXML
    TableView stanTablica;

    @FXML
    TableColumn tblAdresa;

    @FXML
    TableColumn tblBrojKvadrata;

    @FXML
    TableColumn tblCijena;

    @FXML
    TableColumn tblBrojSoba;

    @FXML
    TableColumn tblVrstaStana;

    @FXML
    TableColumn tblMjesto;

    @FXML
    TableColumn tblIme;

    @FXML
    TableColumn tblPrezime;

    @FXML
    TextArea vOpis;

    @FXML
    DatePicker vDatum;

    @FXML
    Button zahtjevBtn;


    Stan stan = new Stan();

    Stan selectedStan = null;

    Ugovor selectedUgovor = null;





    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.tblAdresa.setCellValueFactory(new PropertyValueFactory<>("adresaStana"));
        this.tblBrojKvadrata.setCellValueFactory(new PropertyValueFactory<>("brojKvadrata"));
        this.tblBrojSoba.setCellValueFactory(new PropertyValueFactory<>("brojSoba"));
        this.tblCijena.setCellValueFactory(new PropertyValueFactory<>("cijena"));
        this.tblVrstaStana.setCellValueFactory(new PropertyValueFactory<>("VrstaStana"));
        this.tblMjesto.setCellValueFactory(new PropertyValueFactory<>("Mjesto"));
        this.tblIme.setCellValueFactory(new PropertyValueFactory<>("ime"));
        this.tblPrezime.setCellValueFactory(new PropertyValueFactory<>("prezime"));


        this.popuniStanove();

    }
    private void popuniStanove(){
        ObservableList<Stan> s = (ObservableList<Stan>) stan.fullSelect();
        this.stanTablica.setItems(s);
    }

    @FXML
    public void zahtjev(ActionEvent ev){
        String opis=this.vOpis.getText();
        Date datum = Date.valueOf(vDatum.getValue());


        Stan s = (Stan) this.stanTablica.getSelectionModel().getSelectedItem();
        int stanID=0;
        stanID = s.getId();

        if(opis.equals("")||datum.equals("")||stanID==0){
            return;
        }

        if(this.selectedUgovor!=null){
            this.selectedUgovor.setDatum(datum);
            this.selectedUgovor.setOpis(opis);
            this.selectedUgovor.setId(stanID);
            this.selectedUgovor.setPotvrda(0);

            Ugovor.update(this.selectedUgovor);
            this.selectedUgovor=null;
        }
        else {
            Ugovor u = new Ugovor(0,opis,datum,stanID,0);
            Ugovor.add(u);
        }



    }


}