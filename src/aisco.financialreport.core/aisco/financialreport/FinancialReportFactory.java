package aisco.financialreport;
import aisco.financialreport.core.FinancialReport;

import java.lang.reflect.Constructor;
import java.util.logging.Logger;



public class FinancialReportFactory {
    private static final Logger LOGGER = Logger.getLogger(FinancialReportFactory.class.getName());

    private FinancialReportFactory()
    {

    }

    /** initiate features **/
    public static FinancialReport createFinancialReport(String fullyQualifiedName, Object... base)
    {   
        FinancialReport record = null;
        if(true)
        {
            try {
                Class<?> clz = Class.forName(fullyQualifiedName);
                Constructor<?>[] constructorList = clz.getDeclaredConstructors();
                Constructor<?> constructor = null;
                for (int i = 0; i < constructorList.length; i++) {
                    try {
                        constructor = constructorList[i];
                        System.out.println(constructor.toString());
                        record = (FinancialReport) constructor.newInstance(base);
                        i = constructorList.length;
                    } catch (IllegalArgumentException e) {
                        if (i < constructorList.length - 1) {
                            System.out.println("Trying other constructor");
                            continue;
                        } else {
                            throw e;
                        }
                    }
                }
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
       if (fqn.equals("aisco.financialreport.incomewithfrequency.FinancialReportImpl"))
        {
           String baseku = base.getClass().getCanonicalName();
           a = baseku.equals("aisco.financialreport.income.FinancialReportImpl");
        }
        else if (fqn.equals("aisco.financialreport.expensewithfrequency.FinancialReportImpl"))
        {
           String baseku = base.getClass().getCanonicalName();
           a = baseku.equals("aisco.financialreport.expense.FinancialReportImpl");
        }
        return a;
    }
}
