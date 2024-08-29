package auth.token.core;

import java.util.HashMap;

import vmj.routing.route.VMJExchange;

public abstract class TokenResourceDecorator extends TokenResourceComponent {
    public TokenResourceComponent record;
	
	public TokenResourceDecorator(TokenResourceComponent record) {
		this.record = record;
	}
	
	public TokenResourceDecorator() {
		this.record = new TokenResourceImpl();
	}

    public HashMap<String,Object> verifyToken(VMJExchange vmjExchange) {
        return record.verifyToken(vmjExchange);
    }
}
