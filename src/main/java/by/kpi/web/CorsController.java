package by.kpi.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Proxy for cross-domain requests.
 *
 * @author Pyotr Kukharenka
 * @since 23.05.2018
 */

@RestController
@RequestMapping("/second")
@CrossOrigin
@Slf4j
public class CorsController {

    @PostMapping("/")
    public ResponseEntity<Resource> getRecords(@RequestBody String dataUrl) {
        Resource resource = null;
        try {
            URL url = new URL(dataUrl);
            final String data = this.sendRequest(url);
            log.info("Request to url {} was send and response is accepted", dataUrl);
            resource = new ByteArrayResource(data.getBytes());
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(resource);
    }

    /**
     * The method directs the request to the specified url.
     *
     * @param url - specified url which must be proxied.
     * @return - url connection object.
     */
    private String sendRequest(final URL url) throws IOException {
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        StringBuilder content = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
            String inputLine;
            while ((inputLine = br.readLine()) != null) {
                content.append(inputLine);
            }
            log.info("Response success. Status code is - {}", con.getResponseCode());
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return content.toString();
    }

}
