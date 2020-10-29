package com.github.tncrazvan.quarkus.tools.system;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.Map;
/**
 * Like the File class, an abstract representation of file and directory path names, with a few utility methods to quickly read and write data.
 * @author Razvan Tanase
 */
public class ServerFile extends File{
    private static final long serialVersionUID = 4567989494529454756L;

    /**
     * Like the File class, an abstract representation of file and directory path names, with a few utility methods to quickly read and write data.
     * @param parent parent file (directory).
     * @param file target file (a new handler will be created, instead of using the given one).
     */
    public ServerFile(final File parent, final File file) {
        super(parent, file.getAbsolutePath());
    }

    /**
     * Like the File class, an abstract representation of file and directory path names, with a few utility methods to quickly read and write data.
     * @param file target file (a new handler will be created, instead of using the given one).
     */
    public ServerFile(final File file) {
        super(file.getAbsolutePath());
    }

    /**
     * Like the File class, an abstract representation of file and directory path names, with a few utility methods to quickly read and write data.
     * @param filename full name of the target file.
     */
    public ServerFile(final URI filename) {
        super(filename);
    }

    /**
     * Like the File class, an abstract representation of file and directory path names, with a few utility methods to quickly read and write data.
     * @param filename full name of the target file.
     */
    public ServerFile(final String filename) {
        super(filename);
    }

    /**
     * Like the File class, an abstract representation of file and directory path names, with a few utility methods to quickly read and write data.
     * @param parent name of the parent file (directory).
     * @param filename full name of the target file.
     */
    public ServerFile(final String parent, final String filename) {
        super(parent, filename);
    }

    /**
     * Like the File class, an abstract representation of file and directory path names, with a few utility methods to quickly read and write data.
     * @param parent parent file (directory).
     * @param filename full name of the target file.
     */
    public ServerFile(final File parent, final String filename) {
        super(parent, filename);
    }

    /**
     * Get all contents of this file as a byte array.
     * @return contents
     * @throws FileNotFoundException
     * @throws IOException
     */
    public final byte[] read() throws IOException {
        return read(0,(int) this.length());
    }
    
    /**
     * Get a specific number of bytes of this file's contents, starting from a given offset.
     * @param offset offset from which to start reading.
     * @param length length of the output array.
     * @return contents
     * @throws IOException
     */
    public final byte[] read(int offset, int length) throws IOException {
        byte[] result;
        try (FileInputStream fis = new FileInputStream(this)) {
            fis.getChannel().position(offset);
            result = fis.readNBytes(length);
        }
        return result;
    }

    /**
     * Write a string to this file.
     * @param contents string to write.
     * @param charset charset to encode the contents in.
     * @throws IOException
     */
    public final void write(final String contents, final String charset) throws IOException {
        write(contents.getBytes(charset));
    }

    /**
     * Write a byte array to this file.
     * @param contents bytes to write.
     * @throws IOException
     */
    public final void write(final byte[] contents) throws IOException {
        try(FileOutputStream fos = new FileOutputStream(this)){
            fos.write(contents);
        }
    }

    /**
     * Get all information attributes of this file.
     * @return file information map.
     * @throws IOException
     */
    public final Map<String, Object> info() throws IOException {
        return info("*");
    }

    /**
     * Get a specific information field regarding this file.
     * @param selection
     * @return a map containing the metadata of this file.
     * @throws IOException
     */
    public final Map<String, Object> info(final String selection) throws IOException {
        return Files.readAttributes(this.toPath(), selection);
    }

    /**
     * Read all contents of this file as a String.
     * @param charset charset to use when decoding the contents.
     * @return contents of the file.
     * @throws IOException
     */
    public final String readString(final String charset) throws IOException {
        return Files.readString(this.toPath(),Charset.forName(charset));
    }
}