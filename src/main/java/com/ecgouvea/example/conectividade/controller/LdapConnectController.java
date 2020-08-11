package com.ecgouvea.example.conectividade.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

@RestController
public class LdapConnectController {

    @GetMapping("/teste/ldap")
    public String testeLdap(
            @RequestParam(required = false) String host,
            @RequestParam(required = false) Integer port
    ) throws Exception {
        String values = "";
        String s = null;

        try {
            main();
        } catch (Exception e) {
            e.printStackTrace();
            values = e.getMessage();
        }

        return new Date().toString() + "<br>Result:<br>" + values;
    }





    public static String ldapUri = "ldap://<myserver>.corp:389";
    public static String usersContainer = "CN=my common name,OU=my ou,OU=Services,OU=Special Users,DC=my server,DC=corp";

    public static void main(/*String args[]*/) {

//        if (args.length != 2) {
//            System.out.println("Usage: test userName password");
//            return;
//        }

        String username = "MYDOMAIN\\eduardo.cgouvea"; //args[0];
        String password = "mypwd"; //args[1];

        Hashtable env = new Hashtable();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, ldapUri);
        env.put(Context.SECURITY_PRINCIPAL, username);
        env.put(Context.SECURITY_CREDENTIALS, password);
        try {
            DirContext ctx = new InitialDirContext(env);
            SearchControls ctls = new SearchControls();
            String[] attrIDs = { "cn" };
            ctls.setReturningAttributes(attrIDs);
//            ctls.setSearchScope(SearchControls.ONELEVEL_SCOPE);
            ctls.setSearchScope(SearchControls.SUBTREE_SCOPE);

            NamingEnumeration answer = ctx.search(usersContainer, "(&(objectclass=group))", ctls);

            System.out.println("\n\n\n\n\nanswer.hasMore()? " + answer.hasMore());

            while (answer.hasMore()) {
                SearchResult rslt = (SearchResult) answer.next();
                Attributes attrs = rslt.getAttributes();
                System.out.println(attrs.get("cn"));
            }

            ctx.close();

        } catch (NamingException e) {
            e.printStackTrace();
        }

    }

}
