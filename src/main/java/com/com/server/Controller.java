package com.com.server;

import com.com.dto.LogLevel;
import com.com.dto.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("")
public class Controller {
    private @Value("${log.level}")
    String logLevel;

    @PostMapping("/log")
    private Mono<String> log(@RequestBody Message m) {
        if(m.getLogLevel().get() > LogLevel.valueOf(logLevel).get()) {
            System.out.println(m);
        }

        return Mono.just("");
    }
}
