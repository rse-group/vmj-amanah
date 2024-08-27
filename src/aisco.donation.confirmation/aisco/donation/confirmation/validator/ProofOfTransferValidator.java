package aisco.donation.confirmation.validator;

public class ProofOfTransferValidator {
	public static boolean isValidProofOfTransfer(String proofOfTransferUrl) {
		return proofOfTransferUrl.startsWith("http");
	}
}