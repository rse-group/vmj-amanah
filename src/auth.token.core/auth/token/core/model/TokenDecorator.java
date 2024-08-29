package auth.token.core;

public abstract class TokenDecorator extends TokenComponent {
	
	public TokenComponent token;
	
	public TokenDecorator(TokenComponent token) {
		this.token = token;
	}
	
	public TokenDecorator() {
		this.token = new TokenImpl();
	}
	
	public TokenComponent getToken() {
    	return this.token;
    }
    
    public void setToken(TokenComponent token) {
    	this.token = token;
    }
}
