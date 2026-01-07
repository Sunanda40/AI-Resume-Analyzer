package com.ai.resume.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "resumes")
public class Resume {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;
    private String filePath;
    private String email; // <- THIS IS REQUIRED

    private String resumeText;
   
    public Resume() {
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getResumeText() {
		return resumeText;
	}

	public void setResumeText(String resumeText) {
		this.resumeText = resumeText;
	}

	public Resume(Long id, String fileName, String filePath, String email, String resumeText) {
		super();
		this.id = id;
		this.fileName = fileName;
		this.filePath = filePath;
		this.email = email;
		this.resumeText = resumeText;
	}// email from JWT

}