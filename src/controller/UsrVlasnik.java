package controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import model.Database;
import model.Korisnik;
import model.Stan;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;


import javax.xml.soap.Text;
import java.net.URL;
import java.util.ResourceBundle;

public class UsrVlasnik implements Initializable{
    @FXML
    TableView tableStanovi;

    @FXML
    TableColumn columnAdresa;

    @FXML
    TableColumn columnBrojKvadrata;

    @FXML
    TableColumn columnBrojSoba;

    @FXML
    TableColumn columnCijena;

    @FXML
    TableColumn columnVrstaStana;

    @FXML
    TableColumn columnMjesto;

    @FXML
    TableColumn columnIme;

    @FXML
    TableColumn columnPrezime;

    @FXML
    TextArea vIme;

    @FXML
    TextArea vPrezime;

    @FXML
    TextArea vKorisnickoIme;

    Stan selectedStan = null;

    Stan stan = new Stan();

    Login l = new Login();

    public static Korisnik uneseniKorisnik;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.columnAdresa.setCellValueFactory(new PropertyValueFactory<>("adresaStana"));
        this.columnBrojKvadrata.setCellValueFactory(new PropertyValueFactory<>("brojKvadrata"));
        this.columnBrojSoba.setCellValueFactory(new PropertyValueFactory<>("brojSoba"));
        this.columnCijena.setCellValueFactory(new PropertyValueFactory<>("cijena"));
        this.columnVrstaStana.setCellValueFactory(new PropertyValueFactory<>("VrstaStana"));
        this.columnMjesto.setCellValueFactory(new PropertyValueFactory<>("Mjesto"));
        this.columnIme.setCellValueFactory(new PropertyValueFactory<>("ime"));
        this.columnPrezime.setCellValueFactory(new PropertyValueFactory<>("prezime"));

        this.vIme.setText(l.dohvatiIme());
        this.vPrezime.setText(l.dohvatiPrezime());
        this.vKorisnickoIme.setText(l.dohvatiLogiranog());

        this.popuniStanove();
    }
    private void popuniStanove(){
        ObservableList<Stan> s = (ObservableList<Stan>) stan.specialUsrSelect();
        this.tableStanovi.setItems(s);
    }







}
