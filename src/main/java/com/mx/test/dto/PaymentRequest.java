package com.mx.test.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;


public class PaymentRequest {
    @NotBlank
    private String cardHolderName;

    @NotBlank
    @Pattern(regexp = "\\d{16}", message = "Card number must have 16 digits")
    private String cardNumber;

    @NotBlank
    @Pattern(regexp = "(0[1-9]|1[0-2])/[0-9]{2}", message = "Expiry must be MM/YY")
    private String expiry;

    @NotBlank
    @Pattern(regexp = "\\d{3,4}", message = "CVV must have 3 or 4 digits")
    private String cvv;

    @NotNull
    @DecimalMin(value = "1.00")
    private BigDecimal amount;

    @NotBlank
    @Size(min = 3, max = 3)
    private String currency;

    @NotNull
    private Long userId;

	public String getCardHolderName() {
		return cardHolderName;
	}

	public void setCardHolderName(String cardHolderName) {
		this.cardHolderName = cardHolderName;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getExpiry() {
		return expiry;
	}

	public void setExpiry(String expiry) {
		this.expiry = expiry;
	}

	public String getCvv() {
		return cvv;
	}

	public void setCvv(String cvv) {
		this.cvv = cvv;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
    
    
    
    
    
    
    
    
    
}
