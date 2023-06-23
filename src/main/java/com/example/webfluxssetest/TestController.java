package com.example.webfluxssetest;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TestController {
    @NonFinal Long memberCount = 0l;
    @NonFinal Long totalCount = 0l;


    @GetMapping("api/match/event")
    public Flux<ServerSentEvent<String>> sse() {
        memberCount++;
        return Flux.interval(Duration.ofSeconds(1))
                .timeout(Duration.ofDays(1))
                .map(s -> {
                    totalCount++;
                    return ServerSentEvent.builder(s + "time, member : " + memberCount+ ", total: " + totalCount).build();
                })
                .takeWhile(s -> totalCount != 10)
                .doOnComplete(() -> totalCount = 0l)
                .doOnCancel(() -> memberCount--);
    }
}

// 요청 보냄
// 요청정보 생성
// 큐 넣어
// OK
// SSE 보내
// OK
// 대기

//3명 돼
// ok
