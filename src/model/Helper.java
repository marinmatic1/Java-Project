package model;

public class Helper {
    public int id;
    public String s;

    public Helper(int id,String s){
        this.id=id;
        this.s=s;
    }

    public Helper(){}

    public int getId() {
        return id;
    }

    public String getS() {
        return s;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setS(String s) {
        this.s = s;
    }

    @Override
    public String toString() {
        return String.format(id + " " +s);
    }
}
