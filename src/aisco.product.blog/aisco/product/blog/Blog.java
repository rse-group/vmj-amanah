package aisco.product.blog;

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
import aisco.beneficiary.BeneficiaryResourceFactory;
import aisco.beneficiary.core.BeneficiaryResource;

public class Blog {

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

		configuration.addAnnotatedClass(aisco.program.core.Program.class);
		configuration.addAnnotatedClass(aisco.program.core.ProgramComponent.class);
		configuration.addAnnotatedClass(aisco.program.core.ProgramDecorator.class);
		configuration.addAnnotatedClass(aisco.program.core.ProgramImpl.class);
		configuration.addAnnotatedClass(aisco.program.activity.ProgramImpl.class);
		configuration.addAnnotatedClass(aisco.chartofaccount.core.ChartOfAccount.class);
		configuration.addAnnotatedClass(aisco.chartofaccount.core.ChartOfAccountComponent.class);
		configuration.addAnnotatedClass(aisco.chartofaccount.core.ChartOfAccountDecorator.class);
		configuration.addAnnotatedClass(aisco.chartofaccount.core.ChartOfAccountImpl.class);
		configuration.addAnnotatedClass(aisco.financialreport.core.FinancialReport.class);
		configuration.addAnnotatedClass(aisco.financialreport.core.FinancialReportComponent.class);
		configuration.addAnnotatedClass(aisco.financialreport.core.FinancialReportDecorator.class);
		configuration.addAnnotatedClass(aisco.financialreport.core.FinancialReportImpl.class);
		configuration.addAnnotatedClass(aisco.financialreport.income.FinancialReportImpl.class);
		configuration.addAnnotatedClass(aisco.beneficiary.core.Beneficiary.class);
		configuration.addAnnotatedClass(aisco.beneficiary.core.BeneficiaryComponent.class);
		configuration.addAnnotatedClass(aisco.beneficiary.core.BeneficiaryDecorator.class);
		configuration.addAnnotatedClass(aisco.beneficiary.core.BeneficiaryImpl.class);
		configuration.addAnnotatedClass(aisco.beneficiary.pesertadidik.BeneficiaryImpl.class);

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

		ProgramResource activityProgramResource = ProgramResourceFactory
			.createProgramResource("aisco.program.activity.ProgramResourceImpl"
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
		
		BeneficiaryResource beneficiaryBeneficiaryResource = BeneficiaryResourceFactory
			.createBeneficiaryResource("aisco.beneficiary.core.BeneficiaryResourceImpl"
			);
		
		BeneficiaryResource pesertadidikBeneficiaryResource = BeneficiaryResourceFactory
			.createBeneficiaryResource("aisco.beneficiary.pesertadidik.BeneficiaryResourceImpl"
			,
			BeneficiaryResourceFactory.createBeneficiaryResource("aisco.beneficiary.core.BeneficiaryResourceImpl"));
		

		System.out.println("pesertadidikBeneficiaryResource endpoints binding");
		Router.route(pesertadidikBeneficiaryResource);
		
		System.out.println("beneficiaryBeneficiaryResource endpoints binding");
		Router.route(beneficiaryBeneficiaryResource);
		
		System.out.println("incomeFinancialReportResource endpoints binding");
		Router.route(incomeFinancialReportResource);
		
		System.out.println("financialreportFinancialReportResource endpoints binding");
		Router.route(financialreportFinancialReportResource);
		
		System.out.println("chartofaccountChartOfAccountResource endpoints binding");
		Router.route(chartofaccountChartOfAccountResource);
		
		System.out.println("activityProgramResource endpoints binding");
		Router.route(activityProgramResource);
		
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
