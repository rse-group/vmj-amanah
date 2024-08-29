package auth.management;

import java.lang.reflect.Constructor;
import java.util.logging.Logger;

import auth.management.core.UserRoleResource;

public class UserRoleResourceFactory {
    private static final Logger LOGGER = Logger.getLogger(UserRoleResourceFactory.class.getName());

    private UserRoleResourceFactory()
    {

    }

    /** initiate features **/
    public static UserRoleResource createUserRoleResource(String fullyQualifiedName, Object... base)
    {
        UserRoleResource record = null;
        if (true)
        {
            try {
                Class<?> clz = Class.forName(fullyQualifiedName);
                Constructor<?> constructor = clz.getDeclaredConstructors()[0];
                System.out.println(constructor.toString());
                record = (UserRoleResource) constructor.newInstance(base);
            } 
            catch (IllegalArgumentException e)
            {
                LOGGER.severe("Failed to create instance of User Role.");
                LOGGER.severe("Given FQN: " + fullyQualifiedName);
                LOGGER.severe("Failed to run: Check your constructor argument");
                System.out.println(e.getMessage());
                System.exit(20);
            }
            catch (ClassCastException e)
            {   LOGGER.severe("Failed to create instance of User Role.");
                LOGGER.severe("Given FQN: " + fullyQualifiedName);
                LOGGER.severe("Failed to cast the object");
                System.exit(30);
            }
            catch (ClassNotFoundException e)
            {
                LOGGER.severe("Failed to create instance of User Role.");
                LOGGER.severe("Given FQN: " + fullyQualifiedName);
                LOGGER.severe("Class not Found");
                System.exit(40);
            }
            catch (Exception e)
            {
                System.out.println(e);
                LOGGER.severe("Failed to create instance of User Role.");
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
