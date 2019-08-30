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
import model.Ugovor;


import javax.xml.soap.Text;
import java.net.URL;
import java.util.ResourceBundle;
public class PregledUgovora implements Initializable {

    @FXML
    Button nazadBtn;

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
    TableView tableUgovor;

    @FXML
    Button prihvatiBtn;

    @FXML
    Button raskiniBtn;

    Ugovor ugovor = new Ugovor();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.vOpis.setCellValueFactory(new PropertyValueFactory<>("opis"));
        this.vDatum.setCellValueFactory(new PropertyValueFactory<>("datum"));
        this.vStanje.setCellValueFactory(new PropertyValueFactory<>("potvrda"));
        this.vImeKlijenta.setCellValueFactory(new PropertyValueFactory<>("imeKlijenta"));
        this.vImeVlasnika.setCellValueFactory(new PropertyValueFactory<>("imeVlasnika"));

        popuniUgovore();

    }
    private void popuniUgovore(){
        ObservableList<Ugovor> u = (ObservableList<Ugovor>) ugovor.fullSelect();
        this.tableUgovor.setItems(u);
    }


    @FXML
    public void prihvatiUgovor(ActionEvent ev){
        Ugovor u = (Ugovor) this.tableUgovor.getSelectionModel().getSelectedItem();
        if(u==null) {
            return;
        }
            try {
                PreparedStatement stmnt = Database.CONNECTION.prepareStatement("UPDATE ugovor SET Potvrda=? WHERE id_ugovor=?");
                stmnt.setString(1, "UGOVOR PRIHVAĆEN");

                stmnt.setInt(2, u.getId());
                stmnt.executeUpdate();
            } catch (SQLException e) {
                System.out.println("Ugovor nije uređen: " + e.getMessage());
            }

            popuniUgovore();
        }

    @FXML
    public void raskiniUgovor(ActionEvent ev){
        Ugovor u = (Ugovor) this.tableUgovor.getSelectionModel().getSelectedItem();
        if(u==null){
            return;
        }
        else {
            try {
                PreparedStatement stmnt = Database.CONNECTION.prepareStatement("UPDATE ugovor SET Potvrda=? WHERE id_ugovor=?");
                stmnt.setString(1, "UGOVOR ODBAČEN");

                stmnt.setInt(2, u.getId());
                stmnt.executeUpdate();
            } catch (SQLException e) {
                System.out.println("Ugovor nije uređen: " + e.getMessage());
            }

            popuniUgovore();
        }
    }




    public void nazad(ActionEvent ev){
        Utils u = new Utils();
        u.showNewWindow("usrVlasnik", ev);
    }
}
