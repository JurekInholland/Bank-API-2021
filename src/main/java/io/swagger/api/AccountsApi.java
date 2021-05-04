/**
 * NOTE: This class is auto generated by the swagger code generator program (3.0.25).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */
package io.swagger.api;

import io.swagger.model.Account;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.CookieValue;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;
import java.util.Map;
/*test*/
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-05-04T07:44:48.337Z[GMT]")
@Validated
public interface AccountsApi {

    @Operation(summary = "create an account", description = "creating an account | User access; Customer & Employee", security = {
        @SecurityRequirement(name = "bearerAuth")    }, tags={ "employees", "customers" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Account created successfully"),
        
        @ApiResponse(responseCode = "400", description = "Invalid request, account not created") })
    @RequestMapping(value = "/accounts",
        consumes = { "application/json" }, 
        method = RequestMethod.POST)
    ResponseEntity<Void> createAccount(@Parameter(in = ParameterIn.DEFAULT, description = "", required=true, schema=@Schema()) @Valid @RequestBody List<Account> body);


    @Operation(summary = "Close an account", description = "Closing an account using an iban | User access; Employee", security = {
        @SecurityRequirement(name = "bearerAuth")    }, tags={ "employees" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Account has been delete successfully") })
    @RequestMapping(value = "/accounts/{iban}",
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteAccount(@Parameter(in = ParameterIn.PATH, description = "The iban account to delete", required=true, schema=@Schema()) @PathVariable("iban") String iban);


    @Operation(summary = "get an account using IBAN", description = "get a specific account using an IBAN | User access; Customer (can only get their own account) & Employee", security = {
        @SecurityRequirement(name = "bearerAuth")    }, tags={ "employees", "customers" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "A json account object", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Account.class)))) })
    @RequestMapping(value = "/accounts/{iban}",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<List<Account>> getAccount(@Parameter(in = ParameterIn.PATH, description = "The the iban of the account", required=true, schema=@Schema()) @PathVariable("iban") String iban);


    @Operation(summary = "get accounts", description = "getting a list of accounts | User access; Customer (can only get their own account) & Employee", security = {
        @SecurityRequirement(name = "bearerAuth")    }, tags={ "employees", "customers" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "A JSON array of accounts", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Account.class)))),
        
        @ApiResponse(responseCode = "400", description = "bad input parameter") })
    @RequestMapping(value = "/accounts",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<List<Account>> getAccounts(@Parameter(in = ParameterIn.QUERY, description = "The number of items to skip before starting to collect the result set" ,schema=@Schema()) @Valid @RequestParam(value = "offset", required = false) Integer offset, @Parameter(in = ParameterIn.QUERY, description = "The numbers of items to return" ,schema=@Schema()) @Valid @RequestParam(value = "limit", required = false) Integer limit);


    @Operation(summary = "update an account", description = "updating an accouint using an iban | User access; Customer (can only update their own account) & Employee", security = {
        @SecurityRequirement(name = "bearerAuth")    }, tags={ "employees", "customers" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "account has been updated successfully") })
    @RequestMapping(value = "/accounts/{iban}",
        consumes = { "application/json" }, 
        method = RequestMethod.PUT)
    ResponseEntity<Void> updateAccount(@Parameter(in = ParameterIn.PATH, description = "The the iban of the account", required=true, schema=@Schema()) @PathVariable("iban") String iban, @Parameter(in = ParameterIn.DEFAULT, description = "", schema=@Schema()) @Valid @RequestBody Account body);

}

