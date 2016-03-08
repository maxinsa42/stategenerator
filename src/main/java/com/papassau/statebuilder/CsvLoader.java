package com.papassau.statebuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import org.springframework.stereotype.Component;

/**
 *
 * @author Maxou
 */
@Component
public class CsvLoader
{

    //defined states as in order of csv appearance
    private final String[] symbols;

    public String[] getSymbols()
    {
        return symbols;
    }

    public Map<String, String[]> getTransitions()
    {
        return Collections.unmodifiableMap(transitions);
    }

    //for each state (key) and read symbol (array position) the following state according to automaton
    private final Map<String, String[]> transitions;

    public CsvLoader(String csvPath) throws FileNotFoundException
    {
        transitions = new LinkedHashMap<>();
        Scanner sc = new Scanner(new File(csvPath));

        //Extract symbols of first line (splice at each ",")
        String[] rawSymbols = sc.next().split(",", -1);
        //Then discard first position (commentary  character "#")
        symbols = Arrays.copyOfRange(rawSymbols, 1, rawSymbols.length - 1);

        //Now that the symbals are loaded we need to extract all deuctions. Each line represents one deduction rule.
        while (sc.hasNext()) {
            //read line, splice at each "," and discard first position
            String[] splicedLine = sc.next().split(",", -1);
            String[] resolvedStates = Arrays.copyOfRange(splicedLine, 1, symbols.length - 1);

            //then store result in map
            transitions.put(splicedLine[0], resolvedStates);
        }
        sc.close();
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder("");

        //print symbols
        sb.append("Symbols: \n");
        for (String symbol : symbols) {
            sb.append(String.format("%8s | ", symbol));
        }
        sb.append("\n");

        //print rules
        sb.append("Deductions: \n");
        for (String originState : transitions.keySet()) {
            String[] followingStates = transitions.get(originState);
            for (String followingState : followingStates) {
                sb.append(String.format("%8s | ", followingState));
            }
            sb.append("\n");
        }

        return sb.toString();
    }

}
