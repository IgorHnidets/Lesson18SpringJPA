package com.logos.lesson18springjpa.repository;

import com.logos.lesson18springjpa.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("select u from User u where u.id = ?1")
    Optional<User> searchById(int id);

    Optional<User> findByEmail(String email);

    void findByFirstnameOrLastnameOrderByFirstname(String firstName,String lastName);



}
