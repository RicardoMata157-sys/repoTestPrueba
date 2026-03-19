package com.mx.test.service;

import com.mx.test.dto.PaymentRequest;
import com.mx.test.dto.TransactionResponse;
import com.mx.test.entity.Transaction;
import com.mx.test.entity.TransactionStatus;
import com.mx.test.entity.User;
import com.mx.test.repository.TransactionRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionService {

	private final TransactionRepository transactionRepository;
	private final UserService userService;
	private final EncryptionService encryptionService;

	public TransactionService(UserService userService, TransactionRepository transactionRepository,
			EncryptionService encryptionService) {
		this.userService = userService;
		this.transactionRepository = transactionRepository;
		this.encryptionService = encryptionService;

	}

	public TransactionResponse processPayment(PaymentRequest request) {
		User user = userService.getUser(request.getUserId());

		if (request.getCardNumber() == null || request.getCardNumber().isBlank()) {
			throw new IllegalArgumentException("El número de tarjeta es obligatorio");
		}

		if (request.getCardHolderName() == null || request.getCardHolderName().isBlank()) {
			throw new IllegalArgumentException("El nombre del titular es obligatorio");
		}

		if (request.getAmount() == null) {
			throw new IllegalArgumentException("El monto es obligatorio");
		}

		String cardNumber = request.getCardNumber().replaceAll("\\D", "");

		if (cardNumber.length() < 4) {
			throw new IllegalArgumentException("El número de tarjeta no es válido");
		}

		String cardLast4 = cardNumber.substring(cardNumber.length() - 4);

		boolean approved = isApproved(cardNumber, request.getAmount().doubleValue());

		Transaction transaction = new Transaction();
		transaction.setUser(user);
    	    transaction.setEncryptedCardHolderName(
    	            encryptionService.encrypt(request.getCardHolderName().trim())
    	    );
		transaction.setCardLast4(cardLast4);
		transaction.setAmount(request.getAmount());
		transaction.setCurrency(request.getCurrency().toUpperCase());
		transaction.setStatus(approved ? TransactionStatus.APPROVED : TransactionStatus.REJECTED);
		transaction.setToken(UUID.randomUUID().toString());

		Transaction saved = transactionRepository.save(transaction);
		return toResponse(saved);
	}

	public List<TransactionResponse> findAll() {
		return transactionRepository.findAll().stream().map(this::toResponse).toList();
	}

	public TransactionResponse findById(Long id) {
		Transaction transaction = transactionRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Transaction not found"));
		return toResponse(transaction);
	}

	private TransactionResponse toResponse(Transaction transaction) {
		TransactionResponse transactionResponse = new TransactionResponse();
		transactionResponse.setId(transaction.getId());
		transactionResponse.setUserId(transaction.getUser().getId());
		transactionResponse.setCardHolderName(encryptionService.decrypt(transaction.getEncryptedCardHolderName()));
		transactionResponse.setCardLast4(transaction.getCardLast4());
		transactionResponse.setAmount(transaction.getAmount());
		transactionResponse.setCurrency(transaction.getCurrency());
		transactionResponse.setStatus(transaction.getStatus().name());
		transactionResponse.setToken(transaction.getToken());
		transactionResponse.setCreatedAt(transaction.getCreatedAt());
		return transactionResponse;

	}

	private boolean isApproved(String cardNumber, double amount) {
		int lastDigit = Character.getNumericValue(cardNumber.charAt(cardNumber.length() - 1));
		return lastDigit % 2 == 0 && amount <= 10000;
	}
}
