package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class Mjesto {
    private int id;
    private String naziv;


    public Mjesto() {
    }

    public Mjesto(int id, String naziv) {
        this.id = id;
        this.naziv = naziv;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }



    public static Mjesto add (Mjesto m){
        try {
            PreparedStatement stmnt = Database.CONNECTION.prepareStatement("INSERT INTO mjesto VALUES (null, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
            stmnt.setString(1, m.getNaziv());

            stmnt.executeUpdate();
            ResultSet rs = stmnt.getGeneratedKeys();
            if (rs.next()){
                m.setId(rs.getInt(1));
            }
            return m;
        } catch (SQLException e) {
            System.out.println("Mjesto nije dodano: "+ e.getMessage());
            return null;
        }
    }

    public static boolean remove(Mjesto m) {
        try {
            PreparedStatement stmnt = Database.CONNECTION.prepareStatement("DELETE FROM mjesto WHERE id_mjesto=?");
            stmnt.setInt(1, m.getId());
            stmnt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Mjesto nije obrisano: " + e.getMessage());
            return false;
        }
    }

    public static boolean update(Mjesto m) {
        try {
            PreparedStatement stmnt = Database.CONNECTION.prepareStatement("UPDATE mjesto SET nazivMjesta=? WHERE id_mjesto=?");
            stmnt.setString(1, m.getNaziv());
            stmnt.setInt(2, m.getId());
            stmnt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Mjesto nije uređeno: " + e.getMessage());
            return false;
        }
    }

    public static List<Mjesto> select() {
        ObservableList<Mjesto> mjesto = FXCollections.observableArrayList();
        try {
            Statement stmnt = Database.CONNECTION.createStatement();
            ResultSet rs = stmnt.executeQuery("SELECT * FROM mjesto");


            while(rs.next()){
                mjesto.add(new Mjesto(
                        rs.getInt(1),
                        rs.getString(2)
                ));
            }
            return mjesto;
        } catch (SQLException e) {
            System.out.println("Mjesto se ne moze izvuci iz baze: " + e.getMessage());
            return mjesto;
        }
    }
}
