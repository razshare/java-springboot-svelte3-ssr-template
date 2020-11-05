package com.github.tncrazvan.svelte3ssr.template.services;

import com.github.tncrazvan.svelte3ssr.Svelte3SSR;
import com.github.tncrazvan.svelte3ssr.Svelte3SSRResult;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.UUID;
import org.graalvm.polyglot.Value;
import org.springframework.stereotype.Service;

@Service
public class Svelte3SSRService extends Svelte3SSR{
    public Svelte3SSRService() {
        super(Path.of(System.getProperty("user.dir")));
    }
    
    private String uuid(){
        return UUID.randomUUID().toString().replace("-", "");
    }
    
    public String page(Svelte3DOMService dom, String filename) throws IOException{
        return page(dom, filename, "UTF-8");
    }
    public String page(Svelte3DOMService dom, String filename, String charset) throws IOException{
        return page(dom, filename, charset, new HashMap<>());
    }
    public String page(Svelte3DOMService dom, String filename, String charset,HashMap<String,Object> props) throws IOException{
        return page(dom, filename, charset, props, "en");
    }
    public String page(Svelte3DOMService dom, String filename, String charset,HashMap<String,Object> props, String lang) throws IOException{
        String contents = Files.readString(Path.of(filename), Charset.forName(charset));
        Value renderedObject = this.render(this.compile(contents));
        Svelte3SSRResult result = new Svelte3SSRResult();
        result.head = renderedObject.getMember("head").asString();
        result.head += "<script defer type='module'>"+dom.bundle(dom.compile(contents))+"</script>";
        result.html = renderedObject.getMember("html").asString();
        result.css = renderedObject.getMember("css").getMember("code").asString();
        return result.build(lang);
    }
}
