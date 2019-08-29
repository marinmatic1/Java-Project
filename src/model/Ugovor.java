package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.List;

public class Ugovor {
    private int id;
    private String opis;
    private Date datum;
    private int stan;
    private int potvrda;
    private String imeKlijenta;

    public Ugovor() {
    }

    public Ugovor(int id, String opis, Date datum, int stan, int potvrda,String imeKlijenta) {
        this.id = id;
        this.opis = opis;
        this.datum = datum;
        this.stan = stan;
        this.potvrda=potvrda;
        this.imeKlijenta=imeKlijenta;

    }

    public String getImeKlijenta() {
        return imeKlijenta;
    }

    public void setImeKlijenta(String imeKlijenta) {
        this.imeKlijenta = imeKlijenta;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public int getStan() {
        return stan;
    }

    public void setStan(int stan) {
        this.stan = stan;
    }

     public int getPotvrda() {
        return potvrda;
    }

    public void setPotvrda(int potvrda) {
        this.potvrda = potvrda;
    }

    public static Ugovor add (Ugovor u){
        try {
            PreparedStatement stmnt = Database.CONNECTION.prepareStatement("INSERT INTO ugovor VALUES (null, ?, ?, ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
            stmnt.setString(1, u.getOpis());
            stmnt.setDate(2, u.getDatum());
            stmnt.setInt(3, u.getStan());
            stmnt.setInt(4,u.getPotvrda());
            stmnt.setString(5,u.getImeKlijenta());
            stmnt.executeUpdate();
            ResultSet rs = stmnt.getGeneratedKeys();
            if (rs.next()){
                u.setId(rs.getInt(1));
            }
            return u;
        } catch (SQLException e) {
            System.out.println("Ugovor nije dodan: "+ e.getMessage());
            return null;
        }
    }

    public static boolean remove(Ugovor u) {
        try {
            PreparedStatement stmnt = Database.CONNECTION.prepareStatement("DELETE FROM ugovor WHERE id_ugovor=?");
            stmnt.setInt(1, u.getId());
            stmnt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Ugovor nije obrisan: " + e.getMessage());
            return false;
        }
    }

    public static boolean update(Ugovor u) {
        try {
            PreparedStatement stmnt = Database.CONNECTION.prepareStatement("UPDATE ugovor SET opis=?, datum=?, stan_fk=?, Potvrda=?, imeKlijenta=? WHERE id_ugovor=?");
            stmnt.setString(1, u.getOpis());
            stmnt.setDate(2, u.getDatum());
            stmnt.setInt(3, u.getStan());
            stmnt.setInt(4,u.getPotvrda());
            stmnt.setString(5,u.getImeKlijenta());

            stmnt.setInt(6, u.getId());
            stmnt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Ugovor nije uređen: " + e.getMessage());
            return false;
        }
    }

    public static List<Ugovor> select() {
        ObservableList<Ugovor> ugovor = FXCollections.observableArrayList();
        try {
            Statement stmnt = Database.CONNECTION.createStatement();
            ResultSet rs = stmnt.executeQuery("SELECT * FROM ugovor");


            while(rs.next()){
                ugovor.add(new Ugovor(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getDate(3),
                        rs.getInt(4),
                        rs.getInt(5),
                        rs.getString(5)
                ));
            }
            return ugovor;
        } catch (SQLException e) {
            System.out.println("Nisam uspio izvuci ugovor iz baze: " + e.getMessage());
            return ugovor;
        }
    }




}
