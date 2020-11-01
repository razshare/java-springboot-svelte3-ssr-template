package com.github.tncrazvan.svelte3ssr.template.services;

import com.github.tncrazvan.springboot.tools.Strings;
import com.github.tncrazvan.springboot.tools.system.ServerFile;
import com.github.tncrazvan.svelte3dom.Svelte3DOM;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import org.springframework.stereotype.Service;

@Service
public class Svelte3DOMService extends Svelte3DOM{
    public Svelte3DOMService(){
        super(Path.of(System.getProperty("user.dir")));
    }
    
    public String page(String filename) throws IOException{
        return page(filename, "UTF-8");
    }
    public String page(String filename, String charset) throws IOException{
        return page(filename, charset, new HashMap<>());
    }
    public String page(String filename, String charset,HashMap<String,Object> props) throws IOException{
        return page(filename, charset, props, "en");
    }
    
    public String page(String filename, String charset, HashMap<String,Object> props, String lang) throws IOException{
        String id = Strings.uuid();
        this.bundle(id,this.compile(new ServerFile(filename).readString(charset)));
        return 
        "<!DOCTYPE html>\n" +
        "<html lang=\""+lang+"\">\n" +
        "<head>\n" +
        "	<meta charset='utf-8'>\n" +
        "	<meta name='viewport' content='width=device-width,initial-scale=1'>\n" +
        "\n" +
        "	<title>Svelte app</title>\n" +
        "\n" +
        "	<script defer src='/@bundles?id="+id+"'></script>\n" +
        "</head>\n" +
        "\n" +
        "<body>\n" +
        "</body>\n" +
        "</html>";
    }
}
