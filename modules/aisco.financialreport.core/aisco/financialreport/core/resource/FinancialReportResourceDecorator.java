package aisco.financialreport.core;

import java.util.*;

import vmj.routing.route.Route;
import vmj.routing.route.VMJExchange;

public abstract class FinancialReportResourceDecorator extends FinancialReportResourceComponent {

	protected FinancialReportResourceComponent record;
	
    public FinancialReportResourceDecorator(FinancialReportResourceComponent record) {
    	this.record = record;
    }
}