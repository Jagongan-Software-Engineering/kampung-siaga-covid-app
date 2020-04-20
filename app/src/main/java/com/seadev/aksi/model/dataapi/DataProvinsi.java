package com.seadev.aksi.model.dataapi;

import com.google.gson.annotations.SerializedName;

public class DataProvinsi {

    @SerializedName("attributes")
    private Attribute attribute;
    @SerializedName("geometry")
    private Geometry geometry;

    public DataProvinsi(Attribute attribute, Geometry geometry) {
        this.attribute = attribute;
        this.geometry = geometry;
    }

    public Attribute getAttribute() {
        return attribute;
    }

    public void setAttribute(Attribute attribute) {
        this.attribute = attribute;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    public class Attribute {
        @SerializedName("Kode_Provi")
        private int kodeProv;
        @SerializedName("Provinsi")
        private String namaProv;
        @SerializedName("Kasus_Posi")
        private int kasusPositifl;
        @SerializedName("Kasus_Semb")
        private int kasusSembuh;
        @SerializedName("Kasus_Meni")
        private int kasusKasusManinggal;

        public Attribute(int kodeProv, String namaProv, int kasusPositifl, int kasusSembuh, int kasusKasusManinggal) {
            this.kodeProv = kodeProv;
            this.namaProv = namaProv;
            this.kasusPositifl = kasusPositifl;
            this.kasusSembuh = kasusSembuh;
            this.kasusKasusManinggal = kasusKasusManinggal;
        }

        public int getKodeProv() {
            return kodeProv;
        }

        public void setKodeProv(int kodeProv) {
            this.kodeProv = kodeProv;
        }

        public String getNamaProv() {
            return namaProv;
        }

        public void setNamaProv(String namaProv) {
            this.namaProv = namaProv;
        }

        public int getKasusPositifl() {
            return kasusPositifl;
        }

        public void setKasusPositifl(int kasusPositifl) {
            this.kasusPositifl = kasusPositifl;
        }

        public int getKasusSembuh() {
            return kasusSembuh;
        }

        public void setKasusSembuh(int kasusSembuh) {
            this.kasusSembuh = kasusSembuh;
        }

        public int getKasusKasusManinggal() {
            return kasusKasusManinggal;
        }

        public void setKasusKasusManinggal(int kasusKasusManinggal) {
            this.kasusKasusManinggal = kasusKasusManinggal;
        }
    }

    public class Geometry {
        @SerializedName("x")
        private double x;
        @SerializedName("y")
        private double y;

        public Geometry(double x, double y) {
            this.x = x;
            this.y = y;
        }

        public double getX() {
            return x;
        }

        public void setX(double x) {
            this.x = x;
        }

        public double getY() {
            return y;
        }

        public void setY(double y) {
            this.y = y;
        }
    }
}
