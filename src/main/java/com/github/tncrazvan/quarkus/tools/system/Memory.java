package com.github.tncrazvan.quarkus.tools.system;

import com.github.tncrazvan.quarkus.tools.SharedObject;

/**
 * This interface contains a few methods that provide details regarding the JVM runtime resources.
 * @author Razvan Tanase
 */
public interface Memory {
    /**
     * Get the size of the free memory of the current JVM.
     * @return number of bytes that are free to be used.
     */
    public static long getFreeMemory(){
        return SharedObject.RUNTIME.freeMemory();
    }

    /**
     * Get the size of the total memory of the current JVM.
     * @return number of bytes that are currently being allocated to the JVM.
     */
    public static long getTotalMemory(){
        return SharedObject.RUNTIME.totalMemory();
    }
    /**
     * Returns the maximum amount of memory that the Java virtual machine will attempt to use. If there is no inherent limit then the value.
     * @return the maximum amount of memory that the virtual machine will attempt to use, measured in bytes.
     */
    public static long getMaxMemory(){
        return SharedObject.RUNTIME.maxMemory();
    }

    /**
     * Get the total free memory of the JVM.
     * @return the total number of bytes that are free to be used in the JVM.
     */
    public static long getTotalFreeMemory(){
        return (getFreeMemory() + (getMaxMemory() - getTotalMemory())) / 1024;
    }
}
