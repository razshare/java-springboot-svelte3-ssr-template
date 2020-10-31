package com.github.tncrazvan.svelte3ssr.template.services;

import com.github.tncrazvan.svelte3ssr.Svelte3SSR;
import com.github.tncrazvan.svelte3ssr.Svelte3SSRResult;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import org.graalvm.polyglot.Value;
import org.springframework.stereotype.Service;

@Service
public class Svelte3SSRService extends Svelte3SSR{
    public Svelte3SSRService() {
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
    public String page(String filename, String charset,HashMap<String,Object> props, String lang) throws IOException{
        Value renderedObject = this.render(this.compile(Files.readString(Path.of(filename), Charset.forName(charset))));
        Svelte3SSRResult result = new Svelte3SSRResult();
        result.head = renderedObject.getMember("head").asString();
        result.html = renderedObject.getMember("html").asString();
        result.css = renderedObject.getMember("css").getMember("code").asString();
        return result.build(lang);
    }
}
