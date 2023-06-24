package com.example.webfluxssetest.wait;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class WaitService {
    WaitRepository waitRepository;

    public Flux<WaitStatus> connect() {
        waitRepository.save(new WaitStatus(false, "asdf"));
        return waitRepository.findAll();
    }

    public void add() {
        waitRepository.save(new WaitStatus(true, "asdfasdf"));
        waitRepository.finish();
    }
}
