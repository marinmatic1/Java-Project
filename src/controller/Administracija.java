package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class Administracija {

    @FXML
    Button uKorisnicima;

    @FXML
    Button vOdjava;

    @FXML
    Button vStanovima;

    @FXML
    Button vMjesta;

    @FXML
    Button vVrstaStana;

    public void upravljajKorisnicima(ActionEvent ev){
        Utils u = new Utils();
        u.showNewWindow("strAdmin", ev);
    }

    public void odjava(ActionEvent ev){
        Utils u = new Utils();
        u.showNewWindow("login", ev);
    }

    public void upravljajStanovima(ActionEvent ev){
        Utils u = new Utils();
        u.showNewWindow("strStanovi", ev);
    }

}
