package aisco.automaticreport.activityreport;

import java.lang.reflect.Constructor;
import java.util.logging.Logger;

public class AutomaticReportActivityReportFactory {
    private static final Logger LOGGER = Logger.getLogger(AutomaticReportActivityReportFactory.class.getName());

    public AutomaticReportActivityReportFactory() { }

    public static AutomaticReportActivityReport createAutomaticReportActivityReport(String fullyQualifiedName, 
        Object ... base)
    {
        AutomaticReportActivityReport record = null;
        try {
            Class<?> clz = Class.forName(fullyQualifiedName);
                Constructor<?>[] constructorList = clz.getDeclaredConstructors();
                Constructor<?> constructor = null;
                for (int i = 0; i < constructorList.length; i++) {
                    try {
                        constructor = constructorList[i];
                        System.out.println(constructor.toString());
                        record = (AutomaticReportActivityReport) constructor.newInstance(base);
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
            LOGGER.severe("Failed to create instance of AutomaticReportActivityReport.");
            LOGGER.severe("Given FQN: " + fullyQualifiedName);
            LOGGER.severe("Failed to run: Check your constructor argument");
            LOGGER.severe(e.getMessage());
            System.exit(20);
        }
        catch (ClassCastException e)
        {   LOGGER.severe("Failed to create instance of AutomaticReportActivityReport.");
            LOGGER.severe("Given FQN: " + fullyQualifiedName);
            LOGGER.severe("Failed to cast the object");
            System.exit(30);
        }
        catch (ClassNotFoundException e)
        {
            LOGGER.severe("Failed to create instance of AutomaticReportActivityReport.");
            LOGGER.severe("Given FQN: " + fullyQualifiedName);
            LOGGER.severe("Decorator can't be applied to the object");
            System.exit(40);
        }
        catch (Exception e)
        {
            LOGGER.severe("Failed to create instance of AutomaticReportActivityReport.");
            LOGGER.severe("Given FQN: " + fullyQualifiedName);
            System.exit(50);
        }
        return record;
    }

}
