package io.swagger.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.model.CreateUser;
import io.swagger.model.ModifyUser;
import io.swagger.model.User;
import io.swagger.service.UserService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-05-20T13:58:44.577Z[GMT]")
@RestController
public class UsersApiController implements UsersApi {

    @Autowired
    private UserService userService;

    private static final Logger log = LoggerFactory.getLogger(UsersApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public UsersApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<User> createUser(@Parameter(in = ParameterIn.DEFAULT, description = "", required=true, schema=@Schema()) @Valid @RequestBody CreateUser body) {
        User newUser = new User(body);
        userService.addUser(newUser);
        return new ResponseEntity<>(newUser, HttpStatus.OK);
    }


    public ResponseEntity<Void> deleteUser(@Parameter(in = ParameterIn.PATH, description = "The userid for the user to delete", required=true, schema=@Schema()) @PathVariable("userid") Integer userid) {
        userService.deleteUserById(userid);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<User> getUser(@Parameter(in = ParameterIn.PATH, description = "The userid of the user", required=true, schema=@Schema()) @PathVariable("userid") Integer userid) {
        User user = userService.getUserById(userid);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    public ResponseEntity<List<User>> getUsers(@Parameter(in = ParameterIn.QUERY, description = "The number of items to skip before starting to collect the result set" ,schema=@Schema()) @Valid @RequestParam(value = "offset", required = false) Integer offset,@Parameter(in = ParameterIn.QUERY, description = "The numbers of items to return" ,schema=@Schema()) @Valid @RequestParam(value = "limit", required = false) Integer limit) {
        List<User> users = userService.getUsers();
        return new ResponseEntity<>(users.subList(offset,offset+limit),HttpStatus.OK);
    }


    public ResponseEntity<Void> updateUser(@Parameter(in = ParameterIn.PATH, description = "The userid for the user to update", required=true, schema=@Schema()) @PathVariable("userid") Integer userid,@Parameter(in = ParameterIn.DEFAULT, description = "", schema=@Schema()) @Valid @RequestBody ModifyUser body) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

}
