package com.github.tncrazvan.quarkus.tools.system;

import java.io.File;

import com.github.tncrazvan.quarkus.tools.action.CompleteAction;


/**
 * This interface contains few methods that manipulate the file system.
 * @author Razvan Tanase
 */
public interface FileSystem {
    /**
     * Explores the contents of a directory and calls a CompleteAction functional interface while iterating files.<br />
     * NOTE: returns immediately without warning if the provided resource is not a valid directory.<br />
     * If the CompleteAction callback returns false, the iteration will stop.
     * @param dir directory to explore
     * @param recursive is true, then the method will be evaluated recursively for each subgroups of directories inside the provided directory.
     * @param action the action to execute for each encountered file.
     */
    static void explore(final String dir, final boolean recursive, final CompleteAction<Boolean,ServerFile> action) {
        explore(new ServerFile(dir), recursive, action);
    }

    /**
     * Explore the contents of a directory and calls a CompleteAction functional interface while iterating files.<br />
     * NOTE: returns immediately without warning if the provided resource is not a valid directory.<br />
     * If the CompleteAction callback returns false, the iteration will stop.
     * @param dir directory to explore.
     * @param recursive is true, then the method will be evaluated recursively for each subgroups of directories inside the provided directory.
     * @param action the action to execute for each encountered file.
     */
    static void explore(final ServerFile dir, final boolean recursive, final CompleteAction<Boolean,ServerFile> action) {
        if(!dir.isDirectory()) return;
        final File[] files = dir.listFiles();
        if(files != null){
            //lookup directories
            for (final File file : files) {
                ServerFile f = new ServerFile(file);
                if(file.isDirectory() && action.callback(f) && recursive){
                    explore(f, recursive, action);
                }
            }
            //lookup files
            for (final File file : files) {
                ServerFile f = new ServerFile(file);
                if(!file.isDirectory() && action.callback(f) && recursive){
                    explore(f, recursive, action);
                }
            }
        }
    }
    
    /**
     * Removes the given directory and all its contents recursively.
     * @param directory directory to be removed.
     */
    public static void rmdir(final File directory) {
        final File[] files = directory.listFiles();
        if (files != null) { // some JVMs return null for empty dirs
            for (final File f : files) {
                if(f.isDirectory()) {
                    rmdir(f);
                } else {
                    f.delete();
                }
            }
        }
        directory.delete();
    }
}
