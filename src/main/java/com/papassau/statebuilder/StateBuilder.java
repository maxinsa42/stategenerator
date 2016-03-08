package com.papassau.statebuilder;

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
public class StateBuilder {
    
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
    
    public void buildStates()
    {
        //access csv loaders attributes like symbols, reduction rules, etc...
        System.out.println(csvLoader);
    }
    
}
