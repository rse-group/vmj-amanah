package aisco.automaticreport.activityreport;

import java.util.*;
import vmj.hibernate.integrator.RepositoryUtil;
import vmj.routing.route.Route;
import vmj.routing.route.VMJExchange;
import vmj.routing.route.exceptions.*;

import aisco.automaticreport.core.AutomaticReportResourceDecorator;
import aisco.automaticreport.core.AutomaticReportResourceComponent;
import aisco.automaticreport.periodic.*;
import aisco.financialreport.core.FinancialReport;

public class AutomaticReportResourceImpl extends AutomaticReportResourceDecorator {
    protected RepositoryUtil<AutomaticReportActivityReport> AutomaticReportActivityReportRepository;
    protected RepositoryUtil<AutomaticReportPeriodic> automaticReportPeriodicRepository;

    public AutomaticReportResourceImpl(AutomaticReportResourceComponent record) {
        super(record);
        this.AutomaticReportActivityReportRepository = new RepositoryUtil<AutomaticReportActivityReport>(
            aisco.automaticreport.activityreport.AutomaticReportActivityReportComponent.class);
        this.automaticReportPeriodicRepository = new RepositoryUtil<AutomaticReportPeriodic>(
            aisco.automaticreport.periodic.AutomaticReportPeriodicComponent.class);
    }

    @Route(url = "call/automatic-report-activity")
    public List<HashMap<String, Object>> saveAutomaticReportActivityReport(VMJExchange vmjExchange) {
        List<AutomaticReportActivityReport> AutomaticReportActivityReport = checkActivityReport();
        
        return transformListActivityToHashMap(AutomaticReportActivityReport);
    }

    private List<AutomaticReportActivityReport> checkActivityReport() {
        AutomaticReportPeriodic activePeriodic = getActivePeriodic();
        if (activePeriodic == null) throw new InternalServerException("Periode aktif tidak ditemukan", 5002);

        List<AutomaticReportActivityReport> activities = AutomaticReportActivityReportRepository.getAllObject("automaticreport_activityrepot_impl");
        List<AutomaticReportActivityReport> activityReport = new ArrayList<AutomaticReportActivityReport>();

        for (AutomaticReportActivityReport report : activities) {
            AutomaticReportActivityReportImpl reportImpl = (AutomaticReportActivityReportImpl) report;
            if (((AutomaticReportPeriodicImpl) reportImpl.periodic).name.equals(((AutomaticReportPeriodicImpl) activePeriodic).name)) {
                activityReport.add(report);
            }
        }

        if (activityReport.size() != 0) return activityReport;

        activityReport = createAutomaticReportActivityReport(activePeriodic);

        return activityReport;
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

    private List<AutomaticReportActivityReport> createAutomaticReportActivityReport(AutomaticReportPeriodic periodic) {
        List<AutomaticReportActivityReport> activityReport = new ArrayList<AutomaticReportActivityReport>();

        AutomaticReportActivityReport notBound = createActivityReportNotBound(periodic);
        AutomaticReportActivityReport temporarilyBound = createActivityReportTemporarilyBound(periodic);
        AutomaticReportActivityReport permanentBound = createActivityReportPermanentBound(periodic);

        activityReport.add(notBound);
        activityReport.add(temporarilyBound);
        activityReport.add(permanentBound);

        return activityReport;
    }

    private AutomaticReportActivityReport createActivityReportNotBound(AutomaticReportPeriodic periodic) {
        List<FinancialReport> financialReports = financialReportRepository.getAllObject("financialreport_impl");
        List<AutomaticReportActivityReport> activities = AutomaticReportActivityReportRepository.getAllObject("automaticreport_activity_impl");
        String tahun = Integer.toString(Integer.valueOf(((AutomaticReportPeriodicImpl) periodic).name) - 1);

        String pembatasan = "TIDAK TERIKAT";
        int sumbangan = 0;
        int jasaLayanan = 0;
        int penghasilanInvestasiJangkaPanjang = 0;
        int penghasilanInvestasiLain = 0;
        int penghasilanNetoTerealisasikanDanBelumTerealisasikanDariIJP = 0;
        int lainLain = 0;
        int pemenuhanProgramPembatasan = 0;
        int pemenuhanPembatasanPemerolehanPeralatan = 0;
        int berakhirnyaPembatasanWaktu = 0;
        int bebanDanKerugianProgram = 0;
        int bebanDanKerugianManajemendanUmum = 0;
        int bebanDanKerugianPencarianDana = 0;
        int kerugianAktuarialDanKewajibanTahunan = 0;
        int asetNetoAwalTahun = 0;

        // Saran, masukan code coa ke property files karena code coa bersifat dinamis dan spesifik antara organisasi. 
        // Oleh karena itu akan lebih baik jika user/developer tidak perlu mengubah generated code nantinya dan hanya mengubah data pada property files
        for (FinancialReport report : financialReports) {
            if (!report.getDatestamp().contains(tahun)) continue;

            String coa = Integer.toString(report.getCoa().getCode());

            if (coa.startsWith("112") || coa.startsWith("121")) {
                lainLain += report.getAmount();
            } else if (coa.startsWith("4")) {
                sumbangan += report.getAmount();
            } else if (coa.startsWith("61")) {
                bebanDanKerugianProgram += report.getAmount();
            } else if (coa.startsWith("62") || coa.startsWith("63") || coa.startsWith("64") || coa.startsWith("67") || coa.startsWith("69")) {
                bebanDanKerugianManajemendanUmum += report.getAmount();
            } else if (coa.startsWith("66") || coa.startsWith("68")) {
                kerugianAktuarialDanKewajibanTahunan += report.getAmount();
            }
        }

        for (AutomaticReportActivityReport activity : activities) {
            AutomaticReportActivityReportImpl activityImpl = (AutomaticReportActivityReportImpl) activity;
            AutomaticReportPeriodicImpl activityPeriodic = (AutomaticReportPeriodicImpl) activityImpl.periodic; 

            if (!((Integer.parseInt(activityPeriodic.name) == Integer.parseInt(((AutomaticReportPeriodicImpl) periodic).name) - 1) && activityImpl.pembatasan.equals(pembatasan))) {
                continue;
            }

            asetNetoAwalTahun = activityImpl.sumbangan + activityImpl.jasaLayanan + activityImpl.penghasilanInvestasiJangkaPanjang + activityImpl.penghasilanInvestasiLain + activityImpl.penghasilanNetoTerealisasikanDanBelumTerealisasikanDariIJP + activityImpl.lainLain - (activityImpl.bebanDanKerugianProgram + activityImpl.bebanDanKerugianPencarianDana + activityImpl.bebanDanKerugianManajemendanUmum + activityImpl.kerugianAktuarialDanKewajibanTahunan) + activityImpl.asetNetoAwalTahun;
            break;
        }

        AutomaticReportActivityReport AutomaticReportActivityReport = AutomaticReportActivityReportFactory
                .createAutomaticReportActivityReport(
                        "aisco.automaticreport.activityreport.AutomaticReportActivityReportImpl",
                        pembatasan, sumbangan, jasaLayanan, penghasilanInvestasiJangkaPanjang, penghasilanInvestasiLain, 
                        penghasilanNetoTerealisasikanDanBelumTerealisasikanDariIJP, 
                        lainLain, pemenuhanProgramPembatasan, pemenuhanPembatasanPemerolehanPeralatan, 
                        berakhirnyaPembatasanWaktu, bebanDanKerugianProgram, bebanDanKerugianManajemendanUmum, 
                        bebanDanKerugianPencarianDana, kerugianAktuarialDanKewajibanTahunan, asetNetoAwalTahun, periodic);

        AutomaticReportActivityReportRepository.saveObject(AutomaticReportActivityReport);

        return AutomaticReportActivityReport;
    }

    private AutomaticReportActivityReport createActivityReportTemporarilyBound(AutomaticReportPeriodic periodic) {
        List<FinancialReport> financialReports = financialReportRepository.getAllObject("financialreport_impl");
        List<AutomaticReportActivityReport> activities = AutomaticReportActivityReportRepository.getAllObject("automaticreport_activity_impl");
        String tahun = Integer.toString(Integer.valueOf(((AutomaticReportPeriodicImpl) periodic).name) - 1);

        String pembatasan = "TERIKAT TEMPORER";
        int sumbangan = 0;
        int jasaLayanan = 0;
        int penghasilanInvestasiJangkaPanjang = 0;
        int penghasilanInvestasiLain = 0;
        int penghasilanNetoTerealisasikanDanBelumTerealisasikanDariIJP = 0;
        int lainLain = 0;
        int pemenuhanProgramPembatasan = 0;
        int pemenuhanPembatasanPemerolehanPeralatan = 0;
        int berakhirnyaPembatasanWaktu = 0;
        int bebanDanKerugianProgram = 0;
        int bebanDanKerugianManajemendanUmum = 0;
        int bebanDanKerugianPencarianDana = 0;
        int kerugianAktuarialDanKewajibanTahunan = 0;
        int asetNetoAwalTahun = 0;

        for (FinancialReport report : financialReports) {
            if (!report.getDatestamp().contains(tahun)) continue;

            String coa = Integer.toString(report.getCoa().getCode());
        }

        for (AutomaticReportActivityReport activity : activities) {
            AutomaticReportActivityReportImpl activityImpl = (AutomaticReportActivityReportImpl) activity;
            AutomaticReportPeriodicImpl activityPeriodic = (AutomaticReportPeriodicImpl) activityImpl.periodic; 

            if (!((Integer.parseInt(activityPeriodic.name) == Integer.parseInt(((AutomaticReportPeriodicImpl) periodic).name) - 1) && activityImpl.pembatasan.equals(pembatasan))) {
                continue;
            }

            asetNetoAwalTahun = activityImpl.sumbangan + activityImpl.jasaLayanan + activityImpl.penghasilanInvestasiJangkaPanjang + activityImpl.penghasilanInvestasiLain + activityImpl.penghasilanNetoTerealisasikanDanBelumTerealisasikanDariIJP + activityImpl.lainLain - (activityImpl.bebanDanKerugianProgram + activityImpl.bebanDanKerugianPencarianDana + activityImpl.bebanDanKerugianManajemendanUmum + activityImpl.kerugianAktuarialDanKewajibanTahunan) + activityImpl.asetNetoAwalTahun;
            break;
        }

        AutomaticReportActivityReport AutomaticReportActivityReport = AutomaticReportActivityReportFactory
                .createAutomaticReportActivityReport(
                        "aisco.automaticreport.activityreport.AutomaticReportActivityReportImpl",
                        pembatasan, sumbangan, jasaLayanan, penghasilanInvestasiJangkaPanjang, penghasilanInvestasiLain,
                        penghasilanNetoTerealisasikanDanBelumTerealisasikanDariIJP, 
                        lainLain, pemenuhanProgramPembatasan, pemenuhanPembatasanPemerolehanPeralatan, 
                        berakhirnyaPembatasanWaktu, bebanDanKerugianProgram, bebanDanKerugianManajemendanUmum, 
                        bebanDanKerugianPencarianDana, kerugianAktuarialDanKewajibanTahunan, asetNetoAwalTahun, periodic);

        AutomaticReportActivityReportRepository.saveObject(AutomaticReportActivityReport);
                        
        return AutomaticReportActivityReport;
    }

    private AutomaticReportActivityReport createActivityReportPermanentBound(AutomaticReportPeriodic periodic) {
        List<FinancialReport> financialReports = financialReportRepository.getAllObject("financialreport_impl");
        List<AutomaticReportActivityReport> activities = AutomaticReportActivityReportRepository.getAllObject("automaticreport_activity_impl");
        String tahun = Integer.toString(Integer.valueOf(((AutomaticReportPeriodicImpl) periodic).name) - 1);

        String pembatasan = "TERIKAT PERMANEN";
        int sumbangan = 0;
        int jasaLayanan = 0;
        int penghasilanInvestasiJangkaPanjang = 0;
        int penghasilanInvestasiLain = 0;
        int penghasilanNetoTerealisasikanDanBelumTerealisasikanDariIJP = 0;
        int lainLain = 0;
        int pemenuhanProgramPembatasan = 0;
        int pemenuhanPembatasanPemerolehanPeralatan = 0;
        int berakhirnyaPembatasanWaktu = 0;
        int bebanDanKerugianProgram = 0;
        int bebanDanKerugianManajemendanUmum = 0;
        int bebanDanKerugianPencarianDana = 0;
        int kerugianAktuarialDanKewajibanTahunan = 0;
        int asetNetoAwalTahun = 0;

        // Saran, masukan code coa ke property files karena code coa bersifat dinamis dan spesifik antara organisasi. 
        // Oleh karena itu akan lebih baik jika user/developer tidak perlu mengubah generated code nantinya dan hanya mengubah data pada property files
        for (FinancialReport report : financialReports) {
            if (!report.getDatestamp().contains(tahun)) continue;

            String coa = Integer.toString(report.getCoa().getCode());

            if (coa.startsWith("111") || coa.startsWith("122")) {
                lainLain += report.getAmount();
            } else if (coa.startsWith("65")) {
                bebanDanKerugianProgram += report.getAmount();
            }
        }

        for (AutomaticReportActivityReport activity : activities) {
            AutomaticReportActivityReportImpl activityImpl = (AutomaticReportActivityReportImpl) activity;
            AutomaticReportPeriodicImpl activityPeriodic = (AutomaticReportPeriodicImpl) activityImpl.periodic; 

            if (!((Integer.parseInt(activityPeriodic.name) == Integer.parseInt(((AutomaticReportPeriodicImpl) periodic).name) - 1) && activityImpl.pembatasan.equals(pembatasan))) {
                continue;
            }

            asetNetoAwalTahun = activityImpl.sumbangan + activityImpl.jasaLayanan + activityImpl.penghasilanInvestasiJangkaPanjang + activityImpl.penghasilanInvestasiLain + activityImpl.penghasilanNetoTerealisasikanDanBelumTerealisasikanDariIJP + activityImpl.lainLain - (activityImpl.bebanDanKerugianProgram + activityImpl.bebanDanKerugianPencarianDana + activityImpl.bebanDanKerugianManajemendanUmum + activityImpl.kerugianAktuarialDanKewajibanTahunan) + activityImpl.asetNetoAwalTahun;
            break;
        }

        AutomaticReportActivityReport AutomaticReportActivityReport = AutomaticReportActivityReportFactory
                .createAutomaticReportActivityReport(
                        "aisco.automaticreport.activityreport.AutomaticReportActivityReportImpl",
                        pembatasan, sumbangan, jasaLayanan, penghasilanInvestasiJangkaPanjang, penghasilanInvestasiLain, 
                        penghasilanNetoTerealisasikanDanBelumTerealisasikanDariIJP, 
                        lainLain, pemenuhanProgramPembatasan, pemenuhanPembatasanPemerolehanPeralatan, 
                        berakhirnyaPembatasanWaktu, bebanDanKerugianProgram, bebanDanKerugianManajemendanUmum, 
                        bebanDanKerugianPencarianDana, kerugianAktuarialDanKewajibanTahunan, asetNetoAwalTahun, periodic);

        AutomaticReportActivityReportRepository.saveObject(AutomaticReportActivityReport);

        return AutomaticReportActivityReport;
    }

    private List<HashMap<String, Object>> transformListActivityToHashMap(List<AutomaticReportActivityReport> activities) {
        List<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
        AutomaticReportActivityReportImpl notBound = null;
        AutomaticReportActivityReportImpl temporarilyBound = null;
        AutomaticReportActivityReportImpl permanentBound = null;

        for (AutomaticReportActivityReport activity : activities) {
            if (activity.getPembatasan().equals("TIDAK TERIKAT")) {
                notBound = (AutomaticReportActivityReportImpl) activity;
            } else if (activity.getPembatasan().equals("TERIKAT TEMPORER")) {
                temporarilyBound = (AutomaticReportActivityReportImpl) activity;
            } else if (activity.getPembatasan().equals("TERIKAT PERMANEN")) {
                permanentBound = (AutomaticReportActivityReportImpl) activity;
            }
        }

        if (notBound == null|| temporarilyBound == null || permanentBound == null) throw new InternalServerException("Tidak bisa membuat JSON dari laporan aktivitas karena data tidak lengkap", 5002);
        data.addAll(getPendapatanPenghasilanDanSumbanganLain(notBound, temporarilyBound, permanentBound));
        data.addAll(getBebanDanKerugian(notBound, temporarilyBound, permanentBound));
        data.addAll(getAsetNeto(notBound, temporarilyBound, permanentBound));
        return data;
    }

    private List<HashMap<String, Object>> getPendapatanPenghasilanDanSumbanganLain(AutomaticReportActivityReportImpl notBound, AutomaticReportActivityReportImpl temporarilyBound, AutomaticReportActivityReportImpl permanentBound) {
        List<HashMap<String, Object>> dataPendapatan = new ArrayList<HashMap<String, Object>>();

        HashMap<String, Object> header = new HashMap<String, Object>();
        header.put("name", "Pendapatan, penghasilan, dan sumbangan lain");
        header.put("level", 0);

        HashMap<String, Object> sumbangan = new HashMap<String, Object>();
        sumbangan.put("name", "Sumbangan");
        sumbangan.put("tidakTerikat", notBound.sumbangan);
        sumbangan.put("terikatTemporer", temporarilyBound.sumbangan);
        sumbangan.put("terikatPermanen", permanentBound.sumbangan);
        sumbangan.put("jumlah", notBound.sumbangan + temporarilyBound.sumbangan + permanentBound.sumbangan);
        sumbangan.put("level", 1);

        HashMap<String, Object> jasaLayanan = new HashMap<String, Object>();
        jasaLayanan.put("name", "Jasa Layanan");
        jasaLayanan.put("tidakTerikat", notBound.jasaLayanan);
        jasaLayanan.put("terikatTemporer", temporarilyBound.jasaLayanan);
        jasaLayanan.put("terikatPermanen", permanentBound.jasaLayanan);
        jasaLayanan.put("jumlah", notBound.jasaLayanan + temporarilyBound.jasaLayanan + permanentBound.jasaLayanan);
        jasaLayanan.put("level", 1);
    
        HashMap<String, Object> penghasilanInvestasiJangkaPanjang = new HashMap<String, Object>();
        penghasilanInvestasiJangkaPanjang.put("name", "Penghasilan Investasi Jangka Panjang");
        penghasilanInvestasiJangkaPanjang.put("tidakTerikat", notBound.penghasilanInvestasiJangkaPanjang);
        penghasilanInvestasiJangkaPanjang.put("terikatTemporer", temporarilyBound.penghasilanInvestasiJangkaPanjang);
        penghasilanInvestasiJangkaPanjang.put("terikatPermanen", permanentBound.penghasilanInvestasiJangkaPanjang);
        penghasilanInvestasiJangkaPanjang.put("jumlah", notBound.penghasilanInvestasiJangkaPanjang + temporarilyBound.penghasilanInvestasiJangkaPanjang + permanentBound.penghasilanInvestasiJangkaPanjang);
        penghasilanInvestasiJangkaPanjang.put("level", 1);

        HashMap<String, Object> penghasilanInvestasiLain = new HashMap<String, Object>();
        penghasilanInvestasiLain.put("name", "Penghasilan Investasi Lain");
        penghasilanInvestasiLain.put("tidakTerikat", notBound.penghasilanInvestasiLain);
        penghasilanInvestasiLain.put("terikatTemporer", temporarilyBound.penghasilanInvestasiLain);
        penghasilanInvestasiLain.put("terikatPermanen", permanentBound.penghasilanInvestasiLain);
        penghasilanInvestasiLain.put("jumlah", notBound.penghasilanInvestasiLain + temporarilyBound.penghasilanInvestasiLain + permanentBound.penghasilanInvestasiLain);
        penghasilanInvestasiLain.put("level", 1);

        HashMap<String, Object> penghasilanNetoTerealisasikanDanBelumTerealisasikanDariIJP = new HashMap<String, Object>();
        penghasilanNetoTerealisasikanDanBelumTerealisasikanDariIJP.put("name", "Penghasilan Neto Terealisasikan dan Belum Terealisasikan dari Investasi Jangka Panjang");
        penghasilanNetoTerealisasikanDanBelumTerealisasikanDariIJP.put("tidakTerikat", notBound.penghasilanNetoTerealisasikanDanBelumTerealisasikanDariIJP);
        penghasilanNetoTerealisasikanDanBelumTerealisasikanDariIJP.put("terikatTemporer", temporarilyBound.penghasilanNetoTerealisasikanDanBelumTerealisasikanDariIJP);
        penghasilanNetoTerealisasikanDanBelumTerealisasikanDariIJP.put("terikatPermanen", permanentBound.penghasilanNetoTerealisasikanDanBelumTerealisasikanDariIJP);
        penghasilanNetoTerealisasikanDanBelumTerealisasikanDariIJP.put("jumlah", notBound.penghasilanNetoTerealisasikanDanBelumTerealisasikanDariIJP + temporarilyBound.penghasilanNetoTerealisasikanDanBelumTerealisasikanDariIJP + permanentBound.penghasilanNetoTerealisasikanDanBelumTerealisasikanDariIJP);
        penghasilanNetoTerealisasikanDanBelumTerealisasikanDariIJP.put("level", 1);

        HashMap<String, Object> lainLain = new HashMap<String, Object>();
        lainLain.put("name", "Lain-Lain");
        lainLain.put("tidakTerikat", notBound.lainLain);
        lainLain.put("terikatTemporer", temporarilyBound.lainLain);
        lainLain.put("terikatPermanen", permanentBound.lainLain);
        lainLain.put("jumlah", notBound.lainLain + temporarilyBound.lainLain + permanentBound.lainLain);
        lainLain.put("level", 1);

        HashMap<String, Object> header2 = new HashMap<String, Object>();
        header2.put("name", "Aset Neto yang Berakhir Pembatasannya");
        header2.put("level", 1);

        HashMap<String, Object> pemenuhanProgramPembatasan = new HashMap<String, Object>();
        pemenuhanProgramPembatasan.put("name", "Pemenuhan Program Pembatasan");
        pemenuhanProgramPembatasan.put("tidakTerikat", notBound.pemenuhanProgramPembatasan);
        pemenuhanProgramPembatasan.put("terikatTemporer", temporarilyBound.pemenuhanProgramPembatasan);
        pemenuhanProgramPembatasan.put("terikatPermanen", permanentBound.pemenuhanProgramPembatasan);
        pemenuhanProgramPembatasan.put("jumlah", notBound.pemenuhanProgramPembatasan + temporarilyBound.pemenuhanProgramPembatasan + permanentBound.pemenuhanProgramPembatasan);
        pemenuhanProgramPembatasan.put("level", 1);

        HashMap<String, Object> pemenuhanPembatasanPemerolehanPeralatan = new HashMap<String, Object>();
        pemenuhanPembatasanPemerolehanPeralatan.put("name", "Pemenuhan Pembatasan Pemerolehan Peralatan");
        pemenuhanPembatasanPemerolehanPeralatan.put("tidakTerikat", notBound.pemenuhanPembatasanPemerolehanPeralatan);
        pemenuhanPembatasanPemerolehanPeralatan.put("terikatTemporer", temporarilyBound.pemenuhanPembatasanPemerolehanPeralatan);
        pemenuhanPembatasanPemerolehanPeralatan.put("terikatPermanen", permanentBound.pemenuhanPembatasanPemerolehanPeralatan);
        pemenuhanPembatasanPemerolehanPeralatan.put("jumlah", notBound.pemenuhanPembatasanPemerolehanPeralatan + temporarilyBound.pemenuhanPembatasanPemerolehanPeralatan + permanentBound.pemenuhanPembatasanPemerolehanPeralatan);
        pemenuhanPembatasanPemerolehanPeralatan.put("level", 1);

        HashMap<String, Object> berakhirnyaPembatasanWaktu = new HashMap<String, Object>();
        berakhirnyaPembatasanWaktu.put("name", "Berakhirnya Pembatasan Waktu");
        berakhirnyaPembatasanWaktu.put("tidakTerikat", notBound.berakhirnyaPembatasanWaktu);
        berakhirnyaPembatasanWaktu.put("terikatTemporer", temporarilyBound.berakhirnyaPembatasanWaktu);
        berakhirnyaPembatasanWaktu.put("terikatPermanen", permanentBound.berakhirnyaPembatasanWaktu);
        berakhirnyaPembatasanWaktu.put("jumlah", notBound.berakhirnyaPembatasanWaktu + temporarilyBound.berakhirnyaPembatasanWaktu + permanentBound.berakhirnyaPembatasanWaktu);
        berakhirnyaPembatasanWaktu.put("level", 1);
        
        HashMap<String, Object> jumlahPendapatanPenghasilanDanSumbangan = new HashMap<String, Object>();
        jumlahPendapatanPenghasilanDanSumbangan.put("name", "Jumlah Pendapatan Penghasilan Dan Sumbangan");
        jumlahPendapatanPenghasilanDanSumbangan.put("tidakTerikat", notBound.sumbangan + notBound.jasaLayanan + notBound.penghasilanInvestasiJangkaPanjang + notBound.penghasilanInvestasiLain + notBound.penghasilanNetoTerealisasikanDanBelumTerealisasikanDariIJP + notBound.lainLain);
        jumlahPendapatanPenghasilanDanSumbangan.put("terikatTemporer", temporarilyBound.sumbangan + temporarilyBound.jasaLayanan + temporarilyBound.penghasilanInvestasiJangkaPanjang + temporarilyBound.penghasilanInvestasiLain + temporarilyBound.penghasilanNetoTerealisasikanDanBelumTerealisasikanDariIJP + temporarilyBound.lainLain);
        jumlahPendapatanPenghasilanDanSumbangan.put("terikatPermanen", permanentBound.sumbangan + permanentBound.jasaLayanan + permanentBound.penghasilanInvestasiJangkaPanjang + permanentBound.penghasilanInvestasiLain + permanentBound.penghasilanNetoTerealisasikanDanBelumTerealisasikanDariIJP + permanentBound.lainLain);
        jumlahPendapatanPenghasilanDanSumbangan.put("jumlah", (Integer) jumlahPendapatanPenghasilanDanSumbangan.get("tidakTerikat") + (Integer) jumlahPendapatanPenghasilanDanSumbangan.get("terikatTemporer") + (Integer) jumlahPendapatanPenghasilanDanSumbangan.get("terikatPermanen"));
        jumlahPendapatanPenghasilanDanSumbangan.put("level", 1);
    
        dataPendapatan.add(header);
        dataPendapatan.add(sumbangan);
        dataPendapatan.add(jasaLayanan);
        dataPendapatan.add(penghasilanInvestasiJangkaPanjang);
        dataPendapatan.add(penghasilanInvestasiLain);
        dataPendapatan.add(penghasilanNetoTerealisasikanDanBelumTerealisasikanDariIJP);
        dataPendapatan.add(lainLain);
        dataPendapatan.add(header2);
        dataPendapatan.add(pemenuhanProgramPembatasan);
        dataPendapatan.add(pemenuhanPembatasanPemerolehanPeralatan);
        dataPendapatan.add(berakhirnyaPembatasanWaktu);
        dataPendapatan.add(jumlahPendapatanPenghasilanDanSumbangan);

        return dataPendapatan;
    }

    private List<HashMap<String, Object>> getBebanDanKerugian(AutomaticReportActivityReportImpl notBound, AutomaticReportActivityReportImpl temporarilyBound, AutomaticReportActivityReportImpl permanentBound) {
        List<HashMap<String, Object>> dataBeban = new ArrayList<HashMap<String, Object>>();
        
        HashMap<String, Object> header = new HashMap<String, Object>();
        header.put("name", "Beban dan Kerugian");
        header.put("level", 0);

        HashMap<String, Object> bebanProgram = new HashMap<String, Object>();
        bebanProgram.put("name", "Program");
        bebanProgram.put("tidakTerikat", notBound.bebanDanKerugianProgram);
        bebanProgram.put("terikatTemporer", temporarilyBound.bebanDanKerugianProgram);
        bebanProgram.put("terikatPermanen", permanentBound.bebanDanKerugianProgram);
        bebanProgram.put("jumlah", notBound.bebanDanKerugianProgram + temporarilyBound.bebanDanKerugianProgram + permanentBound.bebanDanKerugianProgram);
        bebanProgram.put("level", 1);

        HashMap<String, Object> manajemenDanUmum = new HashMap<String, Object>();
        manajemenDanUmum.put("name", "Manajemen dan Umum");
        manajemenDanUmum.put("tidakTerikat", notBound.bebanDanKerugianManajemendanUmum);
        manajemenDanUmum.put("terikatTemporer", temporarilyBound.bebanDanKerugianManajemendanUmum);
        manajemenDanUmum.put("terikatPermanen", permanentBound.bebanDanKerugianManajemendanUmum);
        manajemenDanUmum.put("jumlah", notBound.bebanDanKerugianManajemendanUmum + temporarilyBound.bebanDanKerugianManajemendanUmum + permanentBound.bebanDanKerugianManajemendanUmum);
        manajemenDanUmum.put("level", 1);

        HashMap<String, Object> pencarianDana = new HashMap<String, Object>();
        pencarianDana.put("name", "Pencarian Dana");
        pencarianDana.put("tidakTerikat", notBound.bebanDanKerugianPencarianDana);
        pencarianDana.put("terikatTemporer", temporarilyBound.bebanDanKerugianPencarianDana);
        pencarianDana.put("terikatPermanen", permanentBound.bebanDanKerugianPencarianDana);
        pencarianDana.put("jumlah", notBound.bebanDanKerugianPencarianDana + temporarilyBound.bebanDanKerugianPencarianDana + permanentBound.bebanDanKerugianPencarianDana);
        pencarianDana.put("level", 1);

        HashMap<String, Object> jumlahBeban = new HashMap<String, Object>();
        jumlahBeban.put("name", "Jumlah Beban");
        jumlahBeban.put("tidakTerikat", notBound.bebanDanKerugianProgram + notBound.bebanDanKerugianPencarianDana + notBound.bebanDanKerugianManajemendanUmum);
        jumlahBeban.put("terikatTemporer", temporarilyBound.bebanDanKerugianProgram + temporarilyBound.bebanDanKerugianPencarianDana + temporarilyBound.bebanDanKerugianManajemendanUmum);
        jumlahBeban.put("terikatPermanen", permanentBound.bebanDanKerugianProgram + permanentBound.bebanDanKerugianPencarianDana + permanentBound.bebanDanKerugianManajemendanUmum);
        jumlahBeban.put("jumlah", (Integer) jumlahBeban.get("tidakTerikat") + (Integer) jumlahBeban.get("terikatTemporer") + (Integer) jumlahBeban.get("terikatPermanen"));
        jumlahBeban.put("level", 1);

        HashMap<String, Object> kerugianAktuarialDanKewajibanTahunan = new HashMap<String, Object>();
        kerugianAktuarialDanKewajibanTahunan.put("name", "Kerugian Aktuarial dan Kewajiban Tahunan");
        kerugianAktuarialDanKewajibanTahunan.put("tidakTerikat", notBound.kerugianAktuarialDanKewajibanTahunan);
        kerugianAktuarialDanKewajibanTahunan.put("terikatTemporer", temporarilyBound.kerugianAktuarialDanKewajibanTahunan);
        kerugianAktuarialDanKewajibanTahunan.put("terikatPermanen", permanentBound.kerugianAktuarialDanKewajibanTahunan);
        kerugianAktuarialDanKewajibanTahunan.put("jumlah", notBound.kerugianAktuarialDanKewajibanTahunan + temporarilyBound.kerugianAktuarialDanKewajibanTahunan + permanentBound.kerugianAktuarialDanKewajibanTahunan);
        kerugianAktuarialDanKewajibanTahunan.put("level", 1);

        HashMap<String, Object> jumlahBebanDanKerugian = new HashMap<String, Object>();
        jumlahBebanDanKerugian.put("name", "Jumlah Beban dan Kerugian");
        jumlahBebanDanKerugian.put("tidakTerikat", (Integer) jumlahBeban.get("tidakTerikat") + (Integer) kerugianAktuarialDanKewajibanTahunan.get("tidakTerikat"));
        jumlahBebanDanKerugian.put("terikatTemporer", (Integer) jumlahBeban.get("terikatTemporer") + (Integer) kerugianAktuarialDanKewajibanTahunan.get("terikatTemporer"));
        jumlahBebanDanKerugian.put("terikatPermanen", (Integer) jumlahBeban.get("terikatPermanen") + (Integer) kerugianAktuarialDanKewajibanTahunan.get("terikatPermanen"));
        jumlahBebanDanKerugian.put("jumlah", (Integer) jumlahBebanDanKerugian.get("tidakTerikat") + (Integer) jumlahBebanDanKerugian.get("terikatTemporer") + (Integer) jumlahBebanDanKerugian.get("terikatPermanen"));
        jumlahBebanDanKerugian.put("level", 2);

        dataBeban.add(header);
        dataBeban.add(bebanProgram);
        dataBeban.add(manajemenDanUmum);
        dataBeban.add(pencarianDana);
        dataBeban.add(jumlahBeban);
        dataBeban.add(kerugianAktuarialDanKewajibanTahunan);
        dataBeban.add(jumlahBebanDanKerugian);

        return dataBeban;
    }

    private List<HashMap<String, Object>> getAsetNeto (AutomaticReportActivityReportImpl notBound, AutomaticReportActivityReportImpl temporarilyBound,AutomaticReportActivityReportImpl permanentBound) {
        List<HashMap<String, Object>> dataAsetNeto = new ArrayList<HashMap<String, Object>>();

        HashMap<String, Object> perubahanAsetNeto = new HashMap<String, Object>();
        perubahanAsetNeto.put("name", "Perubahan Aset Neto");
        perubahanAsetNeto.put("tidakTerikat", notBound.sumbangan + notBound.jasaLayanan + notBound.penghasilanInvestasiJangkaPanjang + notBound.penghasilanInvestasiLain + notBound.penghasilanNetoTerealisasikanDanBelumTerealisasikanDariIJP + notBound.lainLain - (notBound.bebanDanKerugianProgram + notBound.bebanDanKerugianPencarianDana + notBound.bebanDanKerugianManajemendanUmum + notBound.kerugianAktuarialDanKewajibanTahunan));
        perubahanAsetNeto.put("terikatTemporer", temporarilyBound.sumbangan + temporarilyBound.jasaLayanan + temporarilyBound.penghasilanInvestasiJangkaPanjang + temporarilyBound.penghasilanInvestasiLain + temporarilyBound.penghasilanNetoTerealisasikanDanBelumTerealisasikanDariIJP + temporarilyBound.lainLain - (temporarilyBound.bebanDanKerugianProgram + temporarilyBound.bebanDanKerugianPencarianDana + temporarilyBound.bebanDanKerugianManajemendanUmum + temporarilyBound.kerugianAktuarialDanKewajibanTahunan));
        perubahanAsetNeto.put("terikatPermanen", permanentBound.sumbangan + permanentBound.jasaLayanan + permanentBound.penghasilanInvestasiJangkaPanjang + permanentBound.penghasilanInvestasiLain + permanentBound.penghasilanNetoTerealisasikanDanBelumTerealisasikanDariIJP + permanentBound.lainLain - (permanentBound.bebanDanKerugianProgram + permanentBound.bebanDanKerugianPencarianDana + permanentBound.bebanDanKerugianManajemendanUmum + permanentBound.kerugianAktuarialDanKewajibanTahunan));
        perubahanAsetNeto.put("jumlah", (Integer) perubahanAsetNeto.get("tidakTerikat") + (Integer) perubahanAsetNeto.get("terikatTemporer") + (Integer) perubahanAsetNeto.get("terikatPermanen"));
        perubahanAsetNeto.put("level", 0);

        HashMap<String, Object> asetNetoAwalTahun = new HashMap<String, Object>();
        asetNetoAwalTahun.put("name", "Aset Neto Awal Tahun");
        asetNetoAwalTahun.put("tidakTerikat", notBound.asetNetoAwalTahun);
        asetNetoAwalTahun.put("terikatTemporer", temporarilyBound.asetNetoAwalTahun);
        asetNetoAwalTahun.put("terikatPermanen", permanentBound.asetNetoAwalTahun);
        asetNetoAwalTahun.put("jumlah", (Integer) asetNetoAwalTahun.get("tidakTerikat") + (Integer) asetNetoAwalTahun.get("terikatTemporer") + (Integer) asetNetoAwalTahun.get("terikatPermanen"));
        asetNetoAwalTahun.put("level", 0);

        HashMap<String, Object> asetNetoAkhirTahun = new HashMap<String, Object>();
        asetNetoAkhirTahun.put("name", "Aset Neto Akhir Tahun");
        asetNetoAkhirTahun.put("tidakTerikat", (Integer) perubahanAsetNeto.get("tidakTerikat") - notBound.asetNetoAwalTahun);
        asetNetoAkhirTahun.put("terikatTemporer", (Integer) perubahanAsetNeto.get("terikatTemporer") - temporarilyBound.asetNetoAwalTahun);
        asetNetoAkhirTahun.put("terikatPermanen", (Integer) perubahanAsetNeto.get("terikatPermanen") - permanentBound.asetNetoAwalTahun);
        asetNetoAkhirTahun.put("jumlah", (Integer) asetNetoAkhirTahun.get("tidakTerikat") + (Integer) asetNetoAkhirTahun.get("terikatTemporer") + (Integer) asetNetoAkhirTahun.get("terikatPermanen"));
        asetNetoAkhirTahun.put("level", 0);

        dataAsetNeto.add(perubahanAsetNeto);
        dataAsetNeto.add(asetNetoAwalTahun);
        dataAsetNeto.add(asetNetoAkhirTahun);

        return dataAsetNeto;
    }
}
