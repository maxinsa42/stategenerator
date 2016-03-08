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
    private final String switchMarker = "### SWITCH CODE GOES HERE ###";

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
            generateCpp(state, csvLoader.getTransitions().get(state), csvLoader.getSymbols());
        }
    }

    private void generateHeader(String state) throws FileNotFoundException
    {
        // load header template
        String headerTemplate = new Scanner(new File("templates/EtatTemplate.h")).useDelimiter("\\Z").next();

        // substitute keywords
        headerTemplate = headerTemplate.replaceAll("EtatTemplate", generatorKey + state);
        headerTemplate = headerTemplate.replaceAll("ETATTEMPLATE", (generatorKey + state).toUpperCase());

        // write header to disk
        PrintWriter out = new PrintWriter("/tmp/" + generatorKey + state + ".h");
        out.println(headerTemplate);
        out.close();
    }

    private void generateCpp(String state, String[] targetStates, String[] symbols) throws FileNotFoundException
    {
        // load header template
        String cppTemplate = new Scanner(new File("templates/EtatTemplate.cpp")).useDelimiter("\\Z").next();

        // substitute keywords
        cppTemplate = cppTemplate.replaceAll("EtatTemplate", generatorKey + state);

        //create swich case string that replaces the template key
        StringBuilder switchBuilder = new StringBuilder("");
        switchBuilder.append("switch (*s) {\n");

        //iterate over all symbols
        for (int symbolCounter = 0; symbolCounter < symbols.length; symbolCounter++) {
            //add case for each possible symbol - this is necessary because cpp does not offer a default case syntax
            switchBuilder.append("case ").append(symbols[symbolCounter]).append(":\n");

            //add line if and only if a state change ios required (this is to say if the field in the array of following states in NOT empty)
            if (!targetStates[symbolCounter].equals(""))
                switchBuilder.append("NON TRIVIAL CASE\n");
        }
        switchBuilder.append("}\n");

        //finaly replace the marker (something like: "### CODE GOES HERE ###") by the previously created stringBuilder content
        cppTemplate = cppTemplate.replace(switchMarker, switchBuilder.toString());

        // write cpp file to disk
        PrintWriter out = new PrintWriter("/tmp/" + generatorKey + state + ".cpp");
        out.println(cppTemplate);
        out.close();
    }

}
