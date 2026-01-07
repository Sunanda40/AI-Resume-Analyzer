package com.ai.resume.repo;



import org.springframework.data.jpa.repository.JpaRepository;
import com.ai.resume.entity.Resume;

public interface ResumeRepository extends JpaRepository<Resume, Long> {
	Resume findTopByEmailOrderByIdDesc(String email);
}