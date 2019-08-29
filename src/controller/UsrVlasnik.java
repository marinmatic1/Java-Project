package controller;

import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
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
import java.sql.Statement;
import java.util.*;

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

    @FXML
    TextField vAdresa;

    @FXML
    TextField vBrojKvadrata;

    @FXML
    TextField vCijena;

    @FXML
    TextField vMjesto;

    @FXML
    TextField vVrstaStana;

    @FXML
    TextField vBrojSoba;

    @FXML
    Button odjavaBtn;

    @FXML
    Button dodajBtn;

    @FXML
    ChoiceBox chBox;

    @FXML
    Button ukloniBtn;

    @FXML
    ChoiceBox chBoxVrsta;


    Stan selectedStan = null;

    Stan stan = new Stan();

    Login trenutniLogirani = new Login();

    public static Korisnik uneseniKorisnik;

    ObservableList<String> mjesta = FXCollections.observableArrayList();
    ObservableList<String> vrstaStana = FXCollections.observableArrayList();


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

        this.vIme.setText(trenutniLogirani.dohvatiIme());
        this.vPrezime.setText(trenutniLogirani.dohvatiPrezime());
        this.vKorisnickoIme.setText(trenutniLogirani.dohvatiLogiranog());

        try {
            Statement stmnt = Database.CONNECTION.createStatement();
            ResultSet rs = stmnt.executeQuery("SELECT mjesto.nazivMjesta FROM mjesto");

            while (rs.next()) {
                this.mjesta.add(rs.getString(1));
            }
         } catch (SQLException e) {
            System.out.println("Mjesto se ne moze izvuci iz baze: " + e.getMessage());
        }

        chBox.setItems(this.mjesta);
        chBox.setTooltip(new Tooltip("Odaberi mjesto"));
        chBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                vMjesto.setText(mjesta.get(newValue.intValue()));
            }
        });

        this.vrstaStana.add(0,"namješten");
        this.vrstaStana.add(1,"nenamješten");

        chBoxVrsta.setItems(vrstaStana);
        chBoxVrsta.setTooltip(new Tooltip("Odaberi vrstu stana"));
        chBoxVrsta.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                vVrstaStana.setText(vrstaStana.get(newValue.intValue()));
            }
        });
        this.popuniStanove();
    }


    private void popuniStanove(){
        ObservableList<Stan> s = (ObservableList<Stan>) stan.specialUsrSelect();
        this.tableStanovi.setItems(s);
    }

    @FXML
    public void dodaj(ActionEvent ev) throws Exception{
        String adresa = this.vAdresa.getText();
        String brojKvadrata = this.vBrojKvadrata.getText();
        String brojSoba = this.vBrojSoba.getText();
        String cijena = this.vCijena.getText();
        String mjesto = this.vMjesto.getText();
        String vrstaStana = this.vVrstaStana.getText();

        int vlasnikFK=trenutniLogirani.dohvatiID();
        int mjestoFK=0;
        int vrstaStanaFK=0;

        try {
            PreparedStatement stmnt = Database.CONNECTION.prepareStatement("SELECT mjesto.id_mjesto,vrstastana.id_vrstaStana FROM mjesto,vrstastana WHERE nazivMjesta=? AND vrstaStana=?");
            stmnt.setString(1, mjesto);
            stmnt.setString(2, vrstaStana);
            ResultSet rs = stmnt.executeQuery();

            if (rs.next()) {
                mjestoFK = rs.getInt(1);
                vrstaStanaFK = rs.getInt(2);
            }
        }
            catch(SQLException e){
                e.printStackTrace();
            }

            if(vlasnikFK==0 || mjestoFK==0 || vrstaStanaFK==0){
                return;
            }

            if(adresa.equals("")||brojKvadrata.equals("")||brojSoba.equals("")||cijena.equals("")||mjesto.equals("")&(vrstaStana=="namješten"||vrstaStana=="nenamješten")){
                return;
            }

            if (this.selectedStan != null){
                this.selectedStan.setAdresaStana(adresa);
                this.selectedStan.setBrojKvadrata(brojKvadrata);
                this.selectedStan.setBrojSoba(brojSoba);
                this.selectedStan.setCijena(cijena);
                this.selectedStan.setVlasnik(vlasnikFK);
                this.selectedStan.setMjesto(mjestoFK);
                this.selectedStan.setVrstaStana(vrstaStanaFK);


                Stan.update(this.selectedStan);
                this.selectedStan=null;
                this.popuniStanove();
            } else{
                Stan s = new Stan(0,vlasnikFK,adresa,brojKvadrata,brojSoba,cijena,mjestoFK,vrstaStanaFK);
                Stan.add(s);
                this.popuniStanove();
            }


    }



    @FXML
    public void ukloni(ActionEvent ev){
        Stan stan = (Stan) this.tableStanovi.getSelectionModel().getSelectedItem();
        if(stan==null){
            return;
        }
        else{
            Stan.remove(stan);
            this.popuniStanove();
        }
    }

    @FXML
    public void odjava(ActionEvent ev){
        Utils u = new Utils();
        u.showNewWindow("login", ev);
    }







}
