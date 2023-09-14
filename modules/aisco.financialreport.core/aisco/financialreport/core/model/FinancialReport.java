package aisco.financialreport.core;
import aisco.program.core.Program;
import aisco.chartofaccount.core.ChartOfAccount;

import vmj.routing.route.Route;
import vmj.routing.route.VMJExchange;
import java.util.*;

public interface FinancialReport {
    UUID getId();
    void setId(UUID id);

    String getDatestamp();
    void setDatestamp(String datestamp);

    long getAmount();
    void setAmount(long amount);

    String getDescription();
    void setDescription(String description);

    Program getProgram();
    void setProgram(Program program);

    ChartOfAccount getCoa();
    void setCoa(ChartOfAccount coa);

    HashMap<String, Object> toHashMap();
}