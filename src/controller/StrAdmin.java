package controller;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Korisnik;

import java.net.URL;
import java.util.ResourceBundle;



public class StrAdmin implements Initializable {

    @FXML
    TableColumn korisnikID;

    @FXML
    TableColumn korisnikIme;

    @FXML
    TableColumn korisnikPrezime;

    @FXML
    TableColumn korisnickoIme;

    @FXML
    TableColumn korisnikLozinka;

    @FXML
    TableColumn korisnikUloga;

    @FXML
    TableColumn korisnikBroj;

    @FXML
    TableView korisnikTablica;

    @FXML
    TextField kImeTxt;

    @FXML
    TextField kPrezimeTxt;

    @FXML
    TextField kKimeTxt;

    @FXML
    TextField kLozinkaTxt;

    @FXML
    TextField kUlogaTxt;

    @FXML
    TextField kBrojTxt;

    @FXML
    Button dodajBtn;

    @FXML
    Button izbrisiBtn;

    @FXML
    Button stanoviBtn;

    @FXML
    Button vOdjava;

    @FXML
            ChoiceBox cbUloga;



    ObservableList<String> uloga = FXCollections.observableArrayList();



    Korisnik selectedUser = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.korisnikID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        this.korisnikIme.setCellValueFactory(new PropertyValueFactory<>("ime"));
        this.korisnikPrezime.setCellValueFactory(new PropertyValueFactory<>("prezime"));
        this.korisnickoIme.setCellValueFactory(new PropertyValueFactory<>("korisnickoIme"));
        this.korisnikLozinka.setCellValueFactory(new PropertyValueFactory<>("lozinka"));
        this.korisnikUloga.setCellValueFactory(new PropertyValueFactory<>("uloga"));
        this.korisnikBroj.setCellValueFactory(new PropertyValueFactory<>("brojTelefona"));

        this.uloga.add(0,"KLIJENT");
        this.uloga.add(1,"VLASNIK");
        this.uloga.add(2,"ADMIN");
        cbUloga.setItems(uloga);

        cbUloga.setTooltip(new Tooltip("Odaberi ulogu.."));
        cbUloga.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                kUlogaTxt.setText(uloga.get(newValue.intValue()));
            }
        });



        this.popuniKorisnike();
    }


    private void popuniKorisnike() {
        ObservableList<Korisnik> korisnici = (ObservableList<Korisnik>) Korisnik.select();
        this.korisnikTablica.setItems(korisnici);
    }


    @FXML
    public void dodajKorisnika(ActionEvent ev) throws Exception {
        String ime = this.kImeTxt.getText();
        String prezime = this.kPrezimeTxt.getText();
        String kIme = this.kKimeTxt.getText();
        String lozinka = this.kLozinkaTxt.getText();
        String uloga = this.kUlogaTxt.getText();
        String brojTelefona = this.kBrojTxt.getText();

        if (ime.equals("") || prezime.equals("") || kIme.equals("") || lozinka.equals("") || uloga.equals("") || brojTelefona.equals("")) {
            return;
        }
        if (this.selectedUser != null) {
            this.selectedUser.setIme(ime);
            this.selectedUser.setPrezime(prezime);
            this.selectedUser.setKorisnickoIme(kIme);
            this.selectedUser.setLozinka(lozinka);
            this.selectedUser.setUloga(uloga);
            this.selectedUser.setBrojTelefona(brojTelefona);

            Korisnik.update(this.selectedUser);
            this.selectedUser = null;
            this.dodajBtn.setText("Dodaj korisnika");
        } else {
            Korisnik k = new Korisnik(0, ime, prezime, kIme, lozinka, uloga, brojTelefona);
            Korisnik.add(k);
        }
        this.popuniKorisnike();

        this.kImeTxt.setText("");
        this.kPrezimeTxt.setText("");
        this.kKimeTxt.setText("");
        this.kLozinkaTxt.setText("");
        this.kUlogaTxt.setText("");
        this.kBrojTxt.setText("");

    }

    @FXML
    public void pobrisiKorisnika(ActionEvent ev){
        Korisnik korisnik = (Korisnik) this.korisnikTablica.getSelectionModel().getSelectedItem();
        if(korisnik==null){
            return;
        }
        else{
            Korisnik.remove(korisnik);
            this.popuniKorisnike();
        }
    }


    public void otvoriStanovi(ActionEvent ev){
        Utils u = new Utils();
        u.showNewWindow("Administracija", ev);
    }

    public void odjava(ActionEvent ev){
        Utils u = new Utils();
        u.showNewWindow("login", ev);
    }

}
