package com.example.blogger.users;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

//@SpringBootTest
@DataJpaTest//(showSql = true /*properties = "spring.datasource.url=jdbc:h2:file:./test"*/)
//@ActiveProfiles("test")
public class UsersRepositoryTests {
//    @Autowired private UsersRepository usersRepository;
//    @Test
//    @Order(1)
//    void can_create_users(){
//        usersRepository.save(
//                UserEntity.builder()
//                        .username("johndeo")
//                        .email("johndeo@gmail.com")
//                        .password("johndeo@gmail.com")
//                        .build()
//        );
//    }
//
//    @Test
//    @Order(2)
//    void can_find_users_by_username(){
//        usersRepository.save(
//                UserEntity.builder()
//                        .username("johndeo")
//                        .email("johndeo@gmail.com")
//                        .build()
//        );
//
//        assertEquals("h","h");
//    }
}
