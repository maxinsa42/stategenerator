package com.papassau.statebuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 *
 * @author Maxou
 */
@Configurable(preConstruction = true)
@Component
public class StateBuilder
{

    // string added prior to all generated files' names
    private final String generatorKey = "GeneratedState";

    @Autowired
    CsvLoader csvLoader;

    //@Value("${test.template}")
    //String testTemplate;
    public CsvLoader getCsvLoader()
    {
        return csvLoader;
    }

    public void setCsvLoader(CsvLoader csvLoader)
    {
        this.csvLoader = csvLoader;
    }

    public void buildStates() throws FileNotFoundException
    {
        //access csv loaders attributes like symbols, reduction rules, etc...
        System.out.println(csvLoader);

        for (String state : csvLoader.getTransitions().keySet()) {

            // HEADER FILE
            generateHeader(state);

            //CPP FILE
            generateCpp(state, csvLoader.getTransitions().get(state));
        }
    }

    private void generateHeader(String state) throws FileNotFoundException
    {
        // load header template
        String headerTemplate = new Scanner(new File("templates/EtatTemplate.h")).useDelimiter("\\Z").next();

        // substitute keywords
        headerTemplate = headerTemplate.replaceAll("EtatTemplate", generatorKey + state);
        headerTemplate = headerTemplate.replaceAll("ETATTEMPLATE", (generatorKey + state).toUpperCase());

        System.out.println(headerTemplate);

        // write header to disk
        PrintWriter out = new PrintWriter("/tmp/" + generatorKey + state + ".h");
        out.println(headerTemplate);
        out.close();
    }

    private void generateCpp(String state, String[] get) throws FileNotFoundException
    {
        // load header template
        String cppTemplate = new Scanner(new File("templates/EtatTemplate.cpp")).useDelimiter("\\Z").next();

        // substitute keywords
        cppTemplate = cppTemplate.replaceAll("EtatTemplate", generatorKey + state);

        // write header to disk
        PrintWriter out = new PrintWriter("/tmp/" + generatorKey + state + ".cpp");
        out.println(cppTemplate);
        out.close();
    }

}
