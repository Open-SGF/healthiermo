package org.healthiermo.homepage;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FileUploadRejectionIntegrationTest {

    private static final long TWENTY_ONE_MB = 21L * 1024 * 1024;

    @TempDir
    static Path tempDir;

    @LocalServerPort
    private int port;

    @DynamicPropertySource
    static void overrideProperties(DynamicPropertyRegistry registry) {
        registry.add("app.public-data-root", () -> tempDir.toString());
    }

    @Test
    void upload21MbFileIsRejected() {
        byte[] content = new byte[(int) TWENTY_ONE_MB];

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("sections[0].file", new ByteArrayResource(content) {
            @Override
            public String getFilename() {
                return "huge.wav";
            }
        });
        body.add("sections[0].sectionKey", "OUTER_RING");
        body.add("sections[0].title", "Test Title");
        body.add("sections[0].text", "Test text");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(body, headers);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(new org.springframework.web.client.DefaultResponseErrorHandler() {
            @Override
            public boolean hasError(org.springframework.http.client.ClientHttpResponse response) {
                return false; // do not treat any status as an error
            }
        });

        ResponseEntity<String> response = restTemplate.postForEntity(
                "http://localhost:" + port + "/save/misc", request, String.class);

        int status = response.getStatusCode().value();
        assertNotEquals(200, status, "Expected oversized upload to be rejected, but got HTTP 200");
    }

}
