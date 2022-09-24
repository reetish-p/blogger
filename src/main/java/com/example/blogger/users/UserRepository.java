package com.example.blogger.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Integer> {

    Optional<UserEntity> findByUsername(String username);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO user_followers (users_id, followers_id) VALUES(?2, ?1)", nativeQuery = true)
    void followUser(Integer loggedInUserUserId, Integer userToFollow);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM user_followers WHERE users_id = ?2 AND followers_id = ?1", nativeQuery = true)
    void unfollowUser(Integer loggedInUserUserId, Integer userToFollow);
}
