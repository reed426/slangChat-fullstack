package com.example.chat_translator.Chat;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;


import java.util.Map;
@Slf4j
@Controller
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:8080", maxAge = 3600)
public class TranslatorController {
    @GetMapping("/home")
    public String translate() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Access-Control-Allow-Headers", "*");
        return "index";
    }

    @ResponseBody
    @GetMapping("/chat/chatting/{code}")
    public Map<String, Object> get(@PathVariable String code) {

        // webClient 기본 설정
        WebClient webClient =
                WebClient
                        .builder()
                        .baseUrl("http://127.0.0.1:5000")
                        .build();

        // api 요청
        Map<String, Object> response =
                webClient
                        .get()
                        .uri(uriBuilder ->
                                uriBuilder
                                        .path("/chatting/"+code)
                                        .build())
                        .retrieve()
                        .bodyToMono(Map.class)
                        .block();

        // 결과 확인
        //log.info((String) response.get("output"));
        log.info(String.valueOf(response));
        return response;
    }
}
