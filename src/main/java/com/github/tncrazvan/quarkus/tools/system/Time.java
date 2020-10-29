/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.tncrazvan.quarkus.tools.system;

import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 *
 * @author Razvan Tanase
 */
public interface Time {
    /**
     * Get the current unix timestamp
     * @return unix timestamp
     */
    public static long now() {
        return now(ZoneId.systemDefault());
    }
    /**
     * Get the current unix timestamp
     * @param zone zone id
     * @return unix timestamp
     */
    public static long now(ZoneId zone) {
        return LocalDateTime.now().atZone(zone).toEpochSecond();
    }
    
    /**
     * Get the current unix timestamp plus a certain value in seconds.
     * @param zone zone id
     * @param value seconds to add
     * @return unix timestamp
     */
    public static long future(ZoneId zone, long value){
        return LocalDateTime.now().atZone(zone).plusSeconds(value).toEpochSecond();
    }

    /**
     * Get the current unix timestamp plus a certain value in seconds.
     * @param value seconds to add
     * @return unix timestamp
     */
    public static long future(long value){
        return future(ZoneId.systemDefault(), value);
    }
}
