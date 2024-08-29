package auth.token.core;

import java.util.HashMap;

import vmj.routing.route.VMJExchange;

public interface TokenResource {
	HashMap<String,Object> verifyToken(VMJExchange vmjExchange);
}
