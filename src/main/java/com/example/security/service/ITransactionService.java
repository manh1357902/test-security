package com.example.security.service;

import com.example.security.dto.request.ListTransactionRequest;
import com.example.security.dto.request.TransactionDecodeRequest;
import com.example.security.dto.request.TransactionRequest;
import com.example.security.dto.response.TransactionResponse;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

/**
 * Service interface for transaction operations.
 */
public interface ITransactionService {

    /**
     * Prepare the parameters for creating a transaction.
     *
     * @param transactionDecodeRequest the transaction decode request data
     * @return the list transaction request with prepared parameters
     * @throws NoSuchAlgorithmException if the algorithm is not available
     * @throws InvalidKeySpecException if the key specification is invalid
     */
    ListTransactionRequest createListRequest(TransactionDecodeRequest transactionDecodeRequest) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException;

    /**
     * Create and save two transaction records (sender and receiver) and return their responses.
     *
     * @param listTransactionRequest the list transaction request data
     * @return list of transaction responses (sender and receiver)
     * @throws InvalidAlgorithmParameterException if the algorithm parameters are invalid
     * @throws NoSuchPaddingException if the padding mechanism is not available
     * @throws IllegalBlockSizeException if the block size is illegal
     * @throws NoSuchAlgorithmException if the algorithm is not available
     * @throws BadPaddingException if the padding is incorrect
     * @throws InvalidKeyException if the key is invalid
     */
    List<TransactionResponse> createTransaction(ListTransactionRequest listTransactionRequest) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, InvalidKeySpecException;
}
