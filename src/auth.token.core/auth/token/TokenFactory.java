package auth.token;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import auth.token.core.Token;

public class TokenFactory {
	private static final Logger LOGGER = Logger.getLogger(TokenFactory.class.getName());
	private static String CURRENT_TOKEN;

    private TokenFactory() { }
    
    private static List<String> getListTokenDelta() {
    	List<String> tokenModules = ModuleLayer.boot().modules().stream()
			.map(module -> module.getName() + ".TokenImpl")
			.filter(moduleName -> moduleName.startsWith("auth.token"))
			.filter(moduleName -> !moduleName.contains("core"))
			.collect(Collectors.toList());
    	return tokenModules;
    }
    
    public static Token createToken() {
    	Token coreToken = createToken("auth.token.core.TokenImpl");
        if (CURRENT_TOKEN != null) {
            Token token = createToken(CURRENT_TOKEN, coreToken);
    		if (token != null)
    			return token;
        }
        
    	for (String tokenName: getListTokenDelta()) {
    		Token token = createToken(tokenName, coreToken);
    		if (token != null) {
                CURRENT_TOKEN = tokenName;
    			return token;
    		}
    	}
    	
    	return coreToken;
    }
    
    public static Token createToken(String fullyQualifiedName, Object... base) {
    	Token token = null;
        try {
        	Class<?> clz = Class.forName(fullyQualifiedName);
		    Constructor<?>[] constructorList = clz.getDeclaredConstructors();
		    Constructor<?> constructor = null;
            for (int i = 0; i < constructorList.length; i++) {
                try {
                    constructor = constructorList[i];
                    System.out.println(constructor.toString());
                    token = (Token) constructor.newInstance(base);
                    break;
                } catch (IllegalArgumentException e) {
                    if (i < constructorList.length - 1) {
                        System.out.println("Trying other constructor");
                        continue;
                    }
                }
            }
		} catch (IllegalArgumentException e) {
            LOGGER.severe("Failed to create instance of Token.");
            LOGGER.severe("Given FQN: " + fullyQualifiedName);
            LOGGER.severe("Failed to run: Check your constructor argument");
        } catch (ClassCastException e) {
            LOGGER.severe("Failed to create instance of Token.");
            LOGGER.severe("Given FQN: " + fullyQualifiedName);
            LOGGER.severe("Failed to cast the object");
        } catch (ClassNotFoundException e) {
            LOGGER.severe("Failed to create instance of Token.");
            LOGGER.severe("Given FQN: " + fullyQualifiedName);
            LOGGER.severe("Class not Found");
        } catch (Exception e) {
            LOGGER.severe("Failed to create instance of Token.");
            LOGGER.severe("Given FQN: " + fullyQualifiedName);
        }
        return token;
    }
}
