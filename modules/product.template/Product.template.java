package {{product_qualifier}};

import java.util.ArrayList;

import vmj.routing.route.VMJServer;
import vmj.routing.route.Router;

import aisco.program.ProgramFactory;
import aisco.program.core.Program;

import aisco.financialreport.FinancialReportFactory;
import aisco.financialreport.core.FinancialReport;

import aisco.chartofaccount.ChartOfAccountFactory;
import aisco.chartofaccount.core.ChartOfAccount;

import aisco.dashboard.DashboardFactory;
import aisco.dashboard.core.Dashboard;
import aisco.dashboard.expense.DashboardImpl;

import aisco.donation.DonationFactory;
import aisco.donation.core.Donation;

import blog.page.BlogFactory;
import blog.page.core.Blog;

import vmj.auth.model.UserFactory;
import vmj.auth.model.RoleFactory;
import vmj.auth.model.core.User;
import vmj.auth.model.core.Role;

public class {{product_name}} {
	public static void main(String[] args) {
		generateTables();
		activateServer("localhost", {{ports.backend}});
		generateCRUDEndpoints();
		createObjectsAndBindEndPoints();
	}

	public static void generateTables() {
		try {
			System.out.println("== GENERATING TABLES ==");
			VMJDatabaseMapper.generateTable("aisco.program.core.ProgramComponent", false);
			VMJDatabaseMapper.generateTable("aisco.program.activity.ProgramImpl", true);
			VMJDatabaseMapper.generateTable("aisco.program.operational.ProgramImpl", true);
			VMJDatabaseMapper.generateTable("aisco.chartofaccount.core.ChartOfAccountImpl", false);

			VMJDatabaseMapper.generateTable("vmj.auth.model.passworded.UserImpl", false);
			VMJDatabaseMapper.generateTable("vmj.auth.model.core.RoleImpl", false);
			VMJDatabaseMapper.generateTable("vmj.auth.model.core.UserRoleImpl", false);

			{% for feature in features %}
			{{vmj_mapping.get(feature, {}).get("table")}}
			{% endfor %}
			System.out.println();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Skipping generate tables...");
		} catch (Error e) {
			e.printStackTrace();
			System.out.println("Skipping generate tables...");
		}
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

	public static void generateCRUDEndpoints() {
		System.out.println("== CRUD ENDPOINTS ==");
		VMJServer vmjServer = VMJServer.getInstance();
		/**
		 * PROGRAMS
		 */

		vmjServer.createTableCRUDEndpoint("programs", "program_core", "aisco.program.core.ProgramComponent",
				VMJDatabaseMapper.getTableColumnsNames("aisco.program.core.ProgramComponent", false));
		vmjServer.createTableCRUDEndpoint("activities", "program_delta_activity", "aisco.program.activity.ProgramImpl",
				VMJDatabaseMapper.getTableColumnsNames("aisco.program.activity.ProgramImpl", true));
		vmjServer.createTableCRUDEndpoint("operationals", "program_delta_operational", "aisco.program.operational.ProgramImpl",
				VMJDatabaseMapper.getTableColumnsNames("aisco.program.operational.ProgramImpl", true));

		/**
		 * FINANCIAL REPORTS
		 */
		vmjServer.createTableCRUDEndpoint("financialreports", "financialreport_core", "aisco.financialreport.core.FinancialReportImpl",
				VMJDatabaseMapper.getTableColumnsNames("aisco.financialreport.core.FinancialReportImpl", false));

		/**
		 * AUTH BASE MODELS
		 */
		vmjServer.createTableCRUDEndpoint("users", "auth_user", "vmj.auth.model.core.UserImpl",
				VMJDatabaseMapper.getTableColumnsNames("vmj.auth.model.core.UserImpl", false));
		vmjServer.createTableCRUDEndpoint("users", "auth_user_passworded", "vmj.auth.model.passworded.UserImpl",
				VMJDatabaseMapper.getTableColumnsNames("vmj.auth.model.passworded.UserImpl", true));
		vmjServer.createTableCRUDEndpoint("roles", "auth_role", "vmj.auth.model.core.RoleImpl",
				VMJDatabaseMapper.getTableColumnsNames("vmj.auth.model.core.RoleImpl", false));
		vmjServer.createTableCRUDEndpoint("user-roles", "auth_user_role", "vmj.auth.model.core.UserRoleImpl",
				VMJDatabaseMapper.getTableColumnsNames("vmj.auth.model.core.UserRoleImpl", false));

		System.out.println();

		/**
		 * GENERATED VMJ CRUDS
		 */
		{% for feature in features %}
		{{vmj_mapping.get(feature, {}).get("vmj_crud")}}
		{% endfor %}

		/**
		 * PROGRAMS ABS
		 */
		vmjServer.createABSCRUDEndpoint("program", "program_core", "aisco.program.core.ProgramComponent",
				VMJDatabaseMapper.getTableColumnsNames("aisco.program.core.ProgramComponent", false));

		/**
		 * FINANCIAL REPORTS ABS
		 */
		vmjServer.createABSCRUDEndpoint("expense", "financialreport_core", "aisco.financialreport.core.FinancialReportImpl",
				VMJDatabaseMapper.getTableColumnsNames("aisco.financialreport.core.FinancialReportImpl", false));

		/**
		 * Chart of Account ABS
		 */
		 vmjServer.createABSCRUDEndpoint("chart-of-account", "chartofaccount_core", "aisco.chartofaccount.core.ChartOfAccountImpl",
				VMJDatabaseMapper.getTableColumnsNames("aisco.chartofaccount.core.ChartOfAccountImpl", false));

		/**
		 * AUTH BASE MODELS ABS
		 */
		vmjServer.createTableCRUDEndpoint("users", "auth_user", "vmj.auth.model.core.UserImpl",
				VMJDatabaseMapper.getTableColumnsNames("vmj.auth.model.core.UserImpl", false));
		vmjServer.createTableCRUDEndpoint("users", "auth_user_passworded", "vmj.auth.model.passworded.UserImpl",
				VMJDatabaseMapper.getTableColumnsNames("vmj.auth.model.passworded.UserImpl", true));
		vmjServer.createTableCRUDEndpoint("roles", "auth_role", "vmj.auth.model.core.RoleImpl",
				VMJDatabaseMapper.getTableColumnsNames("vmj.auth.model.core.RoleImpl", false));
		vmjServer.createTableCRUDEndpoint("user-roles", "auth_user_role", "vmj.auth.model.core.UserRoleImpl",
				VMJDatabaseMapper.getTableColumnsNames("vmj.auth.model.core.UserRoleImpl", false));

		System.out.println();

		/**
		 * GENERATED ABS CRUDS
		 */
		{% for feature in features %}
		{{vmj_mapping.get(feature, {}).get("abs_crud")}}
		{% endfor %}
	}

	public static void createObjectsAndBindEndPoints() {
		System.out.println("== CREATING OBJECTS AND BINDING ENDPOINTS ==");
		Program activity = ProgramFactory.createProgram("aisco.program.activity.ProgramImpl");
		Program operational = ProgramFactory.createProgram("aisco.program.operational.ProgramImpl");
		FinancialReport reportCore = FinancialReportFactory
				.createFinancialReport("aisco.financialreport.core.FinancialReportImpl");

		Donation donation = DonationFactory.createDonation("aisco.donation.pgateway.DonationImpl");

		Dashboard dashboardExpense = DashboardFactory.createDashboard("aisco.dashboard.expense.DashboardImpl",
				DashboardFactory.createDashboard("aisco.dashboard.core.DashboardImpl"));

		Blog standardBlog = BlogFactory.createBlog("blog.page.comment.BlogImpl",
				BlogFactory.createBlog("blog.page.core.BlogImpl"));
		Blog shareBlog = BlogFactory.createBlog("blog.page.share.BlogImpl",
				BlogFactory.createBlog("blog.page.core.BlogImpl"));

		User user = UserFactory.createUser("vmj.auth.model.core.UserImpl");
		User passwordedUser = UserFactory.createUser("vmj.auth.model.passworded.UserImpl", user);
		Role role = RoleFactory.createRole("vmj.auth.model.core.RoleImpl");

		{% for feature in features %}
		{{vmj_mapping.get(feature, {}).get("delta_instance")}}
		{% endfor %}

		System.out.println("Activity endpoints binding");
		Router.bindMethod("setExecutionDate", activity);
		System.out.println();

		System.out.println("Report endpoints binding");
		Router.bindMethod("printHeader", reportCore);
		Router.bindMethod("getDescription", reportCore);
		Router.bindMethod("getAmount", reportCore);
		Router.bindMethod("getProgram", reportCore);
		System.out.println();

		System.out.println("Donation Endpoints binding");
		Router.bindMethod("setTransactionStatus", donation);
		Router.bindMethod("pingKirimDoku", donation);
		Router.bindMethod("inquiryKirimDoku", donation);
		System.out.println();

		System.out.println("Dashboard Endpoints binding");
		Router.bindMethod("getPartnerDatas", dashboardExpense);
		Router.bindMethod("totalExpenseByProgramId", dashboardExpense);
		Router.bindMethod("totalExpense", dashboardExpense);
		Router.bindMethod("printDashboard", dashboardExpense);
		Router.bindMethod("totalIncome", dashboardExpense);
		Router.bindMethod("totalIncomeByProgramId", dashboardExpense);
		System.out.println();

		System.out.println("Standard Blog binding");
		Router.bindMethod("createPost", shareBlog);
		Router.bindMethod("getPosts", standardBlog);
		System.out.println();

		System.out.println("comment feature");
		Router.bindMethod("addComments", standardBlog);
		Router.bindMethod("getComments", standardBlog);
		System.out.println();

		System.out.println("share feature");
		Router.bindMethod("getPost", shareBlog);
		Router.bindMethod("addPostToShareFeature", shareBlog);
		System.out.println();

		System.out.println("Passworded User binding");
		Router.bindMethod("login", passwordedUser);
		Router.bindMethod("register", passwordedUser);
		Router.bindMethod("forgotPassword", passwordedUser);
		Router.bindMethod("getForgotPasswordToken", passwordedUser);
		Router.bindMethod("changePassword", passwordedUser);

		System.out.println("Permission mangement");
		Router.bindMethod("changePermissions", user);
		Router.bindMethod("changePermissions", role);
		System.out.println();

		{% for feature in features %}
		{{vmj_mapping.get(feature, {}).get("route")}}
		{% endfor %}
	}

}
