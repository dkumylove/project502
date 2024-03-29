package org.choongang.business;

import org.choongang.commons.Utils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@TestPropertySource(properties = "spring.profiles.active=dev")
public class ApiTest {

    @Autowired
    private Utils utils;

    @Test
    @DisplayName("사업자등록 상태 확인 API 테스트")
    void statusApiTest() throws URISyntaxException {

        String url = "https://api.odcloud.kr/api/nts-businessman/v1/status?serviceKey=CHrWrFoNSLs09ec0iaNNKpj3VuYnP%2BJA5WAtHyPqdDVCdF%2Fn1NB46%2Bfd2NDjlTiNm%2Fw48BE9guQbOo12k2a6wA%3D%3D";

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("b_no", "2208657343");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(new URI(url), request, String.class);
        String body = response.getBody();
        System.out.println(body);
    }

    @Test
    @DisplayName("사업자 등록증 상태 체크 함수 테스트")
    void checkBusinessPermit() {
        boolean result = utils.checkBusinessPermit("2208657343");

        assertTrue(result);

    }
}