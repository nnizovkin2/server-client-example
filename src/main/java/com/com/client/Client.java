package com.com.client;

import com.com.dto.LogLevel;
import com.com.dto.Message;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.netty.http.client.HttpClient;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class Client {
    public static void main(String[] args) throws InterruptedException {
        WebClient client = WebClient
                .builder()
                .baseUrl("http://localhost:8080")
                .clientConnector(new ReactorClientHttpConnector(HttpClient.newConnection()))
                .build();
        ParameterizedTypeReference<ServerSentEvent<String>> type = new ParameterizedTypeReference<>() {
        };
        AtomicLong counter = new AtomicLong();
        Duration d = Duration.ofMillis(2000);
        int cacheSize = 10;

        Flux.range(0, Integer.MAX_VALUE)
                .delayElements(d)
                .map(i -> {
                    String message = UUID.randomUUID().toString();
                    return new Message(LogLevel.values()[Math.abs(message.hashCode()) % 4],
                            message,
                            System.currentTimeMillis(),
                            i);
                })
                .subscribe((message) -> {
                    message.setTimestamp(System.currentTimeMillis());
                    while(true) {
                        long c = counter.get();
                        if(counter.compareAndSet(c, Math.max(c, message.getIndex()))) {
                            break;
                        }
                    }

                    client
                            .post()
                            .uri("/log")
                            .bodyValue(message)
                            .retrieve()
                            .bodyToMono(String.class)
                            .retryWhen(Retry.fixedDelay(Integer.MAX_VALUE, d)
                                    .filter(th -> message.getIndex() + cacheSize > counter.get()))
                            .subscribe();

                });

        Thread.sleep(10000000);
    }
}
