package com.ecgouvea.example.conectividade.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class RestApiConnectController {

    private static final Logger log = LoggerFactory.getLogger(RestApiConnectController.class);
    private RestTemplate restTemplate;

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        restTemplate = builder.build();
        return restTemplate;
    }

    @GetMapping(path={"/api/HealthCheck", "/api/healthCheck", "/api/healthcheck"})
    public String healthCheck() {
        return "true";
    }

    @GetMapping(path="/variaveis-ambiente")
    public String listarVariaveisAmbiente() {
        StringBuilder values = new StringBuilder();
        Map<String, String> env = System.getenv();

        LinkedHashMap<String, String> collect =
                env.entrySet().stream()
                        .sorted(Map.Entry.comparingByKey())
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                                (oldValue, newValue) -> oldValue, LinkedHashMap::new));

        collect.forEach((k, v) -> values.append(k + "=" + v + "<br>"));
        return values.toString();
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
    
}
