package aisco.beneficiary;

import aisco.beneficiary.core.BeneficiaryResource;
import java.lang.reflect.Constructor;
import java.util.logging.Logger;

public class BeneficiaryResourceFactory{
    private static final Logger LOGGER = Logger.getLogger(BeneficiaryFactory.class.getName());

    public BeneficiaryResourceFactory()
    {

    }

    public static BeneficiaryResource createBeneficiaryResource(String fullyQualifiedName, Object ... base)
    {
        BeneficiaryResource record = null;
        try {
            Class<?> clz = Class.forName(fullyQualifiedName);
            Constructor<?>[] constructorList = clz.getDeclaredConstructors();
            Constructor<?> constructor = null;
            for (int i = 0; i < constructorList.length; i++) {
                try {
                    constructor = constructorList[i];
                    System.out.println(constructor.toString());
                    record = (BeneficiaryResource) constructor.newInstance(base);
                    break;
                } catch(IllegalArgumentException e) {
                    if (i < constructorList.length - 1) {
                        continue;
                    } else {
                        throw e;
                    }
                }
            }
        } 
        catch (IllegalArgumentException e)
        {
            LOGGER.severe("Failed to create instance of Beneficiary.");
            LOGGER.severe("Given FQN: " + fullyQualifiedName);
            LOGGER.severe("Failed to run: Check your constructor argument");
            System.exit(20);
        }
        catch (ClassCastException e)
        {   LOGGER.severe("Failed to create instance of Beneficiary.");
            LOGGER.severe("Given FQN: " + fullyQualifiedName);
            LOGGER.severe("Failed to cast the object");
            System.exit(30);
        }
        catch (ClassNotFoundException e)
        {
            LOGGER.severe("Failed to create instance of Beneficiary.");
            LOGGER.severe("Given FQN: " + fullyQualifiedName);
            LOGGER.severe("Decorator can't be applied to the object");
            System.exit(40);
        }
        catch (Exception e)
        {
            LOGGER.severe("Failed to create instance of Beneficiary.");
            LOGGER.severe("Given FQN: " + fullyQualifiedName);
            System.exit(50);
        }
        return record;
    }

}
