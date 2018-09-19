package com.gamemaker;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	BasicConfigurator.configure();
    	Logger logger = Logger.getLogger(App.class);
    	logger.debug("Application started");
        System.out.println( "Hello World!" );
    }
}
