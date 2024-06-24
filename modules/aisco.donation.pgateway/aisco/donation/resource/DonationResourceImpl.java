package aisco.donation.pgateway;

import aisco.donation.core.DonationResourceDecorator;

import java.util.*;

import paymentgateway.product.flip;

public class DonationResourceImpl extends DonationResourceDecorator {
	 public DonationResourceImpl(DonationResourceComponent record) {
		 super(record);
	 }
	 
	 @Route(url = "call/paymentLink")
	 public HashMap<String, Object> sendTransaction (VMJExchange vmjExchange) throws Exception {
		 return flip.paymentLink(vmjExchange);
	 }
}