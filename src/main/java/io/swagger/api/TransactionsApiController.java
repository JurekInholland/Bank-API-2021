package io.swagger.api;

import io.swagger.model.Body1;
import io.swagger.model.Transaction;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-05-04T07:44:48.337Z[GMT]")
@RestController
public class TransactionsApiController implements TransactionsApi {

    private static final Logger log = LoggerFactory.getLogger(TransactionsApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public TransactionsApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<Void> createTransaction(@Parameter(in = ParameterIn.DEFAULT, description = "", required=true, schema=@Schema()) @Valid @RequestBody List<Transaction> body) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<List<Transaction>> getTransactions(@Parameter(in = ParameterIn.QUERY, description = "The number of items to skip before starting to collect the result set" ,schema=@Schema()) @Valid @RequestParam(value = "offset", required = false) Integer offset,@Parameter(in = ParameterIn.QUERY, description = "The numbers of items to return" ,schema=@Schema()) @Valid @RequestParam(value = "limit", required = false) Integer limit) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<List<Transaction>>(objectMapper.readValue("[ {\n  \"amount\" : 6.027456183070403,\n  \"userPerforming\" : {\n    \"firstName\" : \"firstName\",\n    \"lastName\" : \"lastName\",\n    \"emailAddress\" : \"\",\n    \"password\" : \"\",\n    \"phoneNumber\" : 6,\n    \"role\" : \"customer\",\n    \"id\" : 0\n  },\n  \"id\" : 0,\n  \"accountFrom\" : {\n    \"balance\" : 0,\n    \"iban\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\",\n    \"accountType\" : \"current\",\n    \"user\" : {\n      \"firstName\" : \"firstName\",\n      \"lastName\" : \"lastName\",\n      \"emailAddress\" : \"\",\n      \"password\" : \"\",\n      \"phoneNumber\" : 6,\n      \"role\" : \"customer\",\n      \"id\" : 0\n    }\n  },\n  \"timestamp\" : \"2000-01-23T04:56:07.000+00:00\"\n}, {\n  \"amount\" : 6.027456183070403,\n  \"userPerforming\" : {\n    \"firstName\" : \"firstName\",\n    \"lastName\" : \"lastName\",\n    \"emailAddress\" : \"\",\n    \"password\" : \"\",\n    \"phoneNumber\" : 6,\n    \"role\" : \"customer\",\n    \"id\" : 0\n  },\n  \"id\" : 0,\n  \"accountFrom\" : {\n    \"balance\" : 0,\n    \"iban\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\",\n    \"accountType\" : \"current\",\n    \"user\" : {\n      \"firstName\" : \"firstName\",\n      \"lastName\" : \"lastName\",\n      \"emailAddress\" : \"\",\n      \"password\" : \"\",\n      \"phoneNumber\" : 6,\n      \"role\" : \"customer\",\n      \"id\" : 0\n    }\n  },\n  \"timestamp\" : \"2000-01-23T04:56:07.000+00:00\"\n} ]", List.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<List<Transaction>>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<List<Transaction>>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Void> transactionId(@Parameter(in = ParameterIn.PATH, description = "The transactionId of the transaction", required=true, schema=@Schema()) @PathVariable("transactionId") Integer transactionId,@Parameter(in = ParameterIn.DEFAULT, description = "", required=true, schema=@Schema()) @Valid @RequestBody Body1 body) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Void> transactionsTransactionIdDelete(@Parameter(in = ParameterIn.PATH, description = "The transactionId of the transaction", required=true, schema=@Schema()) @PathVariable("transactionId") Integer transactionId) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<List<Transaction>> transactionsTransactionIdGet(@Parameter(in = ParameterIn.PATH, description = "The transactionId of the transaction", required=true, schema=@Schema()) @PathVariable("transactionId") Integer transactionId) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<List<Transaction>>(objectMapper.readValue("[ {\n  \"amount\" : 6.027456183070403,\n  \"userPerforming\" : {\n    \"firstName\" : \"firstName\",\n    \"lastName\" : \"lastName\",\n    \"emailAddress\" : \"\",\n    \"password\" : \"\",\n    \"phoneNumber\" : 6,\n    \"role\" : \"customer\",\n    \"id\" : 0\n  },\n  \"id\" : 0,\n  \"accountFrom\" : {\n    \"balance\" : 0,\n    \"iban\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\",\n    \"accountType\" : \"current\",\n    \"user\" : {\n      \"firstName\" : \"firstName\",\n      \"lastName\" : \"lastName\",\n      \"emailAddress\" : \"\",\n      \"password\" : \"\",\n      \"phoneNumber\" : 6,\n      \"role\" : \"customer\",\n      \"id\" : 0\n    }\n  },\n  \"timestamp\" : \"2000-01-23T04:56:07.000+00:00\"\n}, {\n  \"amount\" : 6.027456183070403,\n  \"userPerforming\" : {\n    \"firstName\" : \"firstName\",\n    \"lastName\" : \"lastName\",\n    \"emailAddress\" : \"\",\n    \"password\" : \"\",\n    \"phoneNumber\" : 6,\n    \"role\" : \"customer\",\n    \"id\" : 0\n  },\n  \"id\" : 0,\n  \"accountFrom\" : {\n    \"balance\" : 0,\n    \"iban\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\",\n    \"accountType\" : \"current\",\n    \"user\" : {\n      \"firstName\" : \"firstName\",\n      \"lastName\" : \"lastName\",\n      \"emailAddress\" : \"\",\n      \"password\" : \"\",\n      \"phoneNumber\" : 6,\n      \"role\" : \"customer\",\n      \"id\" : 0\n    }\n  },\n  \"timestamp\" : \"2000-01-23T04:56:07.000+00:00\"\n} ]", List.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<List<Transaction>>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<List<Transaction>>(HttpStatus.NOT_IMPLEMENTED);
    }

}
