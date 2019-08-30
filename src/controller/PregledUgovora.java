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
public class PregledUgovora implements Initializable {

    @FXML
    Button nazadBtn;

    Korisnik korisnik = new Korisnik();


    @Override
    public void initialize(URL location, ResourceBundle resources) {


    }


    public void nazad(ActionEvent ev){
        Utils u = new Utils();
        u.showNewWindow("usrVlasnik", ev);
    }
}
