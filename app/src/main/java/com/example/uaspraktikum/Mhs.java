package com.example.uaspraktikum;

import java.io.Serializable;

public class Mhs implements Serializable {

    private String nim;
    private String nama;
    private String prodi;
    private String key;
    //private String location;

    public Mhs(){

    }

    public Mhs(String nim1, String nma, String p){
        nim = nim1;
        nama = nma;
        prodi = p;
    }

    public String getNim()
    {
        return nim;
    }

    public void setNim(String nim)
    {
        this.nim = nim;
    }

    public String getNama()
    {
        return nama;
    }

    public void setNama(String nama)
    {
        this.nama = nama;
    }

    public String getProdi()
    {
        return prodi;
    }

    public void setProdi(String prodi)
    {
        this.prodi = prodi;
    }

    /*public String getLocation(){
    return location;
    }
    public void setLocation(String location){
        this.location = location;
    }*/

    public String getKey(){
        return key;
    }

    public void setKey(String key){
        this.key = key;
    }

public String toString(){
    return " "+nim+"" +
            ""+nama+"" +
            ""+prodi;
}

}