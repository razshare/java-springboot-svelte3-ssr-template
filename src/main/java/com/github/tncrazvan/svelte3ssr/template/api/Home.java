package com.github.tncrazvan.svelte3ssr.template.api;

import com.github.tncrazvan.svelte3ssr.template.services.Svelte3DOMService;
import com.github.tncrazvan.svelte3ssr.template.services.Svelte3SSRService;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Home {
    
    @Autowired
    Svelte3SSRService ssr;
    
    @Autowired
    Svelte3DOMService dom;
    
    @RequestMapping("/")
    public String home() throws IOException{
        return ssr.page(dom,"./www/App.svelte");
    }
}
