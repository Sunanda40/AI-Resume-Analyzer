package com.ai.resume.service;

import java.io.File;
import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ai.resume.entity.Resume;
import com.ai.resume.repo.ResumeRepository;
import com.ai.resume.util.PdfUtil;

@Service
public class ResumeService {

    private final ResumeRepository resumeRepository;

    private static final String UPLOAD_DIR = "C:/resume-uploads/";

    public ResumeService(ResumeRepository resumeRepository) {
        this.resumeRepository = resumeRepository;
    }

    public Resume uploadResume(MultipartFile file, String email) throws IOException {

        if (file.isEmpty()) {
            throw new IOException("File is empty");
        }

        String contentType = file.getContentType();
        if (contentType == null || !contentType.contains("pdf")) {
            throw new IOException("Only PDF files are allowed");
        }

        File dir = new File(UPLOAD_DIR);
        if (!dir.exists()) {
            dir.mkdirs(); // creates folder if missing
        }

        String filePath = UPLOAD_DIR
                + System.currentTimeMillis()
                + "_" + file.getOriginalFilename();

        file.transferTo(new File(filePath));

     // Extract resume text from PDF
        String resumeText = PdfUtil.extractText(filePath);

        // Save resume with extracted text
        Resume resume = new Resume(
            null,
            file.getOriginalFilename(),
            filePath,
            email,
            resumeText
        );

        return resumeRepository.save(resume);
    }
}