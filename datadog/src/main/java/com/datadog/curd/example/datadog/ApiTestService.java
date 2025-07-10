package com.datadog.curd.example.datadog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class ApiTestService {

    private final RestTemplate restTemplate;
    private static final String BASE_URL = "http://localhost:8080";
    private int requestCounter = 0;

    @Autowired
    public ApiTestService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @Scheduled(fixedRate = 5000) // 5초마다 실행
    public void performApiTests() {
        requestCounter++;
        System.out.println("Performing API test #" + requestCounter);

        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "dd-test-scanner-log");

        try {
            // 1. 모든 사용자 조회
            restTemplate.exchange(BASE_URL + "/users", HttpMethod.GET,
                new HttpEntity<>(headers), String.class);

            // 2. 이름으로 사용자 검색
            restTemplate.exchange(BASE_URL + "/users/search?name=Test",
                HttpMethod.GET, new HttpEntity<>(headers), String.class);

            // 3. 이메일로 사용자 검색
            restTemplate.exchange(BASE_URL + "/users/search/email?email=test@example.com",
                HttpMethod.GET, new HttpEntity<>(headers), String.class);

            // 4. 사용자 추가 (POST 요청)
            HttpHeaders postHeaders = new HttpHeaders();
            postHeaders.set("User-Agent", "dd-test-scanner-log");
            postHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
            formData.add("name", "사용자" + requestCounter);
            formData.add("email", "user" + requestCounter + "@example.com");

            HttpEntity<MultiValueMap<String, String>> postEntity =
                new HttpEntity<>(formData, postHeaders);
            restTemplate.exchange(BASE_URL + "/users", HttpMethod.POST,
                postEntity, String.class);

            // 5. 존재하지 않는 경로 (404 에러 유도)
            try {
                restTemplate.exchange(BASE_URL + "/users/nonexistent",
                    HttpMethod.GET, new HttpEntity<>(headers), String.class);
            } catch (Exception e) {
                System.err.println("존재하지 않는 경로입니다 (/users/nonexistent): " + e.getMessage());
                e.printStackTrace();
            }

            // 7. 잘못된 파라미터 (에러 유도)
            try {
                restTemplate.exchange(BASE_URL + "/users/search",
                    HttpMethod.GET, new HttpEntity<>(headers), String.class);
            } catch (Exception e) {
                // 에러 무시
            }

        } catch (Exception e) {
            System.err.println("API test error: " + e.getMessage());
        }
    }
}
