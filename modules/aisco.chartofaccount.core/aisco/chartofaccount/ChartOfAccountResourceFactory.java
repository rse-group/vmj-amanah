package aisco.chartofaccount;

import aisco.chartofaccount.core.ChartOfAccountResource;
import java.lang.reflect.Constructor;
import java.util.logging.Logger;

public class ChartOfAccountResourceFactory{
    private static final Logger LOGGER = Logger.getLogger(ChartOfAccountResourceFactory.class.getName());

    public ChartOfAccountResourceFactory()
    {

    }

    public static ChartOfAccountResource createChartOfAccountResource(String fullyQualifiedName, Object ... base)
    {
        ChartOfAccountResource record = null;
        try {
            Class<?> clz = Class.forName(fullyQualifiedName);
            Constructor<?> constructor = clz.getDeclaredConstructors()[0];
            record = (ChartOfAccountResource) constructor.newInstance(base);
        } 
        catch (IllegalArgumentException e)
        {
            LOGGER.severe("Failed to create instance of ChartOfAccountResource.");
            LOGGER.severe("Given FQN: " + fullyQualifiedName);
            LOGGER.severe("Failed to run: Check your constructor argument");
            LOGGER.severe(e.getMessage());
            System.exit(20);
        }
        catch (ClassCastException e)
        {   LOGGER.severe("Failed to create instance of ChartOfAccountResource.");
            LOGGER.severe("Given FQN: " + fullyQualifiedName);
            LOGGER.severe("Failed to cast the object");
            System.exit(30);
        }
        catch (ClassNotFoundException e)
        {
            LOGGER.severe("Failed to create instance of ChartOfAccountResource.");
            LOGGER.severe("Given FQN: " + fullyQualifiedName);
            LOGGER.severe("Decorator can't be applied to the object");
            System.exit(40);
        }
        catch (Exception e)
        {
            LOGGER.severe("Failed to create instance of ChartOfAccountResource.");
            LOGGER.severe("Given FQN: " + fullyQualifiedName);
            System.exit(50);
        }
        return record;
    }

}