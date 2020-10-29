/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.tncrazvan.quarkus.tools.zip;


import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import com.github.tncrazvan.quarkus.tools.system.ServerFile;

import static com.github.tncrazvan.quarkus.tools.SharedObject.LOGGER;


/**
 * A zip archive that can be then saved to the file system.
 * @author Razvan Tanase
 */
 public class ZipArchive{
    private final String filename;
    private ServerFile file;
    private final ArrayList<ZipEntryData> entries;
    
    /**
     * A zip archive that can be then saved to the file system.
     * @param filename name of the archive (will be used to write the actual file to the file system when calling <b>ZipArchive::make()</b>).
     */
    public ZipArchive(final String filename) {
        this.filename = filename;
        entries = new ArrayList<>();
    }

    private class ZipEntryData {
        public ZipEntry entry;
        public byte[] data;

        public ZipEntryData(final ZipEntry entry, final byte[] data) {
            this.entry = entry;
            this.data = data;
        }

    }

    /**
     * Add a file to this archive.
     * @param filename file name of the new archive entry.
     * @param contents contents of the new archive entry.
     * @param charset charset to encode the contents in.
     * @throws IOException
     */
    public final void addEntry(final String filename, final String contents, final String charset) throws IOException {
        addEntry(filename, contents.getBytes(charset));
    }

    /**
     * Add a file to this archive.
     * @param filename file name of the new entry.
     * @param file a file from which to read the contents of the new archive entry.
     * @throws IOException
     */
    public final void addEntry(final String filename, final ServerFile file) throws IOException {
        addEntry(filename, file.read());
    }

    /**
     * Add a file to this archive.
     * @param filename file name of the new entry.
     * @param data contents of the new archive entry.
     * @throws IOException
     */
    public final void addEntry(final String filename, final byte[] data) {
        final ZipEntry e = new ZipEntry(filename);
        entries.add(new ZipEntryData(e, data));
    }

    /**
     * Write the zip archive to the file system.
     * @throws IOException
     */
    public final void make() throws IOException {
        file = new ServerFile(filename);
        try (ZipOutputStream out = new ZipOutputStream(new FileOutputStream(file))) {
            entries.forEach((e) -> {
                try {
                    out.putNextEntry(e.entry);
                    out.write(e.data, 0, e.data.length);
                    out.closeEntry();
                } catch (final IOException ex) {
                    LOGGER.log(Level.SEVERE, null, ex);
                }
            });
        }
    }

    /**
     * Get the file that identifies this zip archive.<br />
     * NOTE: calling this method before writing the archive to the file system using <b>ZipArchive::make()</b> is meaningless.
     * @return the file of the archive if <b>ZipArchive::make()</b> has actually set the file, otherwise null.
     */
    public final ServerFile getFile(){
        return file;
    }
}