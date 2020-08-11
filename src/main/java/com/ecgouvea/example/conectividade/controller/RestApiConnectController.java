package com.ecgouvea.example.conectividade.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;

@RestController
public class RestApiConnectController {

    private static final Logger log = LoggerFactory.getLogger(RestApiConnectController.class);
    private RestTemplate restTemplate;

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        restTemplate = builder.build();
        return restTemplate;
    }

    @GetMapping("/teste/restapi")
    public String testeRestApi(
            @RequestParam(
                    required = false,
                    defaultValue = "https://gturnquist-quoters.cfapps.io/api/random"
            ) String url,
            @RequestParam(required = false) String sqlQuery,
            @RequestParam(required = false, defaultValue = "1") Integer columnIndex
    ) throws Exception {
        log.info("Chamando URL: " + url);
//        String response = restTemplate.getForObject(url, String.class);

        HttpHeaders headers = new HttpHeaders();
//		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
//		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("User-Agent", "curl/7.49.0");
		HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
		//ResponseEntity<Map> r4 = rest.exchange("http://pokeapi.co/api/v2/type/3/", HttpMethod.GET, entity, Map.class);
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        log.info("Response: " + response.getBody());
        String values = response.getBody();

        return new Date().toString() + "<br>Result:<br>" + values;
    }

    @GetMapping("/teste/getfile")
    public String testeRestApiGetFile(
            @RequestParam(
                    required = false,
                    defaultValue = "https://<mydomain-myorigin>.cloudfront.net/<my file>"
            ) String url
    ) throws Exception {
        log.info("Chamando URL: " + url);

        HttpHeaders headers = new HttpHeaders();
        //headers.set("User-Agent", "curl/7.49.0");
        URLConnection urlConnection = new URL(url).openConnection();
        urlConnection.setRequestProperty ("Authorization", "Basic <your user:pwd base64 here>");

        InputStream in = urlConnection.getInputStream();
        char[] buf = new char[1024];
        try (InputStreamReader inputStreamReader = new InputStreamReader(in)) {
          while (inputStreamReader.read(buf) > 0) {
              System.out.println(buf);
          }
        }

        return new Date().toString() + "<br>Result:<br>";
    }
}
