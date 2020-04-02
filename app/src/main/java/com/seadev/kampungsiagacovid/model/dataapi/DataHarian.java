package com.seadev.kampungsiagacovid.model.dataapi;

import com.google.gson.annotations.SerializedName;

public class DataHarian {

    @SerializedName("attributes")
    private Attributes attributes;

    public DataHarian() {
    }

    public DataHarian(Attributes attributes) {
        this.attributes = attributes;
    }

    public Attributes getAttributes() {
        return attributes;
    }

    public class Attributes {
        @SerializedName("Hari_ke")
        private int hari;
        @SerializedName("Tanggal")
        private long tgl;
        @SerializedName("Jumlah_Kasus_Kumulatif")
        private int KasusTotal;
        @SerializedName("Jumlah_pasien_dalam_perawatan")
        private int dalamPerawatan;
        @SerializedName("Jumlah_Pasien_Sembuh")
        private int sembuh;
        @SerializedName("Jumlah_Pasien_Meninggal")
        private int meninggal;

        @SerializedName("Jumlah_Kasus_Baru_per_Hari")
        private int kasusBaru;
        @SerializedName("Jumlah_Kasus_Sembuh_per_Hari")
        private int sembuhBaru;
        @SerializedName("Jumlah_Kasus_Dirawat_per_Hari")
        private int dirawatBaru;
        @SerializedName("Jumlah_Kasus_Meninggal_per_Hari")
        private int meninggalBaru;

        @SerializedName("Persentase_Pasien_dalam_Perawatan")
        private double persenPerawatan;
        @SerializedName("Persentase_Pasien_Sembuh")
        private double persenSembuh;
        @SerializedName("Persentase_Pasien_Meninggal")
        private double persenMeninggal;

        public Attributes() {
        }

        public Attributes(int hari, int tgl, int kasusTotal, int dalamPerawatan, int sembuh, int meninggal) {
            this.hari = hari;
            this.tgl = tgl;
            KasusTotal = kasusTotal;
            this.dalamPerawatan = dalamPerawatan;
            this.sembuh = sembuh;
            this.meninggal = meninggal;
        }

        public Attributes(int hari, int tgl, int kasusTotal, int dalamPerawatan, int sembuh, int meninggal, int kasusBaru, int sembuhBaru, int dirawatBaru, int meninggalBaru, double persenPerawatan, double persenSembuh, double persenMeninggal) {
            this.hari = hari;
            this.tgl = tgl;
            KasusTotal = kasusTotal;
            this.dalamPerawatan = dalamPerawatan;
            this.sembuh = sembuh;
            this.meninggal = meninggal;
            this.kasusBaru = kasusBaru;
            this.sembuhBaru = sembuhBaru;
            this.dirawatBaru = dirawatBaru;
            this.meninggalBaru = meninggalBaru;
            this.persenPerawatan = persenPerawatan;
            this.persenSembuh = persenSembuh;
            this.persenMeninggal = persenMeninggal;
        }

        public int getHari() {
            return hari;
        }

        public long getTgl() {
            return tgl;
        }

        public int getKasusTotal() {
            return KasusTotal;
        }

        public int getDalamPerawatan() {
            return dalamPerawatan;
        }

        public int getSembuh() {
            return sembuh;
        }

        public int getMeninggal() {
            return meninggal;
        }

        public int getKasusBaru() {
            return kasusBaru;
        }

        public int getSembuhBaru() {
            return sembuhBaru;
        }

        public int getDirawatBaru() {
            return dirawatBaru;
        }

        public int getMeninggalBaru() {
            return meninggalBaru;
        }

        public double getPersenPerawatan() {
            return persenPerawatan;
        }

        public double getPersenSembuh() {
            return persenSembuh;
        }

        public double getPersenMeninggal() {
            return persenMeninggal;
        }
    }

}
