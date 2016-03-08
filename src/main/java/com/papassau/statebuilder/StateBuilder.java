package com.papassau.statebuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;

/**
 *
 * @author Maxou
 */
@Configurable(preConstruction = true)
@Component
public class StateBuilder {
    
    @Autowired
    CsvLoader csvLoader;
    
    public void buildStates()
    {
        //access csv loaders attributes like symbols, reduction rules, etc...
        System.out.println(csvLoader);
    }
    
}
