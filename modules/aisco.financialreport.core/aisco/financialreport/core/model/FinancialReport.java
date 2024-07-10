package aisco.financialreport.core;
import aisco.program.core.Program;
import aisco.chartofaccount.core.ChartOfAccount;

import vmj.routing.route.Route;
import vmj.routing.route.VMJExchange;
import java.util.*;

public interface FinancialReport {
    UUID getIdFinancialReport();
    void setIdFinancialReport(UUID idFinancialReport);

    String getDatestamp();
    void setDatestamp(String datestamp);

    long getAmountFinancialReport();
    void setAmountFinancialReport(long amountFinancialReport);

    String getDescriptionFinancialReport();
    void setDescriptionFinancialReport(String descriptionFinancialReport);

    Program getProgram();
    void setProgram(Program program);

    ChartOfAccount getCoa();
    void setCoa(ChartOfAccount coa);

    HashMap<String, Object> toHashMap();
}