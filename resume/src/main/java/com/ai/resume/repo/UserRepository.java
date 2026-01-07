package com.ai.resume.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ai.resume.entity.User;

public interface UserRepository extends JpaRepository<User,Long> {
	Optional<User> findByEmail(String email);

}
