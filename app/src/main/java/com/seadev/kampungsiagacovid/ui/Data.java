package com.seadev.kampungsiagacovid.ui;

public class Data {
    private String  nama, alamat ,rtrw, noHp;
    private String key;

    public Data(String nama, String alamat,String rtrw, String noHp ) {
        this.nama = nama;
        this.alamat = alamat;
        this.rtrw = rtrw;
        this.noHp = noHp;

    }

    public Data() {

    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getRtrw() {
        return rtrw;
    }

    public void setRtrw(String rtrw) {
        this.rtrw = rtrw;
    }

    public String getNoHp() {
        return noHp;
    }

    public void setNoHp(String noHp) {
        this.noHp = noHp;
    }
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }


    @Override
    public String toString() {
        return "" + nama + "";
    }
}


