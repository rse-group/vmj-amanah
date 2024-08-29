package auth.token;

import java.lang.reflect.Constructor;
import java.util.logging.Logger;

import auth.token.core.TokenResource;

public class TokenResourceFactory {
	private static final Logger LOGGER = Logger.getLogger(TokenResourceFactory.class.getName());

    private TokenResourceFactory() { }
    
    public static TokenResource createTokenResource(String fullyQualifiedName, Object... base)
    {
        TokenResource tokenResource = null;
        try {
		    Class<?> clz = Class.forName(fullyQualifiedName);
		    Constructor<?> constructor = clz.getDeclaredConstructors()[0];
		    tokenResource = (TokenResource) constructor.newInstance(base);
		}
		catch (IllegalArgumentException e)
		{
		    LOGGER.severe("Failed to create instance of TokenResource.");
		    LOGGER.severe("Given FQN: " + fullyQualifiedName);
		    LOGGER.severe("Failed to run: Check your constructor argument");
		    System.exit(20);
		}
		catch (ClassCastException e)
		{   LOGGER.severe("Failed to create instance of TokenResource.");
		    LOGGER.severe("Given FQN: " + fullyQualifiedName);
		    LOGGER.severe("Failed to cast the object");
		    System.exit(30);
		}
		catch (ClassNotFoundException e)
		{
		    LOGGER.severe("Failed to create instance of TokenResource.");
		    LOGGER.severe("Given FQN: " + fullyQualifiedName);
		    LOGGER.severe("Class not Found");
		    System.exit(40);
		}
		catch (Exception e)
		{
		    LOGGER.severe("Failed to create instance of TokenResource.");
		    LOGGER.severe("Given FQN: " + fullyQualifiedName);
		    System.exit(50);
		}
        return tokenResource;
    }
}
