package com.example.security.service.impl;

import com.example.security.dto.request.ListTransactionRequest;
import com.example.security.dto.request.TransactionDecodeRequest;
import com.example.security.dto.request.TransactionRequest;
import com.example.security.dto.response.TransactionResponse;
import com.example.security.enity.Transaction;
import com.example.security.repository.TransactionRepository;
import com.example.security.service.ITransactionService;
import com.example.security.util.AesUtil;
import com.example.security.util.RsaUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.math.BigDecimal;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Service implementation for transaction-related business logic.
 */
@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements ITransactionService {

    private final TransactionRepository transactionRepository;

    @Value("${RSA_PUBLIC_KEY}")
    private String publicKeyString;

    @Value("${RSA_PRIVATE_KEY}")
    private String privateKeyString;

    /**
     * Prepare the parameters for creating a transaction.
     *
     * @param transactionDecodeRequest the transaction decode request data
     * @return the list transaction request with prepared parameters
     * @throws NoSuchAlgorithmException if the algorithm is not available
     * @throws InvalidKeySpecException   if the key specification is invalid
     */
    @Override
    public ListTransactionRequest createListRequest(TransactionDecodeRequest transactionDecodeRequest) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        List<TransactionRequest> transactionRequests = new ArrayList<>();
        PublicKey publicKey = RsaUtil.stringToPublicKey(publicKeyString);
        String timeNow = LocalDateTime.now().toString();
        TransactionRequest sendTransaction = new TransactionRequest();
        sendTransaction.setTransactionID(RsaUtil.encrypt(String.valueOf(UUID.randomUUID()), publicKey));
        sendTransaction.setAccount(RsaUtil.encrypt(transactionDecodeRequest.getAccountSender(), publicKey));
        sendTransaction.setInDebt(RsaUtil.encrypt(transactionDecodeRequest.getTransferAmount().toPlainString(), publicKey));
        sendTransaction.setHave(RsaUtil.encrypt(BigDecimal.ZERO.toPlainString(), publicKey));
        sendTransaction.setTime(RsaUtil.encrypt(timeNow, publicKey));
        transactionRequests.add(sendTransaction);

        TransactionRequest receiveTransaction = new TransactionRequest();
        receiveTransaction.setTransactionID(RsaUtil.encrypt(String.valueOf(UUID.randomUUID()), publicKey));
        receiveTransaction.setAccount(RsaUtil.encrypt(transactionDecodeRequest.getAccountSender(), publicKey));
        receiveTransaction.setInDebt(RsaUtil.encrypt(BigDecimal.ZERO.toPlainString(), publicKey));
        receiveTransaction.setHave(RsaUtil.encrypt(transactionDecodeRequest.getTransferAmount().toPlainString(), publicKey));
        receiveTransaction.setTime(RsaUtil.encrypt(timeNow, publicKey));
        transactionRequests.add(receiveTransaction);
        return  new ListTransactionRequest(transactionRequests);
    }

    /**
     * Create and save two transaction records (sender and receiver) and return their responses.
     *
     * @param listTransactionRequest the lst transaction request data
     * @return list of transaction responses (sender and receiver)
     * @throws InvalidAlgorithmParameterException if the algorithm parameters are invalid
     * @throws NoSuchPaddingException             if the padding mechanism is not available
     * @throws IllegalBlockSizeException          if the block size is illegal
     * @throws NoSuchAlgorithmException           if the algorithm is not available
     * @throws BadPaddingException                if the padding is incorrect
     * @throws InvalidKeyException                if the key is invalid
     */
    @Override
    @Transactional
    public List<TransactionResponse> createTransaction(ListTransactionRequest listTransactionRequest) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, InvalidKeySpecException {
        PrivateKey privateKey = RsaUtil.stringToPrivateKey(privateKeyString);
        List<Transaction> transactions = new ArrayList<>();
        for (TransactionRequest transactionRequest : listTransactionRequest.getTransactionRequests()) {
            String decryptedTransactionID = RsaUtil.decrypt(transactionRequest.getTransactionID(), privateKey);
            String decryptedInDebt = RsaUtil.decrypt(transactionRequest.getInDebt(), privateKey);
            String decryptedHave = RsaUtil.decrypt(transactionRequest.getHave(), privateKey);
            String decryptedAccount = RsaUtil.decrypt(transactionRequest.getAccount(), privateKey);
            String decryptedTime = RsaUtil.decrypt(transactionRequest.getTime(), privateKey);
            Transaction transaction = new Transaction();
            transaction.setTransactionID(decryptedTransactionID);
            transaction.setAccount(AesUtil.encrypt(decryptedAccount));
            transaction.setInDebt(new BigDecimal(decryptedInDebt));
            transaction.setHave(new BigDecimal(decryptedHave));
            transaction.setTime(LocalDateTime.parse(decryptedTime));
            transactions.add(transaction);
        }
        List<Transaction> savedTransactions = transactionRepository.saveAll(transactions);

        return savedTransactions.stream().map(this::toTransactionResponse).toList();
    }

    private TransactionResponse toTransactionResponse(Transaction transaction) {
        return new TransactionResponse(
                transaction.getId(),
                transaction.getTransactionID(),
                transaction.getAccount(),
                transaction.getInDebt(),
                transaction.getHave(),
                transaction.getTime()
        );
    }
}


