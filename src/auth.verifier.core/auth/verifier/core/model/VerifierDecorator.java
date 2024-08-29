package auth.verifier.core;

public abstract class VerifierDecorator extends VerifierComponent {
	
	public VerifierComponent verifier;
	
	public VerifierDecorator(VerifierComponent verifier) {
		this.verifier = verifier;
	}
	
	public VerifierDecorator() {
		this.verifier = new VerifierImpl();
	}
	
	public VerifierComponent getVerifier() {
    	return this.verifier;
    }
    
    public void setVerifier(VerifierComponent verifier) {
    	this.verifier = verifier;
    }
}
