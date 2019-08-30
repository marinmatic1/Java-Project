package controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import model.Korisnik;

import javax.xml.soap.Text;
import java.net.URL;
import java.nio.CharBuffer;
import java.util.ResourceBundle;

public class Registracija implements Initializable{
    @FXML
    TextField vIme;

    @FXML
    TextField vPrezime;

    @FXML
    TextField vKorisnickoIme;

    @FXML
    TextField vLozinka;

    @FXML
    TextField vUloga;

    @FXML
    TextField vBrojTelefona;

    @FXML
    Button dodajBtn;

    @FXML
    Button loginBtn;

    @FXML
    Label label;

    @FXML
    TextField vLozinka1;

    @FXML
    ChoiceBox cbUloga;


    Korisnik selectedUser = null;

    ObservableList<String> uloga = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.uloga.add(0,"KLIJENT");
        this.uloga.add(1,"VLASNIK");
        cbUloga.setItems(uloga);

        cbUloga.setTooltip(new Tooltip("Odaberi ulogu..."));
        cbUloga.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                vUloga.setText(uloga.get(newValue.intValue()));
            }
        });
    }

    @FXML
    public void dodajKorisnika(ActionEvent ev) throws Exception {
        String ime = this.vIme.getText();
        String prezime = this.vPrezime.getText();
        String kIme = this.vKorisnickoIme.getText();
        String lozinka = this.vLozinka.getText();
        String uloga = this.vUloga.getText();
        String brojTelefona = this.vBrojTelefona.getText();
        String lozinka2=this.vLozinka1.getText();

        if(!lozinka.equals(lozinka2)){
            label.setTextFill(Color.web("#ff0000"));
            label.setText("Lozinka se ne podudara!");
            return;
        }

        if(uloga.equals("ADMIN")){
            label.setTextFill(Color.web("#0076a3"));
            label.setText("DOBAR POKUŠAJ ALI NE MOŽE hehehehe");
            return;
        }

        if(!(uloga.equals("VLASNIK") || uloga.equals("KLIJENT"))){
            label.setTextFill(Color.web("#ff0000"));
            label.setText("KLIJENT ili VLASNIK mora biti odabran!");
            return;
        }

        if (ime.equals("") || prezime.equals("") || kIme.equals("") || lozinka.equals("") || uloga.equals("") || brojTelefona.equals("")) {
            label.setTextFill(Color.web("#ff0000"));
            label.setText("Unesite potpune podatke!");
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
            label.setText("Korisnik uspješno registriran kao "+uloga+".");
        }

        this.vIme.setText("");
        this.vPrezime.setText("");
        this.vKorisnickoIme.setText("");
        this.vLozinka.setText("");
        this.vUloga.setText("");
        this.vBrojTelefona.setText("");

    }

    @FXML
    public void showLogin(ActionEvent a){
        Utils u = new Utils();
        u.showNewWindow("login", a);
    }




}
