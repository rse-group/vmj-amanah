package aisco.automaticreport.financialposition;

import java.util.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import aisco.automaticreport.periodic.AutomaticReportPeriodicComponent;

@Entity
@Table(name="automaticreport_financialposition_comp")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class AutomaticReportFinancialPositionComponent implements AutomaticReportFinancialPosition {
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

    public abstract int getKasDanSetaraKas();
    public abstract void setKasDanSetaraKas(int kasDanSetaraKas);

    public abstract int getPersediaanDanBiayaDibayarDiMuka();
    public abstract void setPersediaanDanBiayaDibayarDiMuka(int persediaanDanBiayaDibayarDiMuka);

    public abstract int getPiutangBunga();
    public abstract void setPiutangBunga(int piutangBunga);

    public abstract int getPiutangLainLain();
    public abstract void setPiutangLainLain(int piutangLainLain);

    public abstract int getInvestasiLancar();
    public abstract void setInvestasiLancar(int investasiLancar);

    public abstract int getPropertiInvestasi();
    public abstract void setPropertiInvestasi(int propertiInvestasi);

    public abstract int getAsetTetap();
    public abstract void setAsetTetap(int asetTetap);

    public abstract int getInvestasiJangkaPanjang();
    public abstract void setInvestasiJangkaPanjang(int investasiJangkaPanjang);

    public abstract int getJumlahAset();

    public abstract int getUtangDagang();
    public abstract void setUtangDagang(int utangDagang);

    public abstract int getPendapatanDiterimaDiMukaYangDapatDikembalikan();
    public abstract void setPendapatanDiterimaDiMukaYangDapatDikembalikan(int pendapatanDiterimaDiMukaYangDapatDikembalikan);

    public abstract int getUtangLainLain();
    public abstract void setUtangLainLain(int utangLainLain);

    public abstract int getUtangWesel();
    public abstract void setUtangWesel(int utangWesel);

    public abstract int getKewajibanTahunan();
    public abstract void setKewajibanTahunan(int kewajibanTahunan);

    public abstract int getUtangJangkaPanjang();
    public abstract void setUtangJangkaPanjang(int utangJangkaPanjang);

    public abstract int getJumlahLiabilitas();

    public abstract int getAsetNetoTidakTerikat();
    public abstract void setAsetNetoTidakTerikat(int asetNetoTidakTerikat);

    public abstract int getAsetNetoTerikatTemporer();
    public abstract void setAsetNetoTerikatTemporer(int asetNetoTerikatTemporer);

    public abstract int getAsetNetoTerikatPermanen();
    public abstract void setAsetNetoTerikatPermanen(int asetNetoTerikatPermanen);

    public abstract int getJumlahAsetNeto();

    public HashMap<String, Object> toHashMap() {
        HashMap<String, Object> automaticReportFinancialPositionMap = new HashMap<String, Object>();
        automaticReportFinancialPositionMap.put("id", getId());
        automaticReportFinancialPositionMap.put("periodicId", getPeriodic().getId());
        automaticReportFinancialPositionMap.put("kasDanSetaraKas", getKasDanSetaraKas());
        automaticReportFinancialPositionMap.put("persediaanDanBiayaDibayarDiMuka", getPersediaanDanBiayaDibayarDiMuka());
        automaticReportFinancialPositionMap.put("piutangBunga", getPiutangBunga());
        automaticReportFinancialPositionMap.put("piutangLainLain", getPiutangLainLain());
        automaticReportFinancialPositionMap.put("investasiLancar", getInvestasiLancar());
        automaticReportFinancialPositionMap.put("propertiInvestasi", getPropertiInvestasi());
        automaticReportFinancialPositionMap.put("asetTetap", getAsetTetap());
        automaticReportFinancialPositionMap.put("investasiJangkaPanjang", getInvestasiJangkaPanjang());
        automaticReportFinancialPositionMap.put("jumlahAset", getJumlahAset());
        automaticReportFinancialPositionMap.put("utangDagang", getUtangDagang());
        automaticReportFinancialPositionMap.put("pendapatanDiterimaDiMukaYangDapatDikembalikan", getPendapatanDiterimaDiMukaYangDapatDikembalikan());
        automaticReportFinancialPositionMap.put("utangLainLain", getUtangLainLain());
        automaticReportFinancialPositionMap.put("utangWesel", getUtangWesel());
        automaticReportFinancialPositionMap.put("kewajibanTahunan", getKewajibanTahunan());
        automaticReportFinancialPositionMap.put("utangJangkaPanjang", getUtangJangkaPanjang());
        automaticReportFinancialPositionMap.put("jumlahLiabilitas", getJumlahLiabilitas());
        automaticReportFinancialPositionMap.put("asetNetoTidakTerikat", getAsetNetoTidakTerikat());
        automaticReportFinancialPositionMap.put("asetNetoTerikatTemporer", getAsetNetoTerikatTemporer());
        automaticReportFinancialPositionMap.put("asetNetoTerikatPermanen", getAsetNetoTerikatPermanen());
        automaticReportFinancialPositionMap.put("jumlahAsetNeto", getJumlahAsetNeto());
        return automaticReportFinancialPositionMap;
    }
}