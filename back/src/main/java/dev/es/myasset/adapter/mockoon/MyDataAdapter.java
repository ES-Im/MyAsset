package dev.es.myasset.adapter.mockoon;

import dev.es.myasset.application.required.MyDataPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Slf4j
@Component
public class MyDataAdapter implements MyDataPort {

    private final RestClient restClient;

    public MyDataAdapter(@Value("${mydata.base-url}") String baseUrl) {
        log.info(baseUrl);
        this.restClient = RestClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    @Override
    public String ping() {
        return restClient.get()
                .uri("/users")
                .retrieve()
                .body(String.class);
    }
}
