package aisco.automaticreport.activityreport;

import java.util.*;

import aisco.automaticreport.periodic.AutomaticReportPeriodicComponent;

public interface AutomaticReportActivityReport{
    int getId();
    void setId(int id);

    AutomaticReportPeriodicComponent getPeriodic();
    void setPeriodic(AutomaticReportPeriodicComponent periodic);

    String getPembatasan();
    void setPembatasan(String pembatasan);

    int getSumbangan();
    void setSumbangan(int sumbangan);

    int getJasaLayanan();
    void setJasaLayanan(int jasaLayanan);

    int getPenghasilanInvestasiJangkaPanjang();
    void setPenghasilanInvestasiJangkaPanjang(int penghasilanInvestasiJangkaPanjang);

    int getPenghasilanInvestasiLain();
    void setPenghasilanInvestasiLain(int penghasilanInvestasiLain);

    int getPenghasilanNetoTerealisasikanDanBelumTerealisasikanDariIJP();
    void setPenghasilanNetoTerealisasikanDanBelumTerealisasikanDariIJP(int penghasilanNetoTerealisasikanDanBelumTerealisasikanDariIJP);

    int getLainLain();
    void setLainLain(int lainLain);

    int getPemenuhanProgramPembatasan();
    void setPemenuhanProgramPembatasan(int pemenuhanProgramPembatasan);

    int getPemenuhanPembatasanPemerolehanPeralatan();
    void setPemenuhanPembatasanPemerolehanPeralatan(int pemenuhanPembatasanPemerolehanPeralatan);

    int getBerakhirnyaPembatasanWaktu();
    void setBerakhirnyaPembatasanWaktu(int berakhirnyaPembatasanWaktu);

    int getJumlahPendapatanPenghasilanDanSumbangan();

    int getBebanDanKerugianProgram();
    void setBebanDanKerugianProgram(int bebanDanKerugianProgram);

    int getBebanDanKerugianManajemenDanUmum();
    void setBebanDanKerugianManajemenDanUmum(int bebanDanKerugianManajemendanUmum);

    int getBebanDanKerugianPencarianDana();
    void setBebanDanKerugianPencarianDana(int bebanDanKerugianPencarianDana);

    int getBebanDanKerugianJumlahBeban();

    int getKerugianAktuarialDanKewajibanTahunan();
    void setKerugianAktuarialDanKewajibanTahunan(int kerugianAktuarialDanKewajibanTahunan);

    int getJumlahBebanDanKerugian();

    int getPerubahanAsetNeto();
    
    int getAsetNetoAwalTahun();
    void setAsetNetoAwalTahun(int asetNetoAwalTahun);

    int getAsetNetoAkhirTahun();

    HashMap<String, Object> toHashMap();
}
