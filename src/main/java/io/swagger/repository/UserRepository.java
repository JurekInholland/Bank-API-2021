package io.swagger.repository;

import io.swagger.model.Role;
import io.swagger.model.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long>
{

    Optional<User> findByEmailAddress(String email);
    /*
    @Transactional
    @Modifying
    @Query("update User u set u.firstName=?1, u.lastName=?2, u.phoneNumber=?3, u.emailAddress=?4, u.password=?5, u.roles=?6  where u.id = ?7 ")
    void updateUserById(String firstName, String lastName, String phoneNumber, String emailAddress, String password, Collection<Role> roles, long id);
    */

}
