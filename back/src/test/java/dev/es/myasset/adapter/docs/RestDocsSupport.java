package dev.es.myasset.adapter.docs;


import com.fasterxml.jackson.databind.ObjectMapper;
import dev.es.myasset.adapter.webapi.ExceptionAdvice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.charset.StandardCharsets;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;

@ExtendWith(RestDocumentationExtension.class)
public abstract class RestDocsSupport {
    protected abstract Object initController();

    protected MockMvc mockMvc;
    protected ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setup(RestDocumentationContextProvider provider) {

        this.mockMvc = MockMvcBuilders.standaloneSetup(initController())
                .setMessageConverters(
                        new StringHttpMessageConverter(StandardCharsets.UTF_8),
                        new MappingJackson2HttpMessageConverter(objectMapper)
                )
                .setControllerAdvice(new ExceptionAdvice())
                .apply(documentationConfiguration(provider))
                .build();
    }



}
