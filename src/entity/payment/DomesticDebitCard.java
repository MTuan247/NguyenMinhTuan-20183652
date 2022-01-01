package entity.payment;

import java.sql.Timestamp;

public class DomesticDebitCard extends PaymentCard {
	private String cardCode;
	private String cardOwner;
	private String issuingBank;
	private String dateExpired;

	public DomesticDebitCard(String cardCode, String owner, String issuingBank, String dateExpired) {
		super();
		this.cardCode = cardCode;
		this.cardOwner = owner;
		this.issuingBank = issuingBank;
		this.dateExpired = dateExpired;
	}

}
