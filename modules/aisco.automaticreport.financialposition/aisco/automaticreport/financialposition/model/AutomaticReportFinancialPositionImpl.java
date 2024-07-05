package aisco.automaticreport.financialposition;

import java.util.*;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.OneToOne;

import aisco.automaticreport.periodic.*;

@Entity(name = "automaticreport_financialposition_impl")
@Table(name = "automaticreport_financialposition_impl")
public class AutomaticReportFinancialPositionImpl extends AutomaticReportFinancialPositionComponent {
    protected int kasDanSetaraKas;
    protected int persediaanDanBiayaDibayarDiMuka;
    protected int piutangBunga;
    protected int piutangLainLain;
    protected int investasiLancar;
    protected int propertiInvestasi;
    protected int asetTetap;
    protected int investasiJangkaPanjang;
    protected int utangDagang;
    protected int pendapatanDiterimaDiMukaYangDapatDikembalikan;
    protected int utangLainLain;
    protected int utangWesel;
    protected int kewajibanTahunan;
    protected int utangJangkaPanjang;
    protected int asetNetoTidakTerikat;
    protected int asetNetoTerikatTemporer;
    protected int asetNetoTerikatPermanen;

    @OneToOne
    public AutomaticReportPeriodicComponent periodic;

    public AutomaticReportFinancialPositionImpl(){}

    public AutomaticReportFinancialPositionImpl(AutomaticReportPeriodicComponent periodic) {
        Random r = new Random();
        this.id = Math.abs(r.nextInt());
        this.kasDanSetaraKas = 0;
        this.persediaanDanBiayaDibayarDiMuka = 0;
        this.piutangBunga = 0;
        this.piutangLainLain = 0;
        this.investasiLancar = 0;
        this.propertiInvestasi = 0;
        this.asetTetap = 0;
        this.investasiJangkaPanjang = 0;
        this.utangDagang = 0;
        this.pendapatanDiterimaDiMukaYangDapatDikembalikan = 0;
        this.utangLainLain = 0;
        this.utangWesel = 0;
        this.kewajibanTahunan = 0;
        this.utangJangkaPanjang = 0;
        this.asetNetoTidakTerikat = 0;
        this.asetNetoTerikatTemporer = 0;
        this.asetNetoTerikatPermanen = 0;
        this.periodic = periodic;
    }

    public AutomaticReportFinancialPositionImpl(int kasDanSetaraKas, int persediaanDanBiayaDibayarDiMuka,
            int piutangBunga, int piutangLainLain, int investasiLancar, int propertiInvestasi, int asetTetap,
            int investasiJangkaPanjang, int utangDagang, int pendapatanDiterimaDiMukaYangDapatDikembalikan,
            int utangLainLain, int utangWesel, int kewajibanTahunan, int utangJangkaPanjang, int asetNetoTidakTerikat,
            int asetNetoTerikatTemporer, int asetNetoTerikatPermanen, AutomaticReportPeriodicComponent periodic) {

        Random r = new Random();
        this.id = Math.abs(r.nextInt());
        this.kasDanSetaraKas = kasDanSetaraKas;
        this.persediaanDanBiayaDibayarDiMuka = persediaanDanBiayaDibayarDiMuka;
        this.piutangBunga = piutangBunga;
        this.piutangLainLain = piutangLainLain;
        this.investasiLancar = investasiLancar;
        this.propertiInvestasi = propertiInvestasi;
        this.asetTetap = asetTetap;
        this.investasiJangkaPanjang = investasiJangkaPanjang;
        this.utangDagang = utangDagang;
        this.pendapatanDiterimaDiMukaYangDapatDikembalikan = pendapatanDiterimaDiMukaYangDapatDikembalikan;
        this.utangLainLain = utangLainLain;
        this.utangWesel = utangWesel;
        this.kewajibanTahunan = kewajibanTahunan;
        this.utangJangkaPanjang = utangJangkaPanjang;
        this.asetNetoTidakTerikat = asetNetoTidakTerikat;
        this.asetNetoTerikatTemporer = asetNetoTerikatTemporer;
        this.asetNetoTerikatPermanen = asetNetoTerikatPermanen;
        this.periodic = periodic;
    }
    
    public AutomaticReportPeriodicComponent getPeriodic() {
        return this.periodic;
    }

    public void setPeriodic(AutomaticReportPeriodicComponent periodic) {
        this.periodic = periodic;
    }

    public int getKasDanSetaraKas() {
        return this.kasDanSetaraKas;
    }

    public void setKasDanSetaraKas(int kasDanSetaraKas) {
        this.kasDanSetaraKas = kasDanSetaraKas;
    }

    public int getPersediaanDanBiayaDibayarDiMuka() {
        return this.persediaanDanBiayaDibayarDiMuka;
    }

    public void setPersediaanDanBiayaDibayarDiMuka(int persediaanDanBiayaDibayarDiMuka) {
        this.persediaanDanBiayaDibayarDiMuka = persediaanDanBiayaDibayarDiMuka;
    }

    public int getPiutangBunga() {
        return this.piutangBunga;
    }

    public void setPiutangBunga(int piutangBunga) {
        this.piutangBunga = piutangBunga;
    }

    public int getPiutangLainLain() {
        return this.piutangLainLain;
    }

    public void setPiutangLainLain(int piutangLainLain) {
        this.piutangLainLain = piutangLainLain;
    }

    public int getInvestasiLancar() {
        return this.investasiLancar;
    }

    public void setInvestasiLancar(int investasiLancar) {
        this.investasiLancar = investasiLancar;
    }

    public int getPropertiInvestasi() {
        return this.propertiInvestasi;
    }

    public void setPropertiInvestasi(int propertiInvestasi) {
        this.propertiInvestasi = propertiInvestasi;
    }

    public int getAsetTetap() {
        return this.asetTetap;
    }

    public void setAsetTetap(int asetTetap) {
        this.asetTetap = asetTetap;
    }

    public int getInvestasiJangkaPanjang() {
        return this.investasiJangkaPanjang;
    }

    public void setInvestasiJangkaPanjang(int investasiJangkaPanjang) {
        this.investasiJangkaPanjang = investasiJangkaPanjang;
    }

    public int getJumlahAset() {
        int jumlah = this.kasDanSetaraKas + this.persediaanDanBiayaDibayarDiMuka + this.piutangBunga
                + this.piutangLainLain + this.investasiLancar + this.propertiInvestasi + this.asetTetap
                + investasiJangkaPanjang;
        return jumlah;
    }

    public int getUtangDagang() {
        return this.utangDagang;
    }

    public void setUtangDagang(int utangDagang) {
        this.utangDagang = utangDagang;
    }

    public int getPendapatanDiterimaDiMukaYangDapatDikembalikan() {
        return this.pendapatanDiterimaDiMukaYangDapatDikembalikan;
    }

    public void setPendapatanDiterimaDiMukaYangDapatDikembalikan(int pendapatanDiterimaDiMukaYangDapatDikembalikan) {
        this.pendapatanDiterimaDiMukaYangDapatDikembalikan = pendapatanDiterimaDiMukaYangDapatDikembalikan;
    }

    public int getUtangLainLain() {
        return this.utangLainLain;
    }

    public void setUtangLainLain(int utangLainLain) {
        this.utangLainLain = utangLainLain;
    }

    public int getUtangWesel() {
        return this.utangWesel;
    }

    public void setUtangWesel(int utangWesel) {
        this.utangWesel = utangWesel;
    }

    public int getKewajibanTahunan() {
        return this.kewajibanTahunan;
    }

    public void setKewajibanTahunan(int kewajibanTahunan) {
        this.kewajibanTahunan = kewajibanTahunan;
    }

    public int getUtangJangkaPanjang() {
        return this.utangJangkaPanjang;
    }

    public void setUtangJangkaPanjang(int utangJangkaPanjang) {
        this.utangJangkaPanjang = utangJangkaPanjang;
    }

    public int getJumlahLiabilitas() {
        int jumlah = this.utangDagang + this.pendapatanDiterimaDiMukaYangDapatDikembalikan + this.utangLainLain
                + this.utangWesel + this.kewajibanTahunan + this.utangJangkaPanjang;
        return jumlah;
    }

    public int getAsetNetoTidakTerikat() {
        return this.asetNetoTidakTerikat;
    }

    public void setAsetNetoTidakTerikat(int asetNetoTidakTerikat) {
        this.asetNetoTidakTerikat = asetNetoTidakTerikat;
    }

    public int getAsetNetoTerikatTemporer() {
        return this.asetNetoTerikatTemporer;
    }

    public void setAsetNetoTerikatTemporer(int asetNetoTerikatTemporer) {
        this.asetNetoTerikatTemporer = asetNetoTerikatTemporer;
    }

    public int getAsetNetoTerikatPermanen() {
        return this.asetNetoTerikatPermanen;
    }

    public void setAsetNetoTerikatPermanen(int asetNetoTerikatPermanen) {
        this.asetNetoTerikatPermanen = asetNetoTerikatPermanen;
    }

    public int getJumlahAsetNeto() {
        int jumlah = this.asetNetoTidakTerikat + this.asetNetoTerikatTemporer + this.asetNetoTerikatPermanen;
        return jumlah;
    }
}