/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.tncrazvan.svelte3ssr.template.services;

import com.github.tncrazvan.quarkus.tools.system.ServerFile;
import com.github.tncrazvan.svelte3ssr.Svelte3SSR;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import org.springframework.stereotype.Service;

@Service
public class Svelte3SSRService extends Svelte3SSR{
    public Svelte3SSRService() {
        super(Path.of(System.getProperty("user.dir")));
    }
    
    /**
     * Render as Svelte component as a page
     * @param filename name of the file to render
     * @param charset character set to use
     * @return the rendered page as an html string ready to be served
     * @throws IOException 
     */
    public String page(String filename, String charset) throws IOException{
        return page(filename, charset, new HashMap<>(){{}}, "en");
    }
    
    /**
     * Render as Svelte component as a page
     * @param filename name of the file to render
     * @param charset character set to use
     * @param props properties to pass on to the rendered page as svelte props
     * @return the rendered page as an html string ready to be served
     * @throws IOException 
     */
    public String page(String filename, String charset, HashMap<String,Object> props) throws IOException{
        return page(filename, charset, props, "en");
    }
    
    /**
     * Render as Svelte component as a page
     * @param filename name of the file to render
     * @param charset character set to use
     * @param props properties to pass on to the rendered page as svelte props
     * @param language language of the html tag
     * @return the rendered page as an html string ready to be served
     * @throws IOException 
     */
    public String page(String filename, String charset, HashMap<String,Object> props, String language) throws IOException{
        ServerFile file = new ServerFile(filename);
        
        return render(file.readString(charset), props).build(language);
    }
}
