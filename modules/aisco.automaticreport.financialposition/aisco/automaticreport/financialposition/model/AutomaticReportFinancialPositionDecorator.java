package aisco.automaticreport.financialposition;

import java.lang.Math;
import java.util.*;

import aisco.automaticreport.periodic.*;

public abstract class AutomaticReportFinancialPositionDecorator extends AutomaticReportFinancialPositionComponent {

    public AutomaticReportFinancialPositionComponent record;

    public AutomaticReportFinancialPositionDecorator(AutomaticReportFinancialPositionComponent record) {
        this.record = record;
        Random r = new Random();
        this.id = Math.abs(r.nextInt());
    }

    public AutomaticReportFinancialPositionComponent getRecord() {
        return this.record;
    }

    public void setRecord(AutomaticReportFinancialPositionComponent record) {
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
    public int getKasDanSetaraKas() {
        return this.record.getKasDanSetaraKas();
    }

    @Override
    public void setKasDanSetaraKas(int kasDanSetaraKas) {
        this.record.setKasDanSetaraKas(kasDanSetaraKas);
    }

    @Override
    public int getPersediaanDanBiayaDibayarDiMuka() {
        return this.record.getPersediaanDanBiayaDibayarDiMuka();
    }

    @Override
    public void setPersediaanDanBiayaDibayarDiMuka(int persediaanDanBiayaDibayarDiMuka) {
        this.record.setPersediaanDanBiayaDibayarDiMuka(persediaanDanBiayaDibayarDiMuka);
    }

    @Override
    public int getPiutangBunga() {
        return this.record.getPiutangBunga();
    }

    @Override
    public void setPiutangBunga(int piutangBunga) {
        this.record.setPiutangBunga(piutangBunga);
    }

    @Override
    public int getInvestasiLancar() {
        return this.record.getInvestasiLancar();
    }

    @Override
    public void setInvestasiLancar(int investasiLancar) {
        this.record.setInvestasiLancar(investasiLancar);
    }

    @Override
    public int getPropertiInvestasi() {
        return this.record.getPropertiInvestasi();
    }

    @Override
    public void setPropertiInvestasi(int propertiInvestasi) {
        this.record.setPropertiInvestasi(propertiInvestasi);
    }

    @Override
    public int getAsetTetap() {
        return this.record.getAsetTetap();
    }

    @Override
    public void setAsetTetap(int asetTetap) {
        this.record.setAsetTetap(asetTetap);
    }

    @Override
    public int getInvestasiJangkaPanjang() {
        return this.record.getInvestasiJangkaPanjang();
    }

    @Override
    public void setInvestasiJangkaPanjang(int investasiJangkaPanjang) {
        this.record.setInvestasiJangkaPanjang(investasiJangkaPanjang);
    }

    @Override
    public int getJumlahAset() {
        return this.record.getJumlahAset();
    }

    @Override
    public int getUtangDagang() {
        return this.record.getUtangDagang();
    }

    @Override
    public void setUtangDagang(int utangDagang) {
        this.record.setUtangDagang(utangDagang);
    }

    @Override
    public int getPendapatanDiterimaDiMukaYangDapatDikembalikan() {
        return this.record.getPendapatanDiterimaDiMukaYangDapatDikembalikan();
    }

    @Override
    public void setPendapatanDiterimaDiMukaYangDapatDikembalikan(int pendapatanDiterimaDiMukaYangDapatDikembalikan) {
        this.record.setPendapatanDiterimaDiMukaYangDapatDikembalikan(pendapatanDiterimaDiMukaYangDapatDikembalikan);
    }

    @Override
    public int getUtangLainLain() {
        return this.record.getUtangLainLain();
    }
    
    @Override
    public void setUtangLainLain(int utangLainLain) {
        this.record.setUtangLainLain(utangLainLain);
    }

    @Override
    public int getUtangWesel() {
        return this.record.getUtangWesel();
    }
    
    @Override
    public void setUtangWesel(int utangWesel) {
        this.record.setUtangWesel(utangWesel);
    }

    @Override
    public int getKewajibanTahunan() {
        return this.record.getKewajibanTahunan();
    }
    
    @Override
    public void setKewajibanTahunan(int kewajibanTahunan) {
        this.record.setKewajibanTahunan(kewajibanTahunan);
    }

    @Override
    public int getUtangJangkaPanjang() {
        return this.record.getUtangJangkaPanjang();
    }
    
    @Override
    public void setUtangJangkaPanjang(int utangJangkaPanjang) {
        this.record.setUtangJangkaPanjang(utangJangkaPanjang);
    }

    @Override
    public int getJumlahLiabilitas() {
        return this.record.getJumlahLiabilitas();
    }

    @Override
    public int getAsetNetoTidakTerikat() {
        return this.record.getAsetNetoTidakTerikat();
    }
    
    @Override
    public void setAsetNetoTidakTerikat(int asetNetoTidakTerikat) {
        this.record.setAsetNetoTidakTerikat(asetNetoTidakTerikat);
    }

    @Override
    public int getAsetNetoTerikatTemporer() {
        return this.record.getAsetNetoTerikatTemporer();
    }
    
    @Override
    public void setAsetNetoTerikatTemporer(int asetNetoTerikatTemporer) {
        this.record.setAsetNetoTerikatTemporer(asetNetoTerikatTemporer);
    }

    @Override
    public int getAsetNetoTerikatPermanen() {
        return this.record.getAsetNetoTerikatPermanen();
    }
    
    @Override
    public void setAsetNetoTerikatPermanen(int asetNetoTerikatPermanen) {
        this.record.setAsetNetoTerikatPermanen(asetNetoTerikatPermanen);
    }

    @Override
    public int getJumlahAsetNeto() {
        return this.record.getJumlahAsetNeto();
    }
}
