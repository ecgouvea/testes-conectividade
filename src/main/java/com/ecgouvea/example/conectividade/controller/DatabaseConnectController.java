package com.ecgouvea.example.conectividade.controller;

import org.springframework.web.bind.annotation.*;

import javax.annotation.PreDestroy;
import java.sql.*;
import java.util.Date;

@RestController
public class DatabaseConnectController {

    private Connection oracleConn = null;

    public DatabaseConnectController() throws SQLException {
        String url = "jdbc:oracle:thin:@//efbrmtzvlx216.ultra.corp:1522/EFPDVD.ULTRA.CORP?user=dbcsi_p2k&password=dbcsi_p2k";
        this.oracleConn = DriverManager.getConnection(url);
        System.out.printf("\n\n\n\n\n [%s] Construtor this.oracleConn \n\n\n\n\n ", new Date().toString());
    }

    @GetMapping("/teste/postgresql")
    public String testePostgreSQL(@RequestParam(required = false) String connectionString,
                        @RequestParam(required = false) String sqlQuery,
                        @RequestParam(required = false, defaultValue = "1") Integer columnIndex
    ) throws Exception {
        //String url = "jdbc:postgresql://dev-simuladortributario.ctabjq8ajjko.us-east-1.rds.amazonaws.com:5432/simuladortributario?user=simtribRoot12&password=123Mudar12";
        String url = null;
        String sql = "* from public.tabela_teste";
        String values = "";

        if (connectionString != null) {
            url = connectionString;
        } else {
            url = "jdbc:postgresql://localhost:15432/simuladortributario?user=simtribRoot12&password=123Mudar12";
        }

        if (sqlQuery != null) {
            sql = sqlQuery;
        }

        try (
                Connection conn = DriverManager.getConnection(url);
                PreparedStatement stmt = conn.prepareStatement("SELECT " + sql);
                ResultSet rs = stmt.executeQuery()
        ) {
            while (rs.next()) {
                values += rs.getString(columnIndex) + "<br>";
            }
        } catch (SQLException e) {
            System.out.println(e);
            values = e.getMessage();
        }

        return new Date().toString() + "<br>Result:<br>" + values;
    }


    @GetMapping("/teste/sqlserver")
    public String testeSqlServer(
            @RequestParam(required = false) String host,
            @RequestParam(required = false) String instance,
            @RequestParam(required = false) String query,
            @RequestParam(required = false, defaultValue = "1") Integer columnIndex
    ) {
        String result = "";
        String values = "";
        String connectionUrl =
                "jdbc:sqlserver://" + host + "\\" + instance + ";"
                        + "database=DBO_CRE;"
                        + "user=userIntegraBematech;"
                        + "password=userintegrabematech;"
                        + "loginTimeout=30;";

        try (
                Connection conn = DriverManager.getConnection(connectionUrl);
                PreparedStatement stmt = conn.prepareStatement("SELECT " + query);
                ResultSet rs = stmt.executeQuery()
        ) {
            while (rs.next()) {
                values += rs.getString(columnIndex) + "<br>";
            }
            result = new Date().toString() + "<br>Result:<br>" + values;
            result += "conn.getClientInfo().keySet: " + conn.getClientInfo().keySet() + "<hr>";
            result += "conn.getClientInfo: " + conn.getClientInfo() + "<hr>";
            result += "conn.getMetaData: " + conn.getMetaData() + "<hr>";
        }
        // Handle any errors that may have occurred.
        catch (SQLException e) {
            e.printStackTrace();
            result = e.getMessage();
        }

        return result;
    }


    @GetMapping("/teste/oracle")
    public String testeOracle(@RequestParam(required = false) String connectionString,
                                  @RequestParam(required = false) String sqlQuery,
                                  @RequestParam(required = false, defaultValue = "1") Integer columnIndex
    ) throws Exception {
        //String url = "jdbc:postgresql://dev-simuladortributario.ctabjq8ajjko.us-east-1.rds.amazonaws.com:5432/simuladortributario?user=simtribRoot12&password=123Mudar12";
        String url = null;
        String sql = "* from dual";
        String values = "";

        if (connectionString != null) {
            url = connectionString;
        } else {
            url = "jdbc:oracle:thin:@//efbrmtzvlx216.ultra.corp:1522/EFPDVD.ULTRA.CORP?user=dbcsi_p2k&password=dbcsi_p2k";
        }

        if (sqlQuery != null) {
            sql = sqlQuery;
        }

        System.out.printf("\n\n\n\n\n [%s] this.oracleConn == null? \n\n\n\n\n ", new Date().toString());
        if (this.oracleConn == null) {
            System.out.printf("\n\n\n\n\n [%s] this.oracleConn == null: true \n\n\n\n\n ", new Date().toString());
            this.oracleConn = DriverManager.getConnection(url);
        } else {
            System.out.printf("\n\n\n\n\n [%s] this.oracleConn == null: false \n\n\n\n\n ", new Date().toString());
        }

        try (
                PreparedStatement stmt = this.oracleConn.prepareStatement("SELECT " + sql);
                ResultSet rs = stmt.executeQuery()
        ) {
            while (rs.next()) {
                values += rs.getString(columnIndex) + "<br>";
            }
        } catch (SQLException e) {
            System.out.println(e);
            values = e.getMessage();
        }

        return new Date().toString() + "<br>Result:<br>" + values;
    }


    @PreDestroy
    public void destroy() {
        System.out.printf(
                "\n\n\n\n\n\n\n\n [%s] Callback triggered - @PreDestroy. \n\n\n\n\n\n\n\n", new Date().toString());
        try {
            this.oracleConn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
