package aisco.automaticreport.activityreport;

import java.util.*;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.ManyToOne;

import aisco.automaticreport.periodic.*;

@Entity(name = "automaticreport_activityreport_impl")
@Table(name = "automaticreport_activityreport_impl")
public class AutomaticReportActivityReportImpl extends AutomaticReportActivityReportComponent {
    protected String pembatasan;
    protected int sumbangan;
    protected int jasaLayanan;
    protected int penghasilanInvestasiJangkaPanjang;
    protected int penghasilanInvestasiLain;
    protected int penghasilanNetoTerealisasikanDanBelumTerealisasikanDariIJP;
    protected int lainLain;
    protected int pemenuhanProgramPembatasan;
    protected int pemenuhanPembatasanPemerolehanPeralatan;
    protected int berakhirnyaPembatasanWaktu;
    protected int bebanDanKerugianProgram;
    protected int bebanDanKerugianManajemendanUmum;
    protected int bebanDanKerugianPencarianDana;
    protected int kerugianAktuarialDanKewajibanTahunan;
    protected int asetNetoAwalTahun;

    @ManyToOne(targetEntity=aisco.automaticreport.periodic.AutomaticReportPeriodicComponent.class)
    public AutomaticReportPeriodicComponent periodic;

    public AutomaticReportActivityReportImpl() {
    }

    public AutomaticReportActivityReportImpl(AutomaticReportPeriodicComponent periodic, String pembatasan) {
        Random r = new Random();
        this.id = Math.abs(r.nextInt());
        this.pembatasan = pembatasan;
        this.sumbangan = 0;
        this.jasaLayanan = 0;
        this.penghasilanInvestasiJangkaPanjang = 0;
        this.penghasilanInvestasiLain = 0;
        this.penghasilanNetoTerealisasikanDanBelumTerealisasikanDariIJP = 0;
        this.lainLain = 0;
        this.pemenuhanProgramPembatasan = 0;
        this.pemenuhanPembatasanPemerolehanPeralatan = 0;
        this.berakhirnyaPembatasanWaktu = 0;
        this.bebanDanKerugianProgram = 0;
        this.bebanDanKerugianManajemendanUmum = 0;
        this.bebanDanKerugianPencarianDana = 0;
        this.kerugianAktuarialDanKewajibanTahunan = 0;
        this.asetNetoAwalTahun = 0;
        this.periodic = periodic;
    }

    public AutomaticReportActivityReportImpl(String pembatasan, int sumbangan, int jasaLayanan, int penghasilanInvestasiJangkaPanjang, 
            int penghasilanInvestasiLain, int penghasilanNetoTerealisasikanDanBelumTerealisasikanDariIJP, 
            int lainLain, int pemenuhanProgramPembatasan, int pemenuhanPembatasanPemerolehanPeralatan, int berakhirnyaPembatasanWaktu,
            int bebanDanKerugianProgram, int bebanDanKerugianManajemendanUmum, int bebanDanKerugianPencarianDana, 
            int kerugianAktuarialDanKewajibanTahunan, int asetNetoAwalTahun, AutomaticReportPeriodicComponent periodic) {

        Random r = new Random();
        this.id = Math.abs(r.nextInt());
        this.pembatasan = pembatasan;
        this.sumbangan = sumbangan;
        this.jasaLayanan = jasaLayanan;
        this.penghasilanInvestasiJangkaPanjang = penghasilanInvestasiJangkaPanjang;
        this.penghasilanInvestasiLain = penghasilanInvestasiLain;
        this.penghasilanNetoTerealisasikanDanBelumTerealisasikanDariIJP = penghasilanNetoTerealisasikanDanBelumTerealisasikanDariIJP;
        this.lainLain = lainLain;
        this.pemenuhanProgramPembatasan = pemenuhanProgramPembatasan;
        this.pemenuhanPembatasanPemerolehanPeralatan = pemenuhanPembatasanPemerolehanPeralatan;
        this.berakhirnyaPembatasanWaktu = berakhirnyaPembatasanWaktu;
        this.bebanDanKerugianProgram = bebanDanKerugianProgram;
        this.bebanDanKerugianManajemendanUmum = bebanDanKerugianManajemendanUmum;
        this.bebanDanKerugianPencarianDana = bebanDanKerugianPencarianDana;
        this.kerugianAktuarialDanKewajibanTahunan = kerugianAktuarialDanKewajibanTahunan;
        this.asetNetoAwalTahun = asetNetoAwalTahun;
        this.periodic = periodic;
    }
    
    public AutomaticReportPeriodicComponent getPeriodic() {
        return this.periodic;
    }

    public void setPeriodic(AutomaticReportPeriodicComponent periodic) {
        this.periodic = periodic;
    }

    public String getPembatasan() {
        return this.pembatasan;
    }

    public void setPembatasan(String pembatasan) {
        this.pembatasan = pembatasan;
    }

    public int getSumbangan() {
        return this.sumbangan;
    }

    public void setSumbangan(int sumbangan) {
        this.sumbangan = sumbangan;
    }

    public int getJasaLayanan() {
        return this.jasaLayanan;
    }

    public void setJasaLayanan(int jasaLayanan) {
        this.jasaLayanan = jasaLayanan;
    }

    public int getPenghasilanInvestasiJangkaPanjang() {
        return this.penghasilanInvestasiJangkaPanjang;
    }

    public void setPenghasilanInvestasiJangkaPanjang(int penghasilanInvestasiJangkaPanjang) {
        this.penghasilanInvestasiJangkaPanjang = penghasilanInvestasiJangkaPanjang;
    }

    public int getPenghasilanInvestasiLain() {
        return this.penghasilanInvestasiLain;
    }

    public void setPenghasilanInvestasiLain(int penghasilanInvestasiLain) {
        this.penghasilanInvestasiLain = penghasilanInvestasiLain;
    }

    public int getPenghasilanNetoTerealisasikanDanBelumTerealisasikanDariIJP() {
        return this.penghasilanNetoTerealisasikanDanBelumTerealisasikanDariIJP;
    }

    public void setPenghasilanNetoTerealisasikanDanBelumTerealisasikanDariIJP(int penghasilanNetoTerealisasikanDanBelumTerealisasikanDariIJP) {
        this.penghasilanNetoTerealisasikanDanBelumTerealisasikanDariIJP = penghasilanNetoTerealisasikanDanBelumTerealisasikanDariIJP;
    }

    public int getLainLain() {
        return this.lainLain;
    }

    public void setLainLain(int lainLain) {
        this.lainLain = lainLain;
    }

    public int getPemenuhanProgramPembatasan() {
        return this.pemenuhanProgramPembatasan = pemenuhanProgramPembatasan;
    }

    public void setPemenuhanProgramPembatasan(int pemenuhanProgramPembatasan) {
        this.pemenuhanProgramPembatasan = pemenuhanProgramPembatasan;
    }

    public int getPemenuhanPembatasanPemerolehanPeralatan() {
        return this.pemenuhanPembatasanPemerolehanPeralatan = pemenuhanPembatasanPemerolehanPeralatan;
    }

    public void setPemenuhanPembatasanPemerolehanPeralatan(int pemenuhanPembatasanPemerolehanPeralatan) {
        this.pemenuhanPembatasanPemerolehanPeralatan = pemenuhanPembatasanPemerolehanPeralatan;
    }

    public int getBerakhirnyaPembatasanWaktu() {
        return this.berakhirnyaPembatasanWaktu;
    }

    public void setBerakhirnyaPembatasanWaktu(int berakhirnyaPembatasanWaktu) {
        this.berakhirnyaPembatasanWaktu = berakhirnyaPembatasanWaktu;
    }

    public int getJumlahPendapatanPenghasilanDanSumbangan() {
        return this.sumbangan + this.jasaLayanan + this.penghasilanInvestasiJangkaPanjang + this.penghasilanInvestasiLain + this.penghasilanNetoTerealisasikanDanBelumTerealisasikanDariIJP + this.lainLain;
    }

    public int getBebanDanKerugianProgram() {
        return this.bebanDanKerugianProgram;
    }

    public void setBebanDanKerugianProgram(int bebanDanKerugianProgram) {
        this.bebanDanKerugianProgram = bebanDanKerugianProgram;
    }

    public int getBebanDanKerugianManajemenDanUmum() {
        return this.bebanDanKerugianManajemendanUmum;
    }

    public void setBebanDanKerugianManajemenDanUmum(int bebanDanKerugianManajemendanUmum) {
        this.bebanDanKerugianManajemendanUmum = bebanDanKerugianManajemendanUmum;
    }

    public int getBebanDanKerugianPencarianDana() {
        return this.bebanDanKerugianPencarianDana;
    }

    public void setBebanDanKerugianPencarianDana(int bebanDanKerugianPencarianDana) {
        this.bebanDanKerugianPencarianDana = bebanDanKerugianPencarianDana;
    }

    public int getBebanDanKerugianJumlahBeban() {
        return this.bebanDanKerugianProgram + this.bebanDanKerugianPencarianDana + this.bebanDanKerugianManajemendanUmum;
    }

    public int getKerugianAktuarialDanKewajibanTahunan() {
        return this.kerugianAktuarialDanKewajibanTahunan;
    }

    public void setKerugianAktuarialDanKewajibanTahunan(int kerugianAktuarialDanKewajibanTahunan) {
        this.kerugianAktuarialDanKewajibanTahunan = kerugianAktuarialDanKewajibanTahunan;
    }

    public int getJumlahBebanDanKerugian() {
        return this.getJumlahBebanDanKerugian() + this.kerugianAktuarialDanKewajibanTahunan;
    }

    public int getPerubahanAsetNeto() {
        return this.getJumlahPendapatanPenghasilanDanSumbangan() - this.getJumlahBebanDanKerugian();
    }

    public int getAsetNetoAwalTahun() {
        return this.asetNetoAwalTahun;
    }

    public void setAsetNetoAwalTahun(int asetNetoAwalTahun) {
        this.asetNetoAwalTahun = asetNetoAwalTahun;
    }

    public int getAsetNetoAkhirTahun() {
        return this.getPerubahanAsetNeto() + this.asetNetoAwalTahun;
    }
}
