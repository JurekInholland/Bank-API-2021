package io.swagger.api;

import io.swagger.model.CreateUserDto;
import io.swagger.model.ModifyUserDto;
import io.swagger.model.PublicUserDto;
import io.swagger.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.service.UserService;
import io.swagger.util.CurrentUserInfo;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-05-21T13:43:31.154Z[GMT]")
@RestController
public class UsersApiController implements UsersApi {

    @Autowired
    private ModelMapper modelMapper;


    @Autowired
    private UserService userService;

    @Autowired
    PasswordEncoder encoder;

    private static final Logger log = LoggerFactory.getLogger(UsersApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public UsersApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<Void> createUser(@Parameter(in = ParameterIn.DEFAULT, description = "", required=true, schema=@Schema()) @Valid @RequestBody CreateUserDto body) {

        userService.addUser(convertToEntity(body));
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<Void> deleteUser(@Parameter(in = ParameterIn.PATH, description = "The userid for the user to delete", required=true, schema=@Schema()) @PathVariable("userid") Integer userid) {
        userService.deleteUserById(userid);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<User> getUser(@Parameter(in = ParameterIn.PATH, description = "The userid of the user", required=true, schema=@Schema()) @PathVariable("userid") Integer userid) {

        User user = userService.getUserById(userid);
        return new ResponseEntity<>(user, HttpStatus.OK);    }

    public ResponseEntity<List<PublicUserDto>> getUsers(@Parameter(in = ParameterIn.QUERY, description = "The number of items to skip before starting to collect the result set" ,schema=@Schema()) @Valid @RequestParam(value = "offset", required = false, defaultValue="0") Integer offset,@Parameter(in = ParameterIn.QUERY, description = "The numbers of items to return" ,schema=@Schema()) @Valid @RequestParam(value = "limit", required = false, defaultValue = "100") Integer limit) {

        List<User> users = userService.getUsers();
        List<PublicUserDto> publicUsers = new ArrayList<>();


        users.forEach(user -> {
            publicUsers.add(convertToPublicDto(user));
        });

        return new ResponseEntity<>(publicUsers.subList(Math.min(users.size(), offset),Math.min(users.size(), offset + limit)),HttpStatus.OK);
    }

    public ResponseEntity<Void> updateUser(@Parameter(in = ParameterIn.PATH, description = "The userid for the user to update", required=true, schema=@Schema()) @PathVariable("userid") Integer userid,@Parameter(in = ParameterIn.DEFAULT, description = "", schema=@Schema()) @Valid @RequestBody ModifyUserDto body) {

        if(! CurrentUserInfo.isEmployee()) {
            if(Long.parseLong(userid.toString()) != CurrentUserInfo.getCurrentUserId()) {
                throw new RuntimeException("You are not allowed to edit this user.");
            }
        }
        userService.updateUser(body,userid);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

//    Use ModelMapper to convert CreateUserDto to User object
    private User convertToEntity(CreateUserDto createUserDto) {

        User user = modelMapper.map(createUserDto, User.class);
        user.setPassword(encoder.encode(user.getPassword()));

        return user;
    }
    private User convertToUpdateUserEntity(ModifyUserDto modifyUserDto)
    {
        User user = modelMapper.map(modifyUserDto, User.class);
        return user;
    }
//    Convert User to PublicUserDto
//
    private PublicUserDto convertToPublicDto(User user) {
        PublicUserDto publicUser = modelMapper.map(user, PublicUserDto.class);
        return publicUser;
    }

}
