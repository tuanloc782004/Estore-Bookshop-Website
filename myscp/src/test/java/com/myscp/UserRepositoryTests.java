package com.myscp;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.myscp.model.Role;
import com.myscp.model.User;
import com.myscp.repository.UserRepository;
 
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {
 
    @Autowired
    private TestEntityManager entityManager;
     
    @Autowired
    private UserRepository repo;
     
    @Test
    public void testCreateUser() {
    	
    	Role role = new Role();
    	role.setId(1L);
    	
        User user = new User();
        user.setUsername("admin");
        user.setPassword("admin");
        user.setEmail("jobhere.22t.nhat1@gmail.com");
        user.setEnabled(true);
        user.setCreatedAt(LocalDateTime.now());
        user.setRole(role);
         
        User savedUser = repo.save(user);
         
        User existUser = entityManager.find(User.class, savedUser.getId());
         
        assertThat(user.getUsername()).isEqualTo(existUser.getUsername());
         
    }
}
