package com.github.tncrazvan.quarkus.tools;

import java.time.ZoneId;
import java.util.Calendar;
import java.util.Base64;
import java.util.Date;
import java.util.logging.Logger;


/**
 * 
 * @author Razvan Tanase
 */
public abstract class SharedObject implements Strings {
    private SharedObject(){}
    //PROCESS BUILDER
    public static final ProcessBuilder PROCESS_BUILDER = new ProcessBuilder();
    
    //RUNTIME
    public static final Runtime RUNTIME = Runtime.getRuntime();

    //LOCALE & DATES
    public static final Calendar CALENDAR = Calendar.getInstance();
    public static final Date DATE = new Date();
    public static final ZoneId londonTimezone = ZoneId.of("Europe/London");

    //LOGGING
    public static final Logger LOGGER = Logger.getLogger(SharedObject.class.getName());

    //ENCODING & DECODING
    public static final Base64.Encoder BASE64_ENCODER = Base64.getEncoder();
    public static final Base64.Decoder BASE64_DECODER = Base64.getDecoder();
	
}