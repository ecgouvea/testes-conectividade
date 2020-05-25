package com.ecgouvea.example.conectividade.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class EnvVarsController {

    private static final Logger log = LoggerFactory.getLogger(EnvVarsController.class);
    private RestTemplate restTemplate;

    @GetMapping(path="/variaveis-ambiente")
    public String listarVariaveisAmbiente(
            @RequestParam(required = false, defaultValue = "<br>") String lineTermination
    ) {
        StringBuilder values = new StringBuilder();
        Map<String, String> env = System.getenv();

        LinkedHashMap<String, String> collect =
                env.entrySet().stream()
                        .sorted(Map.Entry.comparingByKey())
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                                (oldValue, newValue) -> oldValue, LinkedHashMap::new));

        collect.forEach((k, v) -> values.append(k + "=" + v + lineTermination));
        return values.toString();
    }

}
