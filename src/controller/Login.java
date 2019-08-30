package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import model.Database;
import model.Korisnik;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Login implements Initializable {
    @FXML
    TextField korisnikTxt;

    @FXML
    TextField lozinkaTxt;

    @FXML
    Button prijavaBtn;

    @FXML
    ImageView slikaImg;

    @FXML
    Button registracijaBtn;

    public static Korisnik logiraniKorisnik;

    @FXML
    public void login (ActionEvent a){
        String korisnickoIme = korisnikTxt.getText();
        String lozinka = lozinkaTxt.getText();
        try {
            PreparedStatement stmnt = Database.CONNECTION.prepareStatement("SELECT * FROM korisnik WHERE korisnickoIme=? AND lozinka=?");
            //PreparedStatement stmnt1 = Database.CONNECTION.prepareStatement("SELECT * FROM klijent WHERE korisnickoIme=? AND lozinka=?");
            stmnt.setString(1, korisnickoIme);
            stmnt.setString(2, lozinka);
            /*stmnt1.setString(1, korisnickoIme);
            stmnt1.setString(2, lozinka);*/
            ResultSet rs = stmnt.executeQuery();

            if (rs.next()) {
                this.logiraniKorisnik = Korisnik.get(rs.getInt(1));
                Utils u = new Utils();

                if (logiraniKorisnik.getUloga().equals("VLASNIK")) {
                    u.showNewWindow("usrVlasnik", a);
                }
                else if (logiraniKorisnik.getUloga().equals("KLIJENT")){
                    u.showNewWindow("usrKlijent", a);
                }
                else {
                    u.showNewWindow("Administracija", a);
                }
            } else {
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void registracija(ActionEvent a){
        Utils u = new Utils();
        u.showNewWindow("Registracija", a);
    }



    public String dohvatiLogiranog(){
        return this.logiraniKorisnik.getKorisnickoIme();
    }

    public int dohvatiID(){
        return this.logiraniKorisnik.getId();
    }

    public String dohvatiIme(){
        return this.logiraniKorisnik.getIme();
    }

    public String dohvatiPrezime(){
        return this.logiraniKorisnik.getPrezime();
    }

    public String dohvatiUlogu(){return this.logiraniKorisnik.getUloga();}

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
