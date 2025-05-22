package com.example.security.controller;

import com.example.security.constant.Constant;
import com.example.security.dto.request.ListTransactionRequest;
import com.example.security.dto.request.TransactionUserRequest;
import com.example.security.dto.response.ApiResponse;
import com.example.security.dto.response.TransactionResponse;
import com.example.security.service.ITransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

/**
 * Controller for handling transaction-related endpoints.
 */

@Tag(name = "Transaction API", description = "APIs for transaction operations")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/transactions")
public class TransactionController {

    private final ITransactionService transactionService;
    private static final String URL =  "http://localhost:8080/api/v1/transactions/create";

    @Value("${RSA_PUBLIC_KEY}")
    private String publicKeyString;
    /**
     * Get the public key used for encryption.
     *
     * @return the public key as a string
     */

    /**
     * Receives encrypted transaction information, decodes it, and forwards the request to the transaction creation endpoint.
     *
     * @param transactionUserRequest the encrypted transaction request data
     * @return ApiResponse containing the result of the transaction creation
     * @throws NoSuchAlgorithmException if the cryptographic algorithm is not available
     * @throws InvalidKeySpecException if the key specifications are invalid
     * @throws NoSuchPaddingException if the padding mechanism is not available
     * @throws IllegalBlockSizeException if the block size is illegal for the used algorithm
     * @throws BadPaddingException if the padding is incorrect
     * @throws InvalidKeyException if the key is invalid
     */
    @Operation(
            summary = "Process Encrypted Transaction Information",
            description = "Decodes encrypted transaction data and forwards it to the transaction creation service"
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Successfully processed the transaction information",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid or malformed encrypted data"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal server error during decryption or processing")
    })
    @PostMapping("/info")
    public ResponseEntity<Object> receiveInfo(@Valid @RequestBody TransactionUserRequest transactionUserRequest) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        ListTransactionRequest listTransactionRequest = transactionService.createListRequest(transactionUserRequest);
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<ListTransactionRequest> entity = new HttpEntity<>(listTransactionRequest, headers);
        ResponseEntity<ApiResponse> response = restTemplate.exchange(
                URL,
                HttpMethod.POST,
                entity,
                ApiResponse.class
        );
        return ResponseEntity.ok(response.getBody());
    }
    /**
     * Create two transaction records (sender and receiver) and return their responses.
     *
     * @param listTransactionRequest the transaction request data
     * @return ApiResponse containing a list of transaction responses
     * @throws InvalidAlgorithmParameterException if the algorithm parameters are invalid
     * @throws NoSuchPaddingException if the padding mechanism is not available
     * @throws IllegalBlockSizeException if the block size is illegal
     * @throws NoSuchAlgorithmException if the algorithm is not available
     * @throws BadPaddingException if the padding is incorrect
     * @throws InvalidKeyException if the key is invalid
     */
    @Operation(
            summary = "Create Transaction Records",
            description = "Creates two transaction records - one for the sender (with debt) and one for the receiver (with credit)"
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Successfully created transaction records",
                    content = @Content(schema = @Schema(implementation = TransactionResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid transaction request data"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal server error during transaction processing")
    })
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<Object>> createTransaction(@Valid @RequestBody ListTransactionRequest listTransactionRequest) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, InvalidKeySpecException {
        List<TransactionResponse> transactionResponses = transactionService.createTransaction(listTransactionRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(Constant.SUCCESS, transactionResponses));
    }

}
