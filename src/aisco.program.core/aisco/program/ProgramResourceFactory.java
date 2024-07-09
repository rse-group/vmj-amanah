package aisco.program;

import aisco.program.core.ProgramResource;
import java.lang.reflect.Constructor;
import java.util.logging.Logger;

public class ProgramResourceFactory{
    private static final Logger LOGGER = Logger.getLogger(ProgramResourceFactory.class.getName());

    public ProgramResourceFactory()
    {

    }

    public static ProgramResource createProgramResource(String fullyQualifiedName, Object ... base)
    {
    	System.out.println("=======ProgramResourceFactory=========");
    	System.out.println(fullyQualifiedName);
    	System.out.println("Base:");
        for (Object obj : base) {
            System.out.println(obj);
        }
    	System.out.println("======================================");
        ProgramResource record = null;
        try {
            Class<?> clz = Class.forName(fullyQualifiedName);
            Constructor<?> constructor = clz.getDeclaredConstructors()[0];
            record = (ProgramResource) constructor.newInstance(base);
        } 
        catch (IllegalArgumentException e)
        {
            LOGGER.severe("Failed to create instance of ProgramResource.");
            LOGGER.severe("Given FQN: " + fullyQualifiedName);
            LOGGER.severe("Failed to run: Check your constructor argument");
            LOGGER.severe(e.getMessage());
            System.exit(20);
        }
        catch (ClassCastException e)
        {   LOGGER.severe("Failed to create instance of ProgramResource.");
            LOGGER.severe("Given FQN: " + fullyQualifiedName);
            LOGGER.severe("Failed to cast the object");
            System.exit(30);
        }
        catch (ClassNotFoundException e)
        {
            LOGGER.severe("Failed to create instance of ProgramResource.");
            LOGGER.severe("Given FQN: " + fullyQualifiedName);
            LOGGER.severe("Decorator can't be applied to the object");
            System.exit(40);
        }
        catch (Exception e)
        {
            LOGGER.severe("Failed to create instance of ProgramResource.");
            LOGGER.severe("Given FQN: " + fullyQualifiedName);
            System.exit(50);
        }
        return record;
    }

}