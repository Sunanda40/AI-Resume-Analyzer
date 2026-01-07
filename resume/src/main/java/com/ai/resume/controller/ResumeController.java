package com.ai.resume.controller;


import java.io.IOException;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.ai.resume.dto.ApiResponse;
import com.ai.resume.entity.Resume;
import com.ai.resume.repo.ResumeRepository;
import com.ai.resume.service.ResumeAnalysisService;
import com.ai.resume.service.ResumeService;

@RestController
@RequestMapping("/api/resume")
public class ResumeController {

    private final ResumeService resumeService;
    private final ResumeRepository resumeRepository;
    private final ResumeAnalysisService resumeAnalysisService;

    public ResumeController(
            ResumeService resumeService,
            ResumeRepository resumeRepository,
            ResumeAnalysisService resumeAnalysisService) {

        this.resumeService = resumeService;
        this.resumeRepository = resumeRepository;
        this.resumeAnalysisService = resumeAnalysisService;
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadResume(
            @RequestParam("file") MultipartFile file,
            Authentication authentication) {

        try {
            if (authentication == null) {
                return ResponseEntity.status(401)
                        .body(new ApiResponse("Unauthorized", false));
            }

            if (file == null || file.isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(new ApiResponse("File is missing or empty", false));
            }

            String email = authentication.getName();
            resumeService.uploadResume(file, email);

            return ResponseEntity.ok(
                    new ApiResponse("Resume uploaded successfully", true)
            );

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest()
                    .body(new ApiResponse(e.getMessage(), false));
        }
    }

    @GetMapping("/analyze")
    public ResponseEntity<?> analyzeResume(Authentication authentication) {

        if (authentication == null) {
            return ResponseEntity.status(401)
                    .body(Map.of("message", "Unauthorized"));
        }

        String email = authentication.getName();

        Resume resume =
                resumeRepository.findTopByEmailOrderByIdDesc(email);

        if (resume == null) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "Please upload resume first"));
        }

        if (resume.getResumeText() == null) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "Resume text not found"));
        }

        int score =
                resumeAnalysisService.calculateScore(resume.getResumeText());

        return ResponseEntity.ok(
                Map.of(
                        "score", score,
                        "message",
                        score > 70 ? "Good Resume" : "Needs Improvement"
                )
        );
    }
}