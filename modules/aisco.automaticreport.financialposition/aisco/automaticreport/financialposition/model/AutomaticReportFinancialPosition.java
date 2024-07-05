package aisco.automaticreport.financialposition;

import java.util.*;

import aisco.automaticreport.periodic.AutomaticReportPeriodicComponent;

public interface AutomaticReportFinancialPosition {
    int getId();
    void setId(int id);

    AutomaticReportPeriodicComponent getPeriodic();
    void setPeriodic(AutomaticReportPeriodicComponent periodic);

    int getKasDanSetaraKas();
    void setKasDanSetaraKas(int kasDanSetaraKas);

    int getPersediaanDanBiayaDibayarDiMuka();
    void setPersediaanDanBiayaDibayarDiMuka(int persediaanDanBiayaDibayarDiMuka);

    int getPiutangBunga();
    void setPiutangBunga(int piutangBunga);

    int getPiutangLainLain();
    void setPiutangLainLain(int piutangLainLain);

    int getInvestasiLancar();
    void setInvestasiLancar(int investasiLancar);

    int getPropertiInvestasi();
    void setPropertiInvestasi(int propertiInvestasi);

    int getAsetTetap();
    void setAsetTetap(int asetTetap);

    int getInvestasiJangkaPanjang();
    void setInvestasiJangkaPanjang(int investasiJangkaPanjang);

    int getJumlahAset();

    int getUtangDagang();
    void setUtangDagang(int utangDagang);

    int getPendapatanDiterimaDiMukaYangDapatDikembalikan();
    void setPendapatanDiterimaDiMukaYangDapatDikembalikan(int pendapatanDiterimaDiMukaYangDapatDikembalikan);

    int getUtangLainLain();
    void setUtangLainLain(int utangLainLain);

    int getUtangWesel();
    void setUtangWesel(int utangWesel);

    int getKewajibanTahunan();
    void setKewajibanTahunan(int kewajibanTahunan);

    int getUtangJangkaPanjang();
    void setUtangJangkaPanjang(int utangJangkaPanjang);

    int getJumlahLiabilitas();

    int getAsetNetoTidakTerikat();
    void setAsetNetoTidakTerikat(int asetNetoTidakTerikat);

    int getAsetNetoTerikatTemporer();
    void setAsetNetoTerikatTemporer(int asetNetoTerikatTemporer);

    int getAsetNetoTerikatPermanen();
    void setAsetNetoTerikatPermanen(int asetNetoTerikatPermanen);

    int getJumlahAsetNeto();

    HashMap<String, Object> toHashMap();
}
