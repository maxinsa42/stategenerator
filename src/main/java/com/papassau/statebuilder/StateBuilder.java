package com.papassau.statebuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Stream;
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
    //@Value("${test.value}")
    //int testValue;
    
    // string added prior to all generated files' names
    private final String generatorKey = "GeneratedState";
    private final String switchMarker = "//### SWITCH CODE GOES HERE ###//";
    private final String reductionMarker = "//### REDUCTION CODE GOES HERE ###//";

    @Autowired
    CsvLoader csvLoader;

    private Map<String, String> stateReductions;

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
        //System.out.println("Value = "+testValue);
        
        
        
        //access csv loaders attributes like symbols, reduction rules, etc...
        System.out.println(csvLoader);

        //import redction rules (file)
        String reductions = new Scanner(new File("templates/reductions.conf")).useDelimiter("\\Z").next();
        String[] reductionsByState = reductions.split("###");

        //We have to start at index 1, because everything in fromt of the first ### is unusable
        stateReductions = new LinkedHashMap<>();
        for (int i = 1; i < reductionsByState.length; i++) {
            String reduction = reductionsByState[i];
            int keyValueSplitter = reduction.indexOf("\n");
            String reductionKey = reduction.substring(0, keyValueSplitter);
            String reductionValue = reduction.substring(keyValueSplitter + 1, reduction.length());
            stateReductions.put(reductionKey, reductionValue);
        }

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
            switchBuilder.append("\t\tcase ").append(symbols[symbolCounter]).append(":\n");

            //add line if and only if a state change or reduction is required (this is to say if the field in the array of following states in NOT empty)
            if (targetStates[symbolCounter].startsWith("r")) {
                //reduction
                int reductionAmount = Integer.parseInt(targetStates[symbolCounter].substring(1));
                //char numberAsChar = targetStates[symbolCounter].charAt(1);
                //Integer.parseInt();
                switchBuilder.append("\t\t\tautomate.Reduction(").append(reductionAmount).append(");\n");
                switchBuilder.append("\t\t\tbreak;\n");
            }
            else if (!targetStates[symbolCounter].equals("")) {
                //forward to other state
                switchBuilder.append("\t\t\tautomate.Decalage(s, new ").append(generatorKey).append(targetStates[symbolCounter]).append(")\n");
                switchBuilder.append("\t\t\tbreak;\n");
            }
        }
        switchBuilder.append("\t}\n\treturn false;\n");

        //finaly replace the marker (something like: "### CODE GOES HERE ###") by the previously created stringBuilder content
        cppTemplate = cppTemplate.replace(switchMarker, switchBuilder.toString());

        //Now substitute reductions
        if (stateReductions.containsKey(state))
            cppTemplate = cppTemplate.replaceAll(reductionMarker, stateReductions.get(state));

        // write cpp file to disk
        PrintWriter out = new PrintWriter("/tmp/" + generatorKey + state + ".cpp");
        out.println(cppTemplate);
        out.close();
    }

}
