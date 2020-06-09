package com.ecgouvea.example.conectividade.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.*;
import java.util.Date;

@RestController
public class SocketConnectController {

    @GetMapping("/teste/socket")
    public String testeSocket(
            @RequestParam(required = false) String host,
            @RequestParam(required = false) Integer port
    ) throws Exception {
        String values = "";
        String s = null;

        try (
                Socket clientSocket = new Socket(host, port);
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        ) {
           clientSocket.setSoTimeout(30000);
           values = "Connected. Read line: ";
           char[] cbuf = new char[64];
           while (in.read(cbuf) > 0) {
               s = String.valueOf(cbuf);
               System.out.println(s);
               values += s;
           }
        } catch (Exception e) {
            e.printStackTrace();
            values = e.getMessage();
        }

        return new Date().toString() + "<br>Result:<br>" + values;
    }

}
