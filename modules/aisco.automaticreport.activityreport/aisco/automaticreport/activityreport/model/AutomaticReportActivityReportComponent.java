package aisco.automaticreport.activityreport;

import java.util.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import aisco.automaticreport.periodic.AutomaticReportPeriodicComponent;

@Entity
@Table(name="automaticreport_activityreport_comp")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class AutomaticReportActivityReportComponent implements AutomaticReportActivityReport {
    @Id
    public int id;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public abstract AutomaticReportPeriodicComponent getPeriodic();
    public abstract void setPeriodic(AutomaticReportPeriodicComponent periodic);

    public abstract String getPembatasan();
    public abstract void setPembatasan(String pembatasan);

    public abstract int getSumbangan();
    public abstract void setSumbangan(int sumbangan);

    public abstract int getJasaLayanan();
    public abstract void setJasaLayanan(int jasaLayanan);

    public abstract int getPenghasilanInvestasiJangkaPanjang();
    public abstract void setPenghasilanInvestasiJangkaPanjang(int penghasilanInvestasiJangkaPanjang);

    public abstract int getPenghasilanInvestasiLain();
    public abstract void setPenghasilanInvestasiLain(int penghasilanInvestasiLain);

    public abstract int getPenghasilanNetoTerealisasikanDanBelumTerealisasikanDariIJP();
    public abstract void setPenghasilanNetoTerealisasikanDanBelumTerealisasikanDariIJP(int penghasilanNetoTerealisasikanDanBelumTerealisasikanDariIJP);

    public abstract int getLainLain();
    public abstract void setLainLain(int lainLain);

    public abstract int getPemenuhanProgramPembatasan();
    public abstract void setPemenuhanProgramPembatasan(int pemenuhanProgramPembatasan);

    public abstract int getPemenuhanPembatasanPemerolehanPeralatan();
    public abstract void setPemenuhanPembatasanPemerolehanPeralatan(int pemenuhanPembatasanPemerolehanPeralatan);
    
    public abstract int getBerakhirnyaPembatasanWaktu();
    public abstract void setBerakhirnyaPembatasanWaktu(int berakhirnyaPembatasanWaktu);

    public abstract int getJumlahPendapatanPenghasilanDanSumbangan();

    public abstract int getBebanDanKerugianProgram();
    public abstract void setBebanDanKerugianProgram(int bebanDanKerugianProgram);

    public abstract int getBebanDanKerugianManajemenDanUmum();
    public abstract void setBebanDanKerugianManajemenDanUmum(int bebanDanKerugianManajemendanUmum);

    public abstract int getBebanDanKerugianPencarianDana();
    public abstract void setBebanDanKerugianPencarianDana(int bebanDanKerugianPencarianDana);

    public abstract int getBebanDanKerugianJumlahBeban();

    public abstract int getKerugianAktuarialDanKewajibanTahunan();
    public abstract void setKerugianAktuarialDanKewajibanTahunan(int kerugianAktuarialDanKewajibanTahunan);

    public abstract int getJumlahBebanDanKerugian();

    public abstract int getPerubahanAsetNeto();
    
    public abstract int getAsetNetoAwalTahun();
    public abstract void setAsetNetoAwalTahun(int asetNetoAwalTahun);

    public abstract int getAsetNetoAkhirTahun();

    public HashMap<String, Object> toHashMap() {
        HashMap<String, Object> AutomaticReportActivityReportMap = new HashMap<String, Object>();
        AutomaticReportActivityReportMap.put("id",getId());
        AutomaticReportActivityReportMap.put("periodicId", getPeriodic().getId());
        AutomaticReportActivityReportMap.put("sumbangan",getSumbangan());
        AutomaticReportActivityReportMap.put("jasaLayanan",getJasaLayanan());
        AutomaticReportActivityReportMap.put("penghasilanInvestasiJangkaPanjang",getPenghasilanInvestasiJangkaPanjang());
        AutomaticReportActivityReportMap.put("penghasilanInvestasiLain",getPenghasilanInvestasiLain());
        AutomaticReportActivityReportMap.put("penghasilanNetoTerealisasikanDanBelumTerealisasikanDariIJP",getPenghasilanNetoTerealisasikanDanBelumTerealisasikanDariIJP());
        AutomaticReportActivityReportMap.put("lainLain",getLainLain());
        AutomaticReportActivityReportMap.put("pemenuhanProgramPembatasan",getPemenuhanProgramPembatasan());
        AutomaticReportActivityReportMap.put("pemenuhanPembatasanPerolehanPeralatan",getPemenuhanPembatasanPemerolehanPeralatan());
        AutomaticReportActivityReportMap.put("berakhirnyaPembatasanWaktu",getBerakhirnyaPembatasanWaktu());
        AutomaticReportActivityReportMap.put("jumlahPendapatanPenghasilanDanSumbangan",getJumlahPendapatanPenghasilanDanSumbangan());
        AutomaticReportActivityReportMap.put("bebanDanKerugianProgram",getBebanDanKerugianProgram());
        AutomaticReportActivityReportMap.put("bebanDanKerugianManajemenDanUmum",getBebanDanKerugianManajemenDanUmum());
        AutomaticReportActivityReportMap.put("bebanDanKerugianPencarianDana",getBebanDanKerugianPencarianDana());
        AutomaticReportActivityReportMap.put("bebanDanKerugianJumlahBeban",getBebanDanKerugianJumlahBeban());
        AutomaticReportActivityReportMap.put("kerugianAktuarialDanKewajibanTahunan",getKerugianAktuarialDanKewajibanTahunan());
        AutomaticReportActivityReportMap.put("jumlahBebanDanKerugian",getJumlahBebanDanKerugian());
        AutomaticReportActivityReportMap.put("perubahanAsetNeto",getPerubahanAsetNeto());
        AutomaticReportActivityReportMap.put("asetNetoAwalTahun",getAsetNetoAwalTahun());
        AutomaticReportActivityReportMap.put("asetNetoAkhirTahun",getAsetNetoAkhirTahun());

        return AutomaticReportActivityReportMap;
    }
}
