package com.papassau.statebuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Vector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.SystemEnvironmentPropertySource;
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
    @Value("${leading.identifier}")
    private String generatorKey;

    @Value("${switch.marker}")
    private String switchMarker;

	@Value("${defines.marker}")
	private String definesMarker;

    @Value("${target.dir}")
    private String targetDirectory;

    @Value("${reduction.marker}")
    private String reductionMarker;

    @Value("${anti.reduction.message}")
    private String antiReductionMessage;

    @Value("${reductions.conf}")
    private String reductionsConfFile;

    @Value("${state.template.header}")
    private String templateStateHeader;
    
    @Value("${state.template.cpp}")
    private String templateStateCpp;

    @Value("${print.reductions}")
    private boolean printReductionTable;
    
    @Value("${state.template.identifier}")
    private String stateTemplateIdentifier;

	@Value("${header.all.states}")
	private String headerAllStates;

	Vector<String> states;

    @Autowired
    CsvLoader csvLoader;

    private Map<String, String> stateReductions;

    public void buildStates() throws FileNotFoundException
    {
		states = new Vector<String>();

        //access csv loaders attributes like symbols, reduction rules, etc...
        if (printReductionTable)
            System.out.println(csvLoader);

        //import redction rules (file)
        String reductions = new Scanner(new File(reductionsConfFile)).useDelimiter("\\Z").next();
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

        //genereate header and cpp files for each state specified (all but first line in csv)
        for (String state : csvLoader.getTransitions().keySet()) {
            generateHeader(state);
            generateCpp(state, csvLoader.getTransitions().get(state), csvLoader.getSymbols());
			states.add(state);
        }
        
        System.out.println("\n\n\n***********************************************");
        System.out.println("* Auto generation done. Your files are here:  *");
        System.out.println("*    -> "+targetDirectory+generatorKey+"* [.cpp / .h]     *");
        System.out.println("***********************************************");
    }

    private void generateHeader(String state) throws FileNotFoundException
    {
        // load header template
        String headerTemplate = new Scanner(new File(templateStateHeader)).useDelimiter("\\Z").next();

        // substitute keywords
        headerTemplate = headerTemplate.replaceAll(stateTemplateIdentifier, generatorKey + state);
        headerTemplate = headerTemplate.replaceAll(stateTemplateIdentifier.toUpperCase(), (generatorKey + state).toUpperCase());

        // write header to disk
        PrintWriter out = new PrintWriter(targetDirectory + generatorKey + state + ".h");
        out.println(headerTemplate);
        out.close();
    }

    public void generateHeaderAllStates() throws FileNotFoundException
    {
		// load header template
		String allHeaderStateTemplate = new Scanner(new File(headerAllStates)).useDelimiter("\\Z").next();

		//create swich case string that replaces the template key
		StringBuilder definesBuilder = new StringBuilder("");

		//iterate over all states
		for(String state : states)
		{
			definesBuilder.append("#include \"" + generatorKey + state + ".h\"\n");
		}

		System.out.println(definesBuilder.toString());
		System.out.println(definesMarker);

		//finaly replace the marker (something like: "### CODE GOES HERE ###") by the previously created stringBuilder content
		allHeaderStateTemplate = allHeaderStateTemplate.replaceAll(definesMarker, definesBuilder.toString());

		// write cpp file to disk
		PrintWriter out = new PrintWriter("states/all-states.h");
		out.println(allHeaderStateTemplate);
		out.close();
    }

    private void generateCpp(String state, String[] targetStates, String[] symbols) throws FileNotFoundException
    {
        // load header template
        String cppTemplate = new Scanner(new File(templateStateCpp)).useDelimiter("\\Z").next();

        // substitute keywords
        cppTemplate = cppTemplate.replaceAll(stateTemplateIdentifier, generatorKey + state);

        //create swich case string that replaces the template key
        StringBuilder switchBuilder = new StringBuilder("");
        switchBuilder.append("switch ((int)*s) {\n");

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
            }
            else if (!targetStates[symbolCounter].equals(""))
                //forward to other state
                switchBuilder.append("\t\t\tautomate.Decalage(s, new ").append(generatorKey).append(targetStates[symbolCounter]).append(");\n");
            switchBuilder.append("\t\t\tbreak;\n");

        }
        switchBuilder.append("\t\tdefault:\n\t\t\tbreak;\n\t}\n\treturn false;\n");

        //finaly replace the marker (something like: "### CODE GOES HERE ###") by the previously created stringBuilder content
        cppTemplate = cppTemplate.replace(switchMarker, switchBuilder.toString());

        //Now substitute reductions
        if (stateReductions.containsKey(state))
            cppTemplate = cppTemplate.replaceAll(reductionMarker, stateReductions.get(state));
        else
            cppTemplate = cppTemplate.replaceAll(reductionMarker, antiReductionMessage);

        // write cpp file to disk
        PrintWriter out = new PrintWriter(targetDirectory + generatorKey + state + ".cpp");
        out.println(cppTemplate);
        out.close();
    }

}
