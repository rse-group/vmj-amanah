package auth.token.core;

import java.util.HashMap;

import vmj.routing.route.VMJExchange;

public abstract class TokenResourceComponent implements TokenResource {
    public abstract HashMap<String,Object> verifyToken(VMJExchange vmjExchange);
}
