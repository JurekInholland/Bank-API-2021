package io.swagger.configuration;

import io.swagger.model.Role;
import io.swagger.model.User;
import io.swagger.repository.UserRepository;
import io.swagger.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class BankApiApplicationRunner implements ApplicationRunner
{
    @Autowired
    private UserService userService;

    @Override
    public void run(ApplicationArguments args) throws Exception
    {
        List<User> users = Arrays.asList
        (
            new User("Brad","Gibson","0119627516","brad.gibson@example.com","firewall", Role.CUSTOMER),
            new User("Bobbie","Grant","0112627416","bobbie.grant@example.com","bobbiegrant123", Role.EMPLOYEE),
            new User("Alex","Smith","0549687523","alex.smith@example.com","alexsmith132", Role.CUSTOMER),
            new User("Ray","Manzarek","0295482332","ray.manzarek@example.com","ray475", Role.EMPLOYEE),
            new User("Benjamin","Franklin","0234765412","benjamin.franklin@example.com","benjfrank091", Role.CUSTOMER),
            new User("George","Washington","0259847659","george.washington@example.com","georgie829", Role.EMPLOYEE),
            new User("Felix","Brown","0928957462","felix.brown@example.com","felixbrown430", Role.CUSTOMER),
            new User("Hans","Zimmer","0939857193","hans.zimmer@example.com","hans4849", Role.EMPLOYEE),
            new User("Neil","Gardner","0294891839","neil.garnder@example.com","Neil1093", Role.CUSTOMER),
            new User("Virgil","Manson","0981049285","virgil.manson@example.com","virgil98", Role.EMPLOYEE)
        );
        users.forEach(user -> userService.addUser(user));

    }
}
