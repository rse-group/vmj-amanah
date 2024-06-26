package aisco.product.yayasanmaida;

import java.util.ArrayList;

import vmj.routing.route.VMJServer;
import vmj.routing.route.Router;
import vmj.hibernate.integrator.HibernateUtil;
import org.hibernate.cfg.Configuration;

import aisco.program.ProgramResourceFactory;
import aisco.program.core.ProgramResource;
import aisco.chartofaccount.ChartOfAccountResourceFactory;
import aisco.chartofaccount.core.ChartOfAccountResource;
import aisco.financialreport.FinancialReportResourceFactory;
import aisco.financialreport.core.FinancialReportResource;
import aisco.programreport.ProgramReportResourceFactory;
import aisco.programreport.core.ProgramReportResource;

import prices.auth.vmj.model.UserResourceFactory;
import prices.auth.vmj.model.RoleResourceFactory;
import prices.auth.vmj.model.core.UserResource;
import prices.auth.vmj.model.core.RoleResource;


public class YayasanMaida {

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
		configuration.addAnnotatedClass(aisco.programreport.core.ProgramReport.class);
		configuration.addAnnotatedClass(aisco.programreport.core.ProgramReportComponent.class);
		configuration.addAnnotatedClass(aisco.programreport.core.ProgramReportDecorator.class);
		configuration.addAnnotatedClass(aisco.programreport.core.ProgramReportImpl.class);
		
		configuration.addAnnotatedClass(prices.auth.vmj.model.core.Role.class);
        configuration.addAnnotatedClass(prices.auth.vmj.model.core.RoleComponent.class);
        configuration.addAnnotatedClass(prices.auth.vmj.model.core.RoleImpl.class);
        
        configuration.addAnnotatedClass(prices.auth.vmj.model.core.UserRole.class);
        configuration.addAnnotatedClass(prices.auth.vmj.model.core.UserRoleComponent.class);
        configuration.addAnnotatedClass(prices.auth.vmj.model.core.UserRoleImpl.class);

        configuration.addAnnotatedClass(prices.auth.vmj.model.core.User.class);
        configuration.addAnnotatedClass(prices.auth.vmj.model.core.UserComponent.class);
        configuration.addAnnotatedClass(prices.auth.vmj.model.core.UserDecorator.class);
        configuration.addAnnotatedClass(prices.auth.vmj.model.core.UserImpl.class);
        configuration.addAnnotatedClass(prices.auth.vmj.model.passworded.UserPasswordedImpl.class);
        configuration.addAnnotatedClass(prices.auth.vmj.model.social.UserSocialImpl.class);
		
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
		ProgramResource activity = ProgramResourceFactory
			.createProgramResource(
			"aisco.program.activity.ProgramResourceImpl"
			);
		ChartOfAccountResource chartofaccount = ChartOfAccountResourceFactory
			.createChartOfAccountResource(
			"aisco.chartofaccount.core.ChartOfAccountResourceImpl"
			);
		FinancialReportResource financialreport = FinancialReportResourceFactory
			.createFinancialReportResource(
			"aisco.financialreport.core.FinancialReportResourceImpl"
			);
		FinancialReportResource income = FinancialReportResourceFactory
			.createFinancialReportResource(
			"aisco.financialreport.income.FinancialReportResourceImpl"
			,
			FinancialReportResourceFactory.createFinancialReportResource(
			"aisco.financialreport.core.FinancialReportResourceImpl"));
		ProgramReportResource programreport = ProgramReportResourceFactory
			.createProgramReportResource(
			"aisco.programreport.core.ProgramReportResourceImpl"
			);
		
		UserResource userCore = UserResourceFactory
                .createUserResource("prices.auth.vmj.model.core.UserResourceImpl");
        UserResource userPassworded = UserResourceFactory
	        .createUserResource("prices.auth.vmj.model.passworded.UserPasswordedResourceDecorator",
		        UserResourceFactory
		        	.createUserResource("prices.auth.vmj.model.core.UserResourceImpl"));
        UserResource userSocial = UserResourceFactory
        	.createUserResource("prices.auth.vmj.model.social.UserSocialResourceDecorator",
        		userPassworded);        
        RoleResource role = RoleResourceFactory
        	.createRoleResource("prices.auth.vmj.model.core.RoleResourceImpl");

		System.out.println("programreport endpoints binding");
		Router.route(programreport);
		
		System.out.println("income endpoints binding");
		Router.route(income);
		
		System.out.println("financialreport endpoints binding");
		Router.route(financialreport);
		
		System.out.println("chartofaccount endpoints binding");
		Router.route(chartofaccount);
		
		System.out.println("activity endpoints binding");
		Router.route(activity);
		
		
		System.out.println("auth endpoints binding");
		Router.route(userCore);
		Router.route(userPassworded);
		Router.route(userSocial);
		Router.route(role);
		System.out.println();
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