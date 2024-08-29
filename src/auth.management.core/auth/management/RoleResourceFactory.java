package auth.management;

import java.lang.reflect.Constructor;
import java.util.logging.Logger;

import auth.management.core.RoleResource;

public class RoleResourceFactory {
    private static final Logger LOGGER = Logger.getLogger(RoleResourceFactory.class.getName());

    private RoleResourceFactory()
    {

    }

    /** initiate features **/
    public static RoleResource createRoleResource(String fullyQualifiedName, Object... base)
    {
        RoleResource record = null;
        if (true)
        {
            try {
                Class<?> clz = Class.forName(fullyQualifiedName);
                Constructor<?> constructor = clz.getDeclaredConstructors()[0];
                System.out.println(constructor.toString());
                record = (RoleResource) constructor.newInstance(base);
            } 
            catch (IllegalArgumentException e)
            {
                LOGGER.severe("Failed to create instance of Role.");
                LOGGER.severe("Given FQN: " + fullyQualifiedName);
                LOGGER.severe("Failed to run: Check your constructor argument");
                System.out.println(e.getMessage());
                System.exit(20);
            }
            catch (ClassCastException e)
            {   LOGGER.severe("Failed to create instance of Role.");
                LOGGER.severe("Given FQN: " + fullyQualifiedName);
                LOGGER.severe("Failed to cast the object");
                System.exit(30);
            }
            catch (ClassNotFoundException e)
            {
                LOGGER.severe("Failed to create instance of Role.");
                LOGGER.severe("Given FQN: " + fullyQualifiedName);
                LOGGER.severe("Class not Found");
                System.exit(40);
            }
            catch (Exception e)
            {
                System.out.println(e);
                LOGGER.severe("Failed to create instance of Role.");
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
}
