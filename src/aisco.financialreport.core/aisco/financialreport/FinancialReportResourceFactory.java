package aisco.financialreport;
import aisco.financialreport.core.FinancialReportResource;

import java.lang.reflect.Constructor;
import java.util.logging.Logger;



public class FinancialReportResourceFactory {
    private static final Logger LOGGER = Logger.getLogger(FinancialReportResourceFactory.class.getName());

    private FinancialReportResourceFactory()
    {

    }

    /** initiate features **/
    public static FinancialReportResource createFinancialReportResource(String fullyQualifiedName, Object... base)
    {   
        FinancialReportResource record = null;
        if(true)
        {
            try {
                Class<?> clz = Class.forName(fullyQualifiedName);
                Constructor<?> constructor = clz.getDeclaredConstructors()[0];
                System.out.println(constructor.toString());
                record = (FinancialReportResource) constructor.newInstance(base);
            } 
            catch (IllegalArgumentException e)
            {
                LOGGER.severe("Failed to create instance of Financial Report.");
                LOGGER.severe("Given FQN: " + fullyQualifiedName);
                LOGGER.severe("Failed to run: Check your constructor argument");
                System.out.println(e.getMessage());
                System.exit(20);
            }
            catch (ClassCastException e)
            {   LOGGER.severe("Failed to create instance of Financial Report.");
                LOGGER.severe("Given FQN: " + fullyQualifiedName);
                LOGGER.severe("Failed to cast the object");
                System.exit(30);
            }
            catch (ClassNotFoundException e)
            {
                LOGGER.severe("Failed to create instance of Financial Report.");
                LOGGER.severe("Given FQN: " + fullyQualifiedName);
                LOGGER.severe("Class not Found");
                System.exit(40);
            }
            catch (Exception e)
            {
                System.out.println(e);
                LOGGER.severe("Failed to create instance of Financial Report.");
                LOGGER.severe("Given FQN: " + fullyQualifiedName);
                System.exit(50);
            }
        }
        else
        {
            System.out.println("Config Fail");
            System.exit(10);
        }
        return record;
    }

    public static boolean checkConfig(String fqn, Object base)
    {
       boolean a = true;
       if (fqn.equals("aisco.financialreport.incomewithfrequency.FinancialReportResourceImpl"))
        {
           String baseku = base.getClass().getCanonicalName();
           a = baseku.equals("aisco.financialreport.income.FinancialReportResourceImpl");
        }
        else if (fqn.equals("aisco.financialreport.expensewithfrequency.FinancialReportResourceImpl"))
        {
           String baseku = base.getClass().getCanonicalName();
           a = baseku.equals("aisco.financialreport.expense.FinancialReportResourceImpl");
        }
        return a;
    }
}
