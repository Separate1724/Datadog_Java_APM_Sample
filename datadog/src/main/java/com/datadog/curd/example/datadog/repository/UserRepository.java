package com.datadog.curd.example.datadog.repository;

import com.datadog.curd.example.datadog.entity.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Integer> {

    // Select all users
    @Query(value = "SELECT * FROM user", nativeQuery = true)
    List<User> findAllUsers();

    // Select users by name
    @Query(value = "SELECT * FROM user WHERE name = :name", nativeQuery = true)
    List<User> findByNameNative(@Param("name") String name);

    // Select users by email
    @Query(value = "SELECT * FROM user WHERE email = :email", nativeQuery = true)
    List<User> findByEmailNative(@Param("email") String email);

    // Insert a new user
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO user (name, email) VALUES (:name, :email)", nativeQuery = true)
    int insertUser(@Param("name") String name, @Param("email") String email);

    // Delete a user by ID
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM user WHERE id = :id", nativeQuery = true)
    int deleteUser(@Param("id") Integer id);
}