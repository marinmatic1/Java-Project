package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import model.Helper;

public class Stan {
    private int id;
    private int vlasnik;
    private String ime;
    private String prezime;
    private String adresaStana;
    private String brojKvadrata;
    private String brojSoba;
    private String cijena;
    private int mjesto;
    private int vrstaStana;
    private Image image;
    private String Mjesto;
    private String VrstaStana;

    public String getIme() {
        return ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public void setMjesto(String mjesto) {
        Mjesto = mjesto;
    }

    public void setVrstaStana(String vrstaStana) {
        VrstaStana = vrstaStana;
    }

    public Stan() {
    }

    public Stan (int id,String ime,String prezime,String adresaStana,String brojKvadrata,String brojSoba,String cijena,String Mjesto,String VrstaStana){
        this.id=id;
        this.ime=ime;
        this.prezime=prezime;
        this.adresaStana=adresaStana;
        this.brojKvadrata=brojKvadrata;
        this.cijena=cijena;
        this.Mjesto=Mjesto;
        this.VrstaStana=VrstaStana;
        this.brojSoba=brojSoba;
    }

    public Stan(int id, int vlasnik, String adresaStana, String brojKvadrata, String brojSoba, String cijena, int mjesto, int vrstaStana, Image image) {
        this.id = id;
        this.vlasnik = vlasnik;
        this.adresaStana = adresaStana;
        this.brojKvadrata = brojKvadrata;
        this.brojSoba = brojSoba;
        this.cijena = cijena;
        this.mjesto = mjesto;
        this.vrstaStana = vrstaStana;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVlasnik() {
        return vlasnik;
    }

    public void setVlasnik(int vlasnik) {
        this.vlasnik = vlasnik;
    }

    public String getAdresaStana() {
        return adresaStana;
    }

    public void setAdresaStana(String adresaStana) {
        this.adresaStana = adresaStana;
    }

    public String getBrojKvadrata() {
        return brojKvadrata;
    }

    public void setBrojKvadrata(String brojKvadrata) {
        this.brojKvadrata = brojKvadrata;
    }

    public String getBrojSoba() {
        return brojSoba;
    }

    public void setBrojSoba(String brojSoba) {
        this.brojSoba = brojSoba;
    }

    public String getCijena() {
        return cijena;
    }

    public void setCijena(String cijena) {
        this.cijena = cijena;
    }

    public String getMjesto() {
        return Mjesto;
    }

    public int getMjestoInt(){ return mjesto; }

    public void setMjesto(int mjesto) {
        this.mjesto = mjesto;
    }

    public String getVrstaStana() {
        return VrstaStana;
    }

    public int getVrstaStanaInt(){return vrstaStana;}

    public void setVrstaStana(int vrstaStana) {
        this.vrstaStana = vrstaStana;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public static Stan add (Stan s){
        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(SwingFXUtils.fromFXImage(s.getImage(), null), "jpg", os);
            InputStream fis = new ByteArrayInputStream(os.toByteArray());

            PreparedStatement stmnt = Database.CONNECTION.prepareStatement("INSERT INTO stan VALUES (null, ?, ?, ?, ?, ?, ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
            stmnt.setInt(1, s.getVlasnik());
            stmnt.setString(2, s.getAdresaStana());
            stmnt.setString(3, s.getBrojKvadrata());
            stmnt.setString(4, s.getBrojSoba());
            stmnt.setString(5, s.getCijena());
            stmnt.setInt(6, s.getMjestoInt());
            stmnt.setInt(7, s.getVrstaStanaInt());
            stmnt.setBinaryStream(8, fis);
            stmnt.executeUpdate();
            ResultSet rs = stmnt.getGeneratedKeys();
            if (rs.next()){
                s.setId(rs.getInt(1));
            }
            return s;
        } catch (SQLException e) {
            System.out.println("Stan nije dodan: "+ e.getMessage());
            return null;
        } catch (IOException e) {
            System.out.println("Stan nije dodan: "+ e.getMessage());
            return null;
        }
    }

    public static boolean remove(Stan s) {
        try {
            PreparedStatement stmnt = Database.CONNECTION.prepareStatement("DELETE FROM stan WHERE id_stan=?");
            stmnt.setInt(1, s.getId());
            stmnt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Stan nije obrisan: " + e.getMessage());
            return false;
        }
    }

    public static boolean update(Stan s) {
        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(SwingFXUtils.fromFXImage(s.getImage(), null), "jpg", os);
            InputStream fis = new ByteArrayInputStream(os.toByteArray());

            PreparedStatement stmnt = Database.CONNECTION.prepareStatement("UPDATE stan SET vlasnik_fk=?, adresa=?, brojKvadrata=?, brojSoba=?, cijena=?, mjesto_fk=?, vrstaStana_fk=?  WHERE id_stan=?");
            stmnt.setInt(1, s.getVlasnik());
            stmnt.setString(2, s.getAdresaStana());
            stmnt.setString(3, s.getBrojKvadrata());
            stmnt.setString(4, s.getBrojSoba());
            stmnt.setString(5, s.getCijena());
            stmnt.setInt(6, s.getMjestoInt());
            stmnt.setInt(7, s.getVrstaStanaInt());
            stmnt.setInt(8, s.getId());
            stmnt.setBinaryStream(4, fis);
            stmnt.executeUpdate();
            return true;
        } catch (SQLException | IOException e) {
            System.out.println("Stan nije uređen: " + e.getMessage());
            return false;
        }
    }

    public static List<Stan> select() {
        ObservableList<Stan> stan = FXCollections.observableArrayList();
        try {
            Statement stmnt = Database.CONNECTION.createStatement();
            ResultSet rs = stmnt.executeQuery("SELECT * FROM stan");
            while(rs.next()){
                Image fxSlika = null;
                try {
                    BufferedImage bImage = ImageIO.read(rs.getBinaryStream(5));
                    fxSlika = SwingFXUtils.toFXImage(bImage, null);
                } catch (NullPointerException | IOException ex) {
                    fxSlika = null;
                }

                stan.add(new Stan(
                        rs.getInt(1),
                        rs.getInt(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getInt(7),
                        rs.getInt(8),
                        fxSlika
                ));
            }
            return stan;
        } catch (SQLException e) {
            System.out.println("Stan se ne moze izvuci iz baze: " + e.getMessage());
            return stan;
        }
    }

    public static List<Stan> fullSelect(){
        ObservableList<Stan> stanovi = FXCollections.observableArrayList();
        try{
            Statement stmnt = Database.CONNECTION.createStatement();
            ResultSet rs = stmnt.executeQuery("SELECT id_stan,korisnik.ime,korisnik.prezime,adresa,brojKvadrata,brojSoba,cijena,mjesto.nazivMjesta,vrstastana.vrstaStana\n" +
                    "FROM stan,korisnik,mjesto,vrstastana\n" + "WHERE vlasnik_fk=korisnik.id_vlasnik AND mjesto_fk=mjesto.id_mjesto AND vrstaStana_fk=vrstastana.id_vrstaStana");
            while(rs.next()){
                stanovi.add(new Stan(
                    rs.getInt(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(5),
                    rs.getString(6),
                    rs.getString(7),
                    rs.getString(8),
                    rs.getString(9)
                ));
                }
            return stanovi;
            }catch (SQLException e){
            System.out.println("Stanovi se ne mogu izvuci iz baze: "+ e.getMessage());
            return stanovi;
        }

    }
    public String kIme;
    public Stan(String kIme,String Mjesto,String VrstaStana){
        this.kIme=kIme;
        this.Mjesto=Mjesto;
        this.VrstaStana=VrstaStana;
    }

    Helper h = new Helper(0,"");

    public ArrayList<Helper> list = new ArrayList<Helper>();

    /*public ArrayList loadAllUsers(){
        ArrayList<String> a = new ArrayList<String>();
        try {
            Statement stmnt = Database.CONNECTION.createStatement();
            ResultSet rs = stmnt.executeQuery("SELECT korisnik.korisnickoIme\n"+"FROM korisnik");

            while (rs.next()){
                a.add(rs.getString(1));
            }return a;
        } catch (SQLException e){
            System.out.println("Greska u dohvaćanju korisnickih imena"+ e.getMessage());
            return a;
        }
     }
*/
     public ArrayList loadAllMjesta(){
        ArrayList<String> a = new ArrayList<String>();
         try {
             Statement stmnt = Database.CONNECTION.createStatement();
             ResultSet rs = stmnt.executeQuery("SELECT mjesto.nazivMjesta\n"+"FROM mjesto");

             while (rs.next()){
                 a.add(rs.getString(1));
             }return a;
         } catch (SQLException e){
             System.out.println("Greska u dohvaćanju mjesta"+ e.getMessage());
             return a;
         }
     }

    public ArrayList loadAllVrste(){
        ArrayList<String> a = new ArrayList<String>();
        try {
            Statement stmnt = Database.CONNECTION.createStatement();
            ResultSet rs = stmnt.executeQuery("SELECT vrstastana.vrstaStana\n"+"FROM mjesto");

            while (rs.next()){
                a.add(rs.getString(1));
            }return a;
        } catch (SQLException e){
            System.out.println("Greska u dohvaćanju vrsta stana"+ e.getMessage());
            return a;
        }
    }

    public List<Helper> loadAllUsers(){
        ObservableList<Helper> a = FXCollections.observableArrayList();
        //ArrayList<Helper> a = new ArrayList<Helper>();
        try {
            Statement stmnt = Database.CONNECTION.createStatement();
            ResultSet rs = stmnt.executeQuery("SELECT korisnik.id_vlasnik,korisnik.korisnickoIme\n"+"FROM korisnik");

            while (rs.next()){
                a.add(new Helper(rs.getInt(1),
                rs.getString(2)));
            }return a;
        } catch (SQLException e){
            System.out.println("Greska u dohvaćanju korisnickih imena"+ e.getMessage());
            return a;
        }
    }








}
