package controller;

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

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class StrUpravljanjeStanovima implements Initializable {

    @FXML
    TextField vVrstaStana;

    @FXML
    TextField vMjesto;

    @FXML
    TableColumn tblAdresa;

    @FXML
    TableColumn tblBrojKvadrata;

    @FXML
    TableColumn tblBrojSoba;

    @FXML
    TableColumn tblCijena;

    @FXML
    TableColumn tblVrstaStana;

    @FXML
    TableColumn tblMjesto;

    @FXML
    TableView vlasnikTablica;

    @FXML
    TextField vAdresa;

    @FXML
    TextField vBrojKvadrata;

    @FXML
    TextField vBrojSoba;

    @FXML
    TextField vCijena;

    @FXML
    TableColumn tblIme;

    @FXML
    TableColumn tblPrezime;

    @FXML
    Button btnUpravljajKorisnicima;

    @FXML
    Button btnDodajStan;

    @FXML
    TextField vVlasnik;

    @FXML
    Button vOdjava;

    @FXML
    Button ukloniBtn;

    @FXML
    ChoiceBox cbVlasnik;

    @FXML
    ChoiceBox cbMjesto;

    @FXML
    ChoiceBox cbVrsta;

    Stan selectedStan = null;

    Stan stan = new Stan();



    public static Korisnik uneseniKorisnik;

    ObservableList<String> mjesta = FXCollections.observableArrayList();
    ObservableList<String> vlasnik = FXCollections.observableArrayList();
    ObservableList<String> vrsta = FXCollections.observableArrayList();

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


        try {
            Statement stmnt = Database.CONNECTION.createStatement();
            ResultSet rs = stmnt.executeQuery("SELECT mjesto.nazivMjesta FROM mjesto");

            while (rs.next()) {
                this.mjesta.add(rs.getString(1));
            }
        } catch (SQLException e) {
            System.out.println("Mjesto se ne moze izvuci iz baze: " + e.getMessage());
        }

        try {
            Statement stmnt = Database.CONNECTION.createStatement();
            ResultSet rs = stmnt.executeQuery("SELECT korisnik.korisnickoIme,korisnik.uloga FROM korisnik");

            while (rs.next()) {
                if(rs.getString(2).equals("VLASNIK")){
                    this.vlasnik.add(rs.getString(1));
                }
                else{
                    continue;
                }
            }
        } catch (SQLException e) {
            System.out.println("Mjesto se ne moze izvuci iz baze: " + e.getMessage());
        }

        this.vrsta.add(0,"namješten");
        this.vrsta.add(1,"nenamješten");
        cbVlasnik.setItems(this.vlasnik);
        cbVlasnik.setTooltip(new Tooltip("Odaberi vlasnika"));
        cbVlasnik.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                vVlasnik.setText(vlasnik.get(newValue.intValue()));
            }
        });
        cbMjesto.setItems(this.mjesta);
        cbMjesto.setTooltip(new Tooltip("Odaberi mjesto"));
        cbMjesto.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                vMjesto.setText(mjesta.get(newValue.intValue()));
            }
        });
        cbVrsta.setItems(this.vrsta);
        cbVrsta.setTooltip(new Tooltip("Odaberi vrstu stana"));
        cbVrsta.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                vVrstaStana.setText(vrsta.get(newValue.intValue()));
            }
        });

        this.popuniStanove();
    }
    private void popuniStanove(){
        ObservableList<Stan> s = (ObservableList<Stan>) stan.fullSelect();
        this.vlasnikTablica.setItems(s);
    }
    public void upravljajKorisnicima(ActionEvent ev){
        Utils u = new Utils();
        u.showNewWindow("Administracija", ev);
    }

    @FXML
    public void dodajStan(ActionEvent ev) throws Exception{
        String adresa = this.vAdresa.getText();
        String brojKvadrata = this.vBrojKvadrata.getText();
        String brojSoba = this.vBrojSoba.getText();
        String cijena = this.vCijena.getText();
        String mjesto = this.vMjesto.getText();
        String vrstaStana = this.vVrstaStana.getText();
        String vlasnik = this.vVlasnik.getText();
        int vlasnikFK=0;
        int mjestoFK=0;
        int vrstaStanaFK=0;


        try{
            PreparedStatement stmnt = Database.CONNECTION.prepareStatement("SELECT korisnik.id_vlasnik,mjesto.id_mjesto,vrstastana.id_vrstaStana FROM korisnik,mjesto,vrstastana WHERE korisnickoIme=? AND nazivMjesta=? AND vrstaStana=?");
            stmnt.setString(1,vlasnik);
            stmnt.setString(2,mjesto);
            stmnt.setString(3,vrstaStana);
            ResultSet rs = stmnt.executeQuery();

            if(rs.next()){
                vlasnikFK=rs.getInt(1);
                mjestoFK=rs.getInt(2);
                vrstaStanaFK=rs.getInt(3);
                this.uneseniKorisnik = Korisnik.get(rs.getInt(1));
                if(uneseniKorisnik.getUloga().equals("KLIJENT")||uneseniKorisnik.getUloga().equals("ADMIN")){
                    return;
                }
            }


        }catch(SQLException e){
            e.printStackTrace();
        }

        if(vlasnikFK==0 || mjestoFK==0 || vrstaStanaFK==0){
            return;
        }


        if(adresa.equals("")||brojKvadrata.equals("")||brojSoba.equals("")||cijena.equals("")||vlasnik.equals("")||mjesto.equals("")&(vrstaStana=="namješten"||vrstaStana=="nenamješten")){
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
            this.btnDodajStan.setText("Dodaj Stan");
            this.popuniStanove();
        } else{
            Stan s = new Stan(0,vlasnikFK,adresa,brojKvadrata,brojSoba,cijena,mjestoFK,vrstaStanaFK);
            Stan.add(s);
            this.popuniStanove();
        }
    }

    @FXML
    public void ukloni(ActionEvent ev){
        Stan s = (Stan) this.vlasnikTablica.getSelectionModel().getSelectedItem();
        Stan.remove(s);
        this.popuniStanove();
    }

    @FXML
    public void odjava(ActionEvent ev){
        Utils u = new Utils();
        u.showNewWindow("login", ev);
    }





}
