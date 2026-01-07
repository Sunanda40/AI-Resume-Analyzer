package com.ai.resume.service;

import org.springframework.stereotype.Service;

@Service
public class ResumeAnalysisService {

    public int calculateScore(String resumeText) {

    	if (resumeText == null || resumeText.isBlank()) {
            return 0;
        }

        resumeText = resumeText
        		 .toLowerCase()
        		 .replaceAll("[^a-z ]", " ")   
                 .replaceAll("\\s+", " "); 

        int score = 0;

        String[] keywords = {
            "java", "spring", "spring boot",
            "hibernate", "sql", "mysql",
            "rest", "api", "microservices",
            "html", "css", "javascript"
        };

        for (String keyword : keywords) {
            if (resumeText.contains(keyword)) {
                score += 10;
            }
        }

        return score;
    }
}