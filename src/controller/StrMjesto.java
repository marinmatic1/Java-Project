package controller;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Korisnik;
import model.Mjesto;

import java.net.URL;
import java.util.ResourceBundle;

public class StrMjesto implements Initializable{
    @FXML
    TextField vMjesto;

    @FXML
    TableColumn tblMjesto;

    @FXML
    Button dodajBtn;

    @FXML
    Button izbrisiBtn;

    @FXML
    Button odjavaBtn;

    @FXML
    Button adminBtn;

    @FXML
    TableView mjestaTablica;

    Mjesto selectedMjesto = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.tblMjesto.setCellValueFactory(new PropertyValueFactory<>("naziv"));
        popuniMjesta();
    }
    private void popuniMjesta() {
        ObservableList<Mjesto> mjesto = (ObservableList<Mjesto>) Mjesto.select();
        this.mjestaTablica.setItems(mjesto);
    }

    @FXML
    public void dodajMjesto(ActionEvent ev) throws Exception {
        String mjesto = this.vMjesto.getText();
        if (mjesto.equals("")){
            return;
        }
        if (this.selectedMjesto!=null){
            this.selectedMjesto.setNaziv(mjesto);
            Mjesto.update(this.selectedMjesto);
            this.selectedMjesto=null;
            this.dodajBtn.setText("Dodaj mjesto");
        }
        else{
            Mjesto m = new Mjesto(0,mjesto);
            Mjesto.add(m);
        }
        this.popuniMjesta();
        this.vMjesto.setText("");
    }

    @FXML
    public void izbrisi(ActionEvent ev){
        Mjesto mjesto = (Mjesto) this.mjestaTablica.getSelectionModel().getSelectedItem();
        if(mjesto==null){
            return;
        }
        else{
            Mjesto.remove(mjesto);
            this.popuniMjesta();
        }
    }

    public void administracija(ActionEvent ev){
        Utils u = new Utils();
        u.showNewWindow("Administracija", ev);
    }

    public void odjava(ActionEvent ev){
        Utils u = new Utils();
        u.showNewWindow("login", ev);
    }

}
