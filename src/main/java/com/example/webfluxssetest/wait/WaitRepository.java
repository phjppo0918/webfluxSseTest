package com.example.webfluxssetest.wait;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@Repository
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class WaitRepository {
    Sinks.Many<WaitStatus> sink = Sinks.many().replay().latest();

    public void save(WaitStatus waitStatus) {
        sink.tryEmitNext(waitStatus);
    }

    public Flux<WaitStatus> findAll() {
        return sink.asFlux();
    }

    public void finish() {
        sink.tryEmitComplete();
    }


}
