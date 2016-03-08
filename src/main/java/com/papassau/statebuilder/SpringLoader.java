package com.papassau.statebuilder;

import java.io.FileNotFoundException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * This software is meant to generate C++ source and header files for INSAs
 * lutin parser/interpreter originating the automatons CSV encored deductions.
 *
 * @author Maxou
 */
class SpringLoader
{
    public static void main(String[] args) throws FileNotFoundException
    {
        //initialize Spring's Bean Pool
        ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");

        
        StateBuilder builder = new StateBuilder();
        //StateBuilder builder = (StateBuilder) context.getBean("StateBuilder");
        builder.buildStates();
    }

}
