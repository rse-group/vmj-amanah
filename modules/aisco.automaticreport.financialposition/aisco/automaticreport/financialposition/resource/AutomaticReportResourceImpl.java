package aisco.automaticreport.financialposition;

import java.util.*;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;

import vmj.hibernate.integrator.RepositoryUtil;
import vmj.routing.route.Route;
import vmj.routing.route.VMJExchange;
import vmj.routing.route.exceptions.*;

import aisco.automaticreport.core.AutomaticReportResourceDecorator;
import aisco.automaticreport.core.AutomaticReportResourceComponent;
import aisco.automaticreport.periodic.*;
import aisco.financialreport.core.FinancialReport;

public class AutomaticReportResourceImpl extends AutomaticReportResourceDecorator {
    protected RepositoryUtil<AutomaticReportFinancialPosition> automaticReportFinancialPositionRepository;
    protected RepositoryUtil<AutomaticReportPeriodic> automaticReportPeriodicRepository;

    public AutomaticReportResourceImpl(AutomaticReportResourceComponent record) {
        super(record);
        this.automaticReportFinancialPositionRepository = new RepositoryUtil<AutomaticReportFinancialPosition>(
            aisco.automaticreport.financialposition.AutomaticReportFinancialPositionComponent.class);
        this.automaticReportPeriodicRepository = new RepositoryUtil<AutomaticReportPeriodic>(
            aisco.automaticreport.periodic.AutomaticReportPeriodicComponent.class);
    }

    @Route(url = "call/automatic-report-financialposition/save")
    public HashMap<String, Object> saveAutomaticReportFinancialPosition(VMJExchange vmjExchange) {
        AutomaticReportFinancialPosition automaticReportFinancialPosition = checkFinancialPositionReport();
        
        return automaticReportFinancialPosition.toHashMap();
    }

    private AutomaticReportFinancialPosition checkFinancialPositionReport() {
        AutomaticReportPeriodic periodic = getActivePeriodic();
        if (periodic == null) throw new InternalServerException("Periode aktif tidak ditemukan", 5002);

        List<AutomaticReportFinancialPosition> financialPositions = automaticReportFinancialPositionRepository.getAllObject("automaticreport_financialposition_impl");

        for (AutomaticReportFinancialPosition report : financialPositions) {
            if (report.getPeriodic().getName().equals(periodic.getName())) {
                return report;
            }
        }

        AutomaticReportFinancialPosition automaticReportFinancialPosition = createFinancialPositionReport(periodic);
        automaticReportFinancialPositionRepository.saveObject(automaticReportFinancialPosition);
        return automaticReportFinancialPosition;
    }
    
    private AutomaticReportPeriodic getActivePeriodic() {
        List<AutomaticReportPeriodic> automaticReportPeriodicList = automaticReportPeriodicRepository.getAllObject("automaticreport_periodic_impl");
        for (AutomaticReportPeriodic period : automaticReportPeriodicList) {
            if (period.getIsActive() == true) {
                return period;
            }
        } 
        return null;
    }

    private AutomaticReportFinancialPosition createFinancialPositionReport(AutomaticReportPeriodic periodic) {
        List<FinancialReport> financialReports = financialReportRepository.getAllObject("financialreport_impl");
        
        int kasDanSetaraKas = 0;
        int persediaanDanBiayaDibayarDiMuka = 0;
        int piutangBunga = 0;
        int piutangLainLain = 0;
        int investasiLancar = 0;
        int propertiInvestasi = 0;
        int asetTetap = 0;
        int investasiJangkaPanjang = 0;
        int utangDagang = 0;
        int pendapatanDiterimaDiMukaYangDapatDikembalikan = 0;
        int utangLainLain = 0;
        int utangWesel = 0;
        int kewajibanTahunan = 0;
        int utangJangkaPanjang = 0;
        int asetNetoTidakTerikat = 0;
        int asetNetoTerikatTemporer = 0;
        int asetNetoTerikatPermanen = 0;

        // Saran, masukan code coa ke property files karena code coa bersifat dinamis dan spesifik antara organisasi. 
        // Oleh karena itu akan lebih baik jika user/developer tidak perlu mengubah generated code nantinya dan hanya mengubah data pada property files
        LocalDate periodicDate = LocalDate.parse(periodic.getName() + "-01-01");
        for (FinancialReport report : financialReports) {           
            LocalDate date = LocalDate.parse(report.getDatestamp());
            if (date.compareTo(periodicDate) >= 0) continue;
            
            String coa = Integer.toString(report.getCoa().getCode());

            if (coa.startsWith("111") || coa.startsWith("122")) {
                kasDanSetaraKas += report.getAmount();
                asetNetoTerikatPermanen += report.getAmount();
            } else if (coa.startsWith("1")) {
                kasDanSetaraKas += report.getAmount();
                asetNetoTidakTerikat += report.getAmount();
            } else if (coa.startsWith("4")) {
                kasDanSetaraKas += report.getAmount();
                asetNetoTidakTerikat += report.getAmount();
            } else if (coa.startsWith("65")) {
                utangDagang += report.getAmount();
                asetNetoTerikatPermanen -= report.getAmount();
            } else if (coa.startsWith("66")) {
                kewajibanTahunan += report.getAmount();
                asetNetoTidakTerikat -= report.getAmount();
            } else if (coa.startsWith("67") || coa.startsWith("68") || coa.startsWith("69")) {
                utangLainLain += report.getAmount();
                asetNetoTidakTerikat -= report.getAmount();
            } else if (coa.startsWith("6")) {
                utangDagang += report.getAmount();
                asetNetoTidakTerikat -= report.getAmount();
            }
        }

        AutomaticReportFinancialPosition automaticReportFinancialPosition = AutomaticReportFinancialPositionFactory
                .createAutomaticReportFinancialPosition(
                        "aisco.automaticreport.financialposition.AutomaticReportFinancialPositionImpl",
                        kasDanSetaraKas, persediaanDanBiayaDibayarDiMuka, piutangBunga, piutangLainLain,
                        investasiLancar, propertiInvestasi, asetTetap, investasiJangkaPanjang, utangDagang,
                        pendapatanDiterimaDiMukaYangDapatDikembalikan, utangLainLain, utangWesel, kewajibanTahunan,
                        utangJangkaPanjang, asetNetoTidakTerikat, asetNetoTerikatTemporer, asetNetoTerikatPermanen, periodic);

        return automaticReportFinancialPosition;
    }

    @Route(url = "call/automatic-report-financialposition/current")
    public List<HashMap<String, Object>> detailAutomaticReportPeriodic(VMJExchange vmjExchange) {
        AutomaticReportFinancialPosition financialPositionLastYear = checkFinancialPositionReport();

        AutomaticReportPeriodic periodic = financialPositionLastYear.getPeriodic();
        String lastYear = Integer.toString(Integer.valueOf(periodic.getName()) - 1);
        String twoYearsBefore = Integer.toString(Integer.valueOf(periodic.getName()) - 2);
        HashMap<String, Object> dataTahun = getTahun(Integer.valueOf(lastYear));
        
        AutomaticReportFinancialPosition financialPositionTwoYearsBefore = new AutomaticReportFinancialPositionImpl(new AutomaticReportPeriodicImpl(twoYearsBefore));

        List<AutomaticReportFinancialPosition> financialPositions = automaticReportFinancialPositionRepository.getAllObject("automaticreport_financialposition_impl");
        
        for (AutomaticReportFinancialPosition report : financialPositions) {
            if (report.getPeriodic().getName().equals(twoYearsBefore)) {
                financialPositionTwoYearsBefore = report;
                break;
            }
        }

        List<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
        // data.add(dataTahun);
        data.addAll(getAset(financialPositionLastYear, financialPositionTwoYearsBefore));
        data.addAll(getLiabilitas(financialPositionLastYear, financialPositionTwoYearsBefore));
        data.addAll(getAsetNeto(financialPositionLastYear, financialPositionTwoYearsBefore));
        data.add(getJumlahLiabilitasDanAsetNeto(financialPositionLastYear, financialPositionTwoYearsBefore));
        return data;
    }

    public HashMap<String, Object> getTahun(int tahun) {
        String lastYear = Integer.toString(tahun);
        String twoYearsBefore = Integer.toString(tahun - 1);

        HashMap<String, Object> dataTahun = new HashMap<String, Object>();
        dataTahun.put("name", "Tahun");
        dataTahun.put("amountLastYear", lastYear);
        dataTahun.put("amountTwoYearsBefore", twoYearsBefore);
        dataTahun.put("level", 0);
        
        return dataTahun;
    }

    public List<HashMap<String, Object>> getAset(AutomaticReportFinancialPosition financialPositionLastYear, AutomaticReportFinancialPosition financialPositionTwoYearsBefore) {
        List<HashMap<String, Object>> dataAset = new ArrayList<HashMap<String, Object>>();

        HashMap<String, Object> header = new HashMap<String, Object>();
        header.put("name", "Aset");
        header.put("level", 0);
        
        HashMap<String, Object> kasDanSetaraKas = new HashMap<String, Object>();
        kasDanSetaraKas.put("name", "Kas dan Setara Kas");
        kasDanSetaraKas.put("amountLastYear", financialPositionLastYear.getKasDanSetaraKas());
        kasDanSetaraKas.put("amountTwoYearsBefore", financialPositionTwoYearsBefore.getKasDanSetaraKas());
        kasDanSetaraKas.put("level", 1);

        HashMap<String, Object> piutangBunga = new HashMap<String, Object>();
        piutangBunga.put("name", "Piutang Bunga");
        piutangBunga.put("amountLastYear", financialPositionLastYear.getPiutangBunga());
        piutangBunga.put("amountTwoYearsBefore", financialPositionTwoYearsBefore.getPiutangBunga());
        piutangBunga.put("level", 1);

        HashMap<String, Object> persediaanDanBiayaDibayarDiMuka = new HashMap<String, Object>();
        persediaanDanBiayaDibayarDiMuka.put("name", "Persediaan dan Biaya Dibayar Di Muka");
        persediaanDanBiayaDibayarDiMuka.put("amountLastYear", financialPositionLastYear.getPersediaanDanBiayaDibayarDiMuka());
        persediaanDanBiayaDibayarDiMuka.put("amountTwoYearsBefore", financialPositionTwoYearsBefore.getPersediaanDanBiayaDibayarDiMuka());
        persediaanDanBiayaDibayarDiMuka.put("level", 1);

        HashMap<String, Object> piutangLainLain = new HashMap<String, Object>();
        piutangLainLain.put("name", "Piutang Lain-Lain");
        piutangLainLain.put("amountLastYear", financialPositionLastYear.getPiutangLainLain());
        piutangLainLain.put("amountTwoYearsBefore", financialPositionTwoYearsBefore.getPiutangLainLain());
        piutangLainLain.put("level", 1);

        HashMap<String, Object> investasiLancar = new HashMap<String, Object>();
        investasiLancar.put("name", "Investasi Lancar");
        investasiLancar.put("amountLastYear", financialPositionLastYear.getInvestasiLancar());
        investasiLancar.put("amountTwoYearsBefore", financialPositionTwoYearsBefore.getInvestasiLancar());
        investasiLancar.put("level", 1);

        HashMap<String, Object> propertiInvestasi = new HashMap<String, Object>();
        propertiInvestasi.put("name", "Properti Investasi");
        propertiInvestasi.put("amountLastYear", financialPositionLastYear.getPropertiInvestasi());
        propertiInvestasi.put("amountTwoYearsBefore", financialPositionTwoYearsBefore.getPropertiInvestasi());
        propertiInvestasi.put("level", 1);

        HashMap<String, Object> asetTetap = new HashMap<String, Object>();
        asetTetap.put("name", "Aset Tetap");
        asetTetap.put("amountLastYear", financialPositionLastYear.getAsetTetap());
        asetTetap.put("amountTwoYearsBefore", financialPositionTwoYearsBefore.getAsetTetap());
        asetTetap.put("level", 1);

        HashMap<String, Object> investasiJangkaPanjang = new HashMap<String, Object>();
        investasiJangkaPanjang.put("name", "Investasi Jangka Panjang");
        investasiJangkaPanjang.put("amountLastYear", financialPositionLastYear.getInvestasiJangkaPanjang());
        investasiJangkaPanjang.put("amountTwoYearsBefore", financialPositionTwoYearsBefore.getInvestasiJangkaPanjang());
        investasiJangkaPanjang.put("level", 1);

        HashMap<String, Object> jumlahAset = new HashMap<String, Object>();
        jumlahAset.put("name", "Jumlah Aset");
        jumlahAset.put("amountLastYear", financialPositionLastYear.getJumlahAset());
        jumlahAset.put("amountTwoYearsBefore", financialPositionTwoYearsBefore.getJumlahAset());
        jumlahAset.put("level", 1);

        dataAset.add(header);
        dataAset.add(kasDanSetaraKas);
        dataAset.add(piutangBunga);
        dataAset.add(persediaanDanBiayaDibayarDiMuka);
        dataAset.add(piutangLainLain);
        dataAset.add(investasiLancar);
        dataAset.add(propertiInvestasi);
        dataAset.add(asetTetap);
        dataAset.add(investasiJangkaPanjang);
        dataAset.add(jumlahAset);

        return dataAset;
    }

    public List<HashMap<String, Object>> getLiabilitas(AutomaticReportFinancialPosition financialPositionLastYear, AutomaticReportFinancialPosition financialPositionTwoYearsBefore) {
        List<HashMap<String, Object>> dataLiabilitas = new ArrayList<HashMap<String, Object>>();

        HashMap<String, Object> header = new HashMap<String, Object>();
        header.put("name", "Liabilitas");
        header.put("level", 0);


        HashMap<String, Object> utangDagang = new HashMap<String, Object>();
        utangDagang.put("name", "Utang Dagang");
        utangDagang.put("amountLastYear", financialPositionLastYear.getUtangDagang());
        utangDagang.put("amountTwoYearsBefore", financialPositionTwoYearsBefore.getUtangDagang());
        utangDagang.put("level", 1);

        
        HashMap<String, Object> pendapatanDiterimaDiMukaYangDapatDikembalikan = new HashMap<String, Object>();
        pendapatanDiterimaDiMukaYangDapatDikembalikan.put("name", "Pendapatan Diterima Di Muka Yang Dapat Dikembalikan");
        pendapatanDiterimaDiMukaYangDapatDikembalikan.put("amountLastYear", financialPositionLastYear.getPendapatanDiterimaDiMukaYangDapatDikembalikan());
        pendapatanDiterimaDiMukaYangDapatDikembalikan.put("amountTwoYearsBefore", financialPositionTwoYearsBefore.getPendapatanDiterimaDiMukaYangDapatDikembalikan());
        pendapatanDiterimaDiMukaYangDapatDikembalikan.put("level", 1);
        
        HashMap<String, Object> utangLainLain = new HashMap<String, Object>();
        utangLainLain.put("name", "Utang Lain-Lain");
        utangLainLain.put("amountLastYear", financialPositionLastYear.getUtangLainLain());
        utangLainLain.put("amountTwoYearsBefore", financialPositionTwoYearsBefore.getUtangLainLain());
        utangLainLain.put("level", 1);
        
        HashMap<String, Object> utangWesel = new HashMap<String, Object>();
        utangWesel.put("name", "Utang Wesel");
        utangWesel.put("amountLastYear", financialPositionLastYear.getUtangWesel());
        utangWesel.put("amountTwoYearsBefore", financialPositionTwoYearsBefore.getUtangWesel());
        utangWesel.put("level", 1);
        
        HashMap<String, Object> kewajibanTahunan = new HashMap<String, Object>();
        kewajibanTahunan.put("name", "Kewajiban Tahunan");
        kewajibanTahunan.put("amountLastYear", financialPositionLastYear.getKewajibanTahunan());
        kewajibanTahunan.put("amountTwoYearsBefore", financialPositionTwoYearsBefore.getKewajibanTahunan());
        kewajibanTahunan.put("level", 1);
        
        HashMap<String, Object> utangJangkaPanjang = new HashMap<String, Object>();
        utangJangkaPanjang.put("name", "Utang Jangka Panjang");
        utangJangkaPanjang.put("amountLastYear", financialPositionLastYear.getUtangJangkaPanjang());
        utangJangkaPanjang.put("amountTwoYearsBefore", financialPositionTwoYearsBefore.getUtangJangkaPanjang());
        utangJangkaPanjang.put("level", 1);
        
        HashMap<String, Object> jumlahLiabilitas = new HashMap<String, Object>();
        jumlahLiabilitas.put("name", "Jumlah Liabilitas");
        jumlahLiabilitas.put("amountLastYear", financialPositionLastYear.getJumlahLiabilitas());
        jumlahLiabilitas.put("amountTwoYearsBefore", financialPositionTwoYearsBefore.getJumlahLiabilitas());
        jumlahLiabilitas.put("level", 1);

        dataLiabilitas.add(header);
        dataLiabilitas.add(utangDagang);
        dataLiabilitas.add(pendapatanDiterimaDiMukaYangDapatDikembalikan);
        dataLiabilitas.add(utangLainLain);
        dataLiabilitas.add(utangWesel);
        dataLiabilitas.add(kewajibanTahunan);
        dataLiabilitas.add(utangJangkaPanjang);
        dataLiabilitas.add(jumlahLiabilitas);

        return dataLiabilitas;
    }

    public List<HashMap<String, Object>> getAsetNeto(AutomaticReportFinancialPosition financialPositionLastYear, AutomaticReportFinancialPosition financialPositionTwoYearsBefore) {
        List<HashMap<String, Object>> dataAsetNeto = new ArrayList<HashMap<String, Object>>();

        HashMap<String, Object> header = new HashMap<String, Object>();
        header.put("name", "Aset Neto");
        header.put("level", 0);

        HashMap<String, Object> asetNetoTidakTerikat = new HashMap<String, Object>();
        asetNetoTidakTerikat.put("name", "Tidak Terikat");
        asetNetoTidakTerikat.put("amountLastYear", financialPositionLastYear.getAsetNetoTidakTerikat());
        asetNetoTidakTerikat.put("amountTwoYearsBefore", financialPositionTwoYearsBefore.getAsetNetoTidakTerikat());
        asetNetoTidakTerikat.put("level", 0);

        HashMap<String, Object> asetNetoTerikatTemporer = new HashMap<String, Object>();
        asetNetoTerikatTemporer.put("name", "Terikat Temporer");
        asetNetoTerikatTemporer.put("amountLastYear", financialPositionLastYear.getAsetNetoTerikatTemporer());
        asetNetoTerikatTemporer.put("amountTwoYearsBefore", financialPositionTwoYearsBefore.getAsetNetoTerikatTemporer());
        asetNetoTerikatTemporer.put("level", 0);

        HashMap<String, Object> asetNetoTerikatPermanen = new HashMap<String, Object>();
        asetNetoTerikatPermanen.put("name", "Terikat Permanen");
        asetNetoTerikatPermanen.put("amountLastYear", financialPositionLastYear.getAsetNetoTerikatPermanen());
        asetNetoTerikatPermanen.put("amountTwoYearsBefore", financialPositionTwoYearsBefore.getAsetNetoTerikatPermanen());
        asetNetoTerikatPermanen.put("level", 0);

        HashMap<String, Object> jumlahAsetNeto = new HashMap<String, Object>();
        jumlahAsetNeto.put("name", "Jumlah Aset Neto");
        jumlahAsetNeto.put("amountLastYear", financialPositionLastYear.getJumlahAsetNeto());
        jumlahAsetNeto.put("amountTwoYearsBefore", financialPositionTwoYearsBefore.getJumlahAsetNeto());
        jumlahAsetNeto.put("level", 0);

        dataAsetNeto.add(header);
        dataAsetNeto.add(asetNetoTidakTerikat);
        dataAsetNeto.add(asetNetoTerikatTemporer);
        dataAsetNeto.add(asetNetoTerikatPermanen);
        dataAsetNeto.add(jumlahAsetNeto);

        return dataAsetNeto;
    }

    public HashMap<String, Object> getJumlahLiabilitasDanAsetNeto(AutomaticReportFinancialPosition financialPositionLastYear, AutomaticReportFinancialPosition financialPositionTwoYearsBefore) {
        HashMap<String, Object> jumlahLiabilitasDanAsetNeto = new HashMap<String, Object>();
        jumlahLiabilitasDanAsetNeto.put("name", "Jumlah Liabilitas dan Aset Neto");
        jumlahLiabilitasDanAsetNeto.put("amountLastYear", financialPositionLastYear.getJumlahLiabilitas() + financialPositionLastYear.getJumlahAsetNeto());
        jumlahLiabilitasDanAsetNeto.put("amountTwoYearsBefore", financialPositionTwoYearsBefore.getJumlahLiabilitas() + financialPositionTwoYearsBefore.getJumlahAsetNeto());
        jumlahLiabilitasDanAsetNeto.put("level", 0);

        return jumlahLiabilitasDanAsetNeto;
    }
}
