package aisco.product.dermapeduli;

import java.util.ArrayList;

import vmj.routing.route.VMJServer;
import vmj.routing.route.Router;
import vmj.hibernate.integrator.HibernateUtil;
import org.hibernate.cfg.Configuration;

import vmj.auth.model.UserResourceFactory;
import vmj.auth.model.RoleResourceFactory;
import vmj.auth.model.core.UserResource;
import vmj.auth.model.core.RoleResource;

import aisco.program.ProgramResourceFactory;
import aisco.program.core.ProgramResource;
import aisco.chartofaccount.ChartOfAccountResourceFactory;
import aisco.chartofaccount.core.ChartOfAccountResource;
import aisco.financialreport.FinancialReportResourceFactory;
import aisco.financialreport.core.FinancialReportResource;
import aisco.automaticreport.AutomaticReportResourceFactory;
import aisco.automaticreport.core.AutomaticReportResource;
import aisco.donation.DonationResourceFactory;
import aisco.donation.core.DonationResource;

public class DermaPeduli {

	public static void main(String[] args) {

		// get hostAddress and portnum from env var
        // ex:
        // AMANAH_HOST_BE --> "localhost"
        // AMANAH_PORT_BE --> 7776
		String hostAddress= getEnvVariableHostAddress("AMANAH_HOST_BE");
        int portNum = getEnvVariablePortNumber("AMANAH_PORT_BE");
        activateServer(hostAddress, portNum);

		Configuration configuration = new Configuration();
		// panggil setter setelah membuat object dari kelas Configuration
        // ex:
        // AMANAH_DB_URL --> jdbc:postgresql://localhost:5432/superorg
        // AMANAH_DB_USERNAME --> postgres
        // AMANAH_DB_PASSWORD --> postgres123
		setDBProperties("AMANAH_DB_URL", "url", configuration);
        setDBProperties("AMANAH_DB_USERNAME", "username", configuration);
        setDBProperties("AMANAH_DB_PASSWORD","password", configuration);

		configuration.addAnnotatedClass(vmj.auth.model.core.Role.class);
        configuration.addAnnotatedClass(vmj.auth.model.core.RoleComponent.class);
        configuration.addAnnotatedClass(vmj.auth.model.core.RoleDecorator.class);
        configuration.addAnnotatedClass(vmj.auth.model.core.RoleImpl.class);
        configuration.addAnnotatedClass(vmj.auth.model.core.UserRole.class);
        configuration.addAnnotatedClass(vmj.auth.model.core.UserRoleComponent.class);
        configuration.addAnnotatedClass(vmj.auth.model.core.UserRoleDecorator.class);
        configuration.addAnnotatedClass(vmj.auth.model.core.UserRoleImpl.class);
        configuration.addAnnotatedClass(vmj.auth.model.core.User.class);
        configuration.addAnnotatedClass(vmj.auth.model.core.UserComponent.class);
        configuration.addAnnotatedClass(vmj.auth.model.core.UserDecorator.class);
        configuration.addAnnotatedClass(vmj.auth.model.core.UserImpl.class);
        configuration.addAnnotatedClass(vmj.auth.model.passworded.UserImpl.class);

		configuration.addAnnotatedClass(paymentgateway.payment.ewallet.EWalletImpl.class);
		configuration.addAnnotatedClass(paymentgateway.payment.paymentlink.PaymentLinkImpl.class);
		configuration.addAnnotatedClass(paymentgateway.payment.virtualaccount.VirtualAccountImpl.class);
		configuration.addAnnotatedClass(aisco.program.core.Program.class);
		configuration.addAnnotatedClass(aisco.program.core.ProgramComponent.class);
		configuration.addAnnotatedClass(aisco.program.core.ProgramDecorator.class);
		configuration.addAnnotatedClass(aisco.program.core.ProgramImpl.class);
		configuration.addAnnotatedClass(aisco.program.activity.ProgramImpl.class);
		configuration.addAnnotatedClass(aisco.program.operational.ProgramImpl.class);
		configuration.addAnnotatedClass(aisco.program.goodsprogram.ProgramImpl.class);
		configuration.addAnnotatedClass(aisco.chartofaccount.core.ChartOfAccount.class);
		configuration.addAnnotatedClass(aisco.chartofaccount.core.ChartOfAccountComponent.class);
		configuration.addAnnotatedClass(aisco.chartofaccount.core.ChartOfAccountDecorator.class);
		configuration.addAnnotatedClass(aisco.chartofaccount.core.ChartOfAccountImpl.class);
		configuration.addAnnotatedClass(aisco.financialreport.core.FinancialReport.class);
		configuration.addAnnotatedClass(aisco.financialreport.core.FinancialReportComponent.class);
		configuration.addAnnotatedClass(aisco.financialreport.core.FinancialReportDecorator.class);
		configuration.addAnnotatedClass(aisco.financialreport.core.FinancialReportImpl.class);
		configuration.addAnnotatedClass(aisco.financialreport.income.FinancialReportImpl.class);
		configuration.addAnnotatedClass(aisco.financialreport.expense.FinancialReportImpl.class);
		configuration.addAnnotatedClass(aisco.automaticreport.periodic.AutomaticReportPeriodic.class);
		configuration.addAnnotatedClass(aisco.automaticreport.periodic.AutomaticReportPeriodicComponent.class);
		configuration.addAnnotatedClass(aisco.automaticreport.periodic.AutomaticReportPeriodicDecorator.class);
		configuration.addAnnotatedClass(aisco.automaticreport.periodic.AutomaticReportPeriodicImpl.class);
		configuration.addAnnotatedClass(aisco.donation.core.Donation.class);
		configuration.addAnnotatedClass(aisco.donation.core.DonationComponent.class);
		configuration.addAnnotatedClass(aisco.donation.core.DonationDecorator.class);
		configuration.addAnnotatedClass(aisco.donation.core.DonationImpl.class);
		configuration.addAnnotatedClass(aisco.donation.confirmation.DonationImpl.class);
		configuration.addAnnotatedClass(aisco.donation.goodsdonation.DonationImpl.class);

		configuration.buildMappings();
		HibernateUtil.buildSessionFactory(configuration);

		createObjectsAndBindEndPoints();
	}

	public static void activateServer(String hostName, int portNumber) {
		VMJServer vmjServer = VMJServer.getInstance(hostName, portNumber);
		try {
			vmjServer.startServerGeneric();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public static void createObjectsAndBindEndPoints() {
		System.out.println("== CREATING OBJECTS AND BINDING ENDPOINTS ==");
		UserResource userResource = UserResourceFactory
            .createUserResource("vmj.auth.model.core.UserResourceImpl"
			);

		RoleResource roleResource = RoleResourceFactory
        	.createRoleResource("vmj.auth.model.core.RoleResourceImpl"
			);
        
        UserResource userPasswordedResource = UserResourceFactory
	        .createUserResource("vmj.auth.model.passworded.UserResourceImpl"
			,
		    UserResourceFactory.createUserResource("vmj.auth.model.core.UserResourceImpl"));

		PaymentResource ewalletPaymentResource = PaymentResourceFactory
			.createPaymentResource("paymentgateway.payment.ewallet.PaymentResourceImpl"
			);
		
		PaymentResource paymentlinkPaymentResource = PaymentResourceFactory
			.createPaymentResource("paymentgateway.payment.paymentlink.PaymentResourceImpl"
			);
		
		PaymentResource virtualaccountPaymentResource = PaymentResourceFactory
			.createPaymentResource("paymentgateway.payment.virtualaccount.PaymentResourceImpl"
			);
		
		ProgramResource activityProgramResource = ProgramResourceFactory
			.createProgramResource("aisco.program.activity.ProgramResourceImpl"
			);
		
		ProgramResource operationalProgramResource = ProgramResourceFactory
			.createProgramResource("aisco.program.operational.ProgramResourceImpl"
			);
		
		ProgramResource goodsprogramProgramResource = ProgramResourceFactory
			.createProgramResource("aisco.program.goodsprogram.ProgramResourceImpl"
			);
		
		ChartOfAccountResource chartofaccountChartOfAccountResource = ChartOfAccountResourceFactory
			.createChartOfAccountResource("aisco.chartofaccount.core.ChartOfAccountResourceImpl"
			);
		
		FinancialReportResource financialreportFinancialReportResource = FinancialReportResourceFactory
			.createFinancialReportResource("aisco.financialreport.core.FinancialReportResourceImpl"
			);
		
		FinancialReportResource incomeFinancialReportResource = FinancialReportResourceFactory
			.createFinancialReportResource("aisco.financialreport.income.FinancialReportResourceImpl"
			,
			FinancialReportResourceFactory.createFinancialReportResource("aisco.financialreport.core.FinancialReportResourceImpl"));
		
		FinancialReportResource expenseFinancialReportResource = FinancialReportResourceFactory
			.createFinancialReportResource("aisco.financialreport.expense.FinancialReportResourceImpl"
			,
			FinancialReportResourceFactory.createFinancialReportResource("aisco.financialreport.core.FinancialReportResourceImpl"));
		
		AutomaticReportResource automaticreportAutomaticReportResource = AutomaticReportResourceFactory
			.createAutomaticReportResource("aisco.automaticreport.core.AutomaticReportResourceImpl"
			);
		
		AutomaticReportResource twolevelAutomaticReportResource = AutomaticReportResourceFactory
			.createAutomaticReportResource("aisco.automaticreport.twolevel.AutomaticReportResourceImpl"
			,
			AutomaticReportResourceFactory.createAutomaticReportResource("aisco.automaticreport.core.AutomaticReportResourceImpl"));
		
		AutomaticReportResource periodicAutomaticReportResource = AutomaticReportResourceFactory
			.createAutomaticReportResource("aisco.automaticreport.periodic.AutomaticReportResourceImpl"
			,
			AutomaticReportResourceFactory.createAutomaticReportResource("aisco.automaticreport.core.AutomaticReportResourceImpl"));
		
		DonationResource donationDonationResource = DonationResourceFactory
			.createDonationResource("aisco.donation.core.DonationResourceImpl"
			);
		
		DonationResource confirmationDonationResource = DonationResourceFactory
			.createDonationResource("aisco.donation.confirmation.DonationResourceImpl"
			,
			DonationResourceFactory.createDonationResource("aisco.donation.core.DonationResourceImpl"));
		
		DonationResource goodsdonationDonationResource = DonationResourceFactory
			.createDonationResource("aisco.donation.goodsdonation.DonationResourceImpl"
			,
			DonationResourceFactory.createDonationResource("aisco.donation.core.DonationResourceImpl"));
		

		System.out.println("goodsdonationDonationResource endpoints binding");
		Router.route(goodsdonationDonationResource);
		
		System.out.println("confirmationDonationResource endpoints binding");
		Router.route(confirmationDonationResource);
		
		System.out.println("donationDonationResource endpoints binding");
		Router.route(donationDonationResource);
		
		System.out.println("periodicAutomaticReportResource endpoints binding");
		Router.route(periodicAutomaticReportResource);
		
		System.out.println("twolevelAutomaticReportResource endpoints binding");
		Router.route(twolevelAutomaticReportResource);
		
		System.out.println("automaticreportAutomaticReportResource endpoints binding");
		Router.route(automaticreportAutomaticReportResource);
		
		System.out.println("expenseFinancialReportResource endpoints binding");
		Router.route(expenseFinancialReportResource);
		
		System.out.println("incomeFinancialReportResource endpoints binding");
		Router.route(incomeFinancialReportResource);
		
		System.out.println("financialreportFinancialReportResource endpoints binding");
		Router.route(financialreportFinancialReportResource);
		
		System.out.println("chartofaccountChartOfAccountResource endpoints binding");
		Router.route(chartofaccountChartOfAccountResource);
		
		System.out.println("goodsprogramProgramResource endpoints binding");
		Router.route(goodsprogramProgramResource);
		
		System.out.println("operationalProgramResource endpoints binding");
		Router.route(operationalProgramResource);
		
		System.out.println("activityProgramResource endpoints binding");
		Router.route(activityProgramResource);
		
		System.out.println("virtualaccountPaymentResource endpoints binding");
		Router.route(virtualaccountPaymentResource);
		
		System.out.println("paymentlinkPaymentResource endpoints binding");
		Router.route(paymentlinkPaymentResource);
		
		System.out.println("ewalletPaymentResource endpoints binding");
		Router.route(ewalletPaymentResource);
		
		System.out.println("authResource endpoints binding");
		Router.route(userPasswordedResource);
		Router.route(roleResource);
		Router.route(userResource);
	}

	public static void setDBProperties(String varname, String typeProp, Configuration configuration) {
		String varNameValue = System.getenv(varname);
		String propertyName = String.format("hibernate.connection.%s",typeProp);
		if (varNameValue != null) {
			configuration.setProperty(propertyName, varNameValue);
		} else {
			String hibernatePropertyVal = configuration.getProperty(propertyName);
			if (hibernatePropertyVal == null) {
				String error_message = String.format("Please check '%s' in your local environment variable or "
                	+ "'hibernate.connection.%s' in your 'hibernate.properties' file!", varname, typeProp);
            	System.out.println(error_message);
			}
		}
	}

	// if the env variable for server host is null, use localhost instead.
    public static String getEnvVariableHostAddress(String varname_host){
            String hostAddress = System.getenv(varname_host)  != null ? System.getenv(varname_host) : "localhost"; // Host
            return hostAddress;
    }

    // try if the environment variable for port number is null, use 7776 instead
    public static int getEnvVariablePortNumber(String varname_port){
            String portNum = System.getenv(varname_port)  != null? System.getenv(varname_port)  : "7776"; //PORT
            int portNumInt = Integer.parseInt(portNum);
            return portNumInt;
    }

}
