package aisco.automaticreport.activityreport;

import java.lang.Math;
import java.util.*;

import aisco.automaticreport.periodic.*;

public abstract class AutomaticReportActivityReportDecorator extends AutomaticReportActivityReportComponent {
    
    public AutomaticReportActivityReportComponent record;

    public AutomaticReportActivityReportDecorator(AutomaticReportActivityReportComponent record) {
        this.record = record;
        Random r = new Random();
        this.id = Math.abs(r.nextInt());
    }

    public AutomaticReportActivityReportComponent getRecord() {
        return this.record;
    }

    public void setRecord(AutomaticReportActivityReportComponent record) {
        this.record = record;
    }

    @Override
    public AutomaticReportPeriodicComponent getPeriodic() {
        return this.record.getPeriodic();
    }

    @Override
    public void setPeriodic(AutomaticReportPeriodicComponent periodic) {
        this.record.setPeriodic(periodic);
    }

    @Override
    public String getPembatasan() {
        return this.record.getPembatasan();
    }

    @Override
    public void setPembatasan(String pembatasan) {
        this.record.setPembatasan(pembatasan);
    }

    @Override
    public int getSumbangan() {
        return this.record.getSumbangan();
    }

    @Override
    public void setSumbangan(int sumbangan) {
        this.record.setSumbangan(sumbangan);
    }

    @Override
    public int getJasaLayanan() {
        return this.record.getJasaLayanan();
    }

    @Override
    public void setJasaLayanan(int jasaLayanan) {
        this.record.setJasaLayanan(jasaLayanan);
    }

    @Override
    public int getPenghasilanInvestasiJangkaPanjang() {
        return this.record.getPenghasilanInvestasiJangkaPanjang();
    }

    @Override
    public void setPenghasilanInvestasiJangkaPanjang(int penghasilanInvestasiJangkaPanjang) {
        this.record.setPenghasilanInvestasiJangkaPanjang(penghasilanInvestasiJangkaPanjang);
    }

    @Override
    public int getPenghasilanInvestasiLain() {
        return this.record.getPenghasilanInvestasiLain();
    }
    
    @Override
    public void setPenghasilanInvestasiLain(int penghasilanInvestasiLain) {
        this.record.setPenghasilanInvestasiLain(penghasilanInvestasiLain);
    }

    @Override
    public int getPenghasilanNetoTerealisasikanDanBelumTerealisasikanDariIJP() {
        return this.record.getPenghasilanNetoTerealisasikanDanBelumTerealisasikanDariIJP();
    }

    @Override
    public void setPenghasilanNetoTerealisasikanDanBelumTerealisasikanDariIJP(int penghasilanNetoTerealisasikanDanBelumTerealisasikanDariIJP) {
        this.record.setPenghasilanNetoTerealisasikanDanBelumTerealisasikanDariIJP(penghasilanNetoTerealisasikanDanBelumTerealisasikanDariIJP);
    }

    @Override
    public int getLainLain() {
        return this.record.getLainLain();
    }

    @Override
    public void setLainLain(int lainLain) {
        this.record.setLainLain(lainLain);
    }

    @Override
    public int getPemenuhanProgramPembatasan() {
        return this.record.getPemenuhanProgramPembatasan();
    }

    @Override
    public void setPemenuhanProgramPembatasan(int pemenuhanProgramPembatasan) {
        this.record.setPemenuhanProgramPembatasan(pemenuhanProgramPembatasan);
    }

    @Override
    public int getPemenuhanPembatasanPemerolehanPeralatan() {
        return this.record.getPemenuhanPembatasanPemerolehanPeralatan();
    }

    @Override
    public void setPemenuhanPembatasanPemerolehanPeralatan(int pemenuhanPembatasanPemerolehanPeralatan) {
        this.record.setPemenuhanPembatasanPemerolehanPeralatan(pemenuhanPembatasanPemerolehanPeralatan);
    }

    @Override
    public int getBerakhirnyaPembatasanWaktu() {
        return this.record.getBerakhirnyaPembatasanWaktu();
    }

    @Override
    public void setBerakhirnyaPembatasanWaktu(int berakhirnyaPembatasanWaktu) {
        this.record.setBerakhirnyaPembatasanWaktu(berakhirnyaPembatasanWaktu);
    }

    @Override
    public int getJumlahPendapatanPenghasilanDanSumbangan() {
        return this.record.getJumlahPendapatanPenghasilanDanSumbangan();
    }

    @Override
    public int getBebanDanKerugianProgram() {
        return this.record.getBebanDanKerugianProgram();
    }

    @Override
    public void setBebanDanKerugianProgram(int bebanDanKerugianProgram) {
        this.record.setBebanDanKerugianProgram(bebanDanKerugianProgram);
    }

    @Override
    public int getBebanDanKerugianManajemenDanUmum() {
        return this.record.getBebanDanKerugianManajemenDanUmum();
    }

    @Override
    public void setBebanDanKerugianManajemenDanUmum(int bebanDanKerugianManajemendanUmum) {
        this.record.setBebanDanKerugianManajemenDanUmum(bebanDanKerugianManajemendanUmum);
    }

    @Override
    public int getBebanDanKerugianPencarianDana() {
        return this.record.getBebanDanKerugianPencarianDana();
    }

    @Override
    public void setBebanDanKerugianPencarianDana(int bebanDanKerugianPencarianDana) {
        this.record.setBebanDanKerugianPencarianDana(bebanDanKerugianPencarianDana);
    }

    @Override
    public int getBebanDanKerugianJumlahBeban() {
        return this.record.getBebanDanKerugianJumlahBeban();
    }

    @Override
    public int getKerugianAktuarialDanKewajibanTahunan() {
        return this.record.getKerugianAktuarialDanKewajibanTahunan();
    }

    @Override
    public void setKerugianAktuarialDanKewajibanTahunan(int kerugianAktuarialDanKewajibanTahunan) {
        this.record.setKerugianAktuarialDanKewajibanTahunan(kerugianAktuarialDanKewajibanTahunan);
    }

    @Override
    public int getJumlahBebanDanKerugian() {
        return this.record.getJumlahBebanDanKerugian();
    }

    @Override
    public int getPerubahanAsetNeto() {
        return this.record.getPerubahanAsetNeto();
    }
    
    @Override
    public int getAsetNetoAwalTahun() {
        return this.getAsetNetoAwalTahun();
    }

    @Override
    public void setAsetNetoAwalTahun(int asetNetoAwalTahun) {
        this.record.setAsetNetoAwalTahun(asetNetoAwalTahun);
    }

    @Override
    public int getAsetNetoAkhirTahun() {
        return this.record.getAsetNetoAkhirTahun();
    }
}
