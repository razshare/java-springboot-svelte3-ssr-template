# Svelte SSR for Spring Boot

This project offers a template for getting started to use Java Spring Boot and render Svelte on the server.
This project is still being tested and not all features might work correctly.

Most of the bugs have root in the ```require``` function of nodejs.

The svelte compiler is ofcourse written with nodejs in mind not plain javascript, this means that the original project will make use of the ```require``` function in many ways.

This project makes use of 3 different items in order to achieve server side rendering.

1. GraalVM
1. Svelte3SSR
1. Svelte3DOM

You will also need to install Maven: [http://maven.apache.org/download.cgi](http://maven.apache.org/download.cgi)

----

## GraalVM
In order to get started you will need to download GraalVM.
The official projects offers a free and open source commmunity edition, you can download it here: [https://www.graalvm.org/downloads/](https://www.graalvm.org/downloads/)

Fortunately JavaScripts is included in the main bundle by default, this means there's not extra setup you have to do.
Simply download a release and add the binaries to your global path variable.

---

## Svelte3SSR & Svelte3DOM
These 2 items are 2 maven dependency that your template includes.
You don't really have to setup anything here, but it is important to know that these 2 dependency are separate projects.
My goal is to keep these 2 projects independent from Spring Boot, so that in the future it will become easier to implement them in other frameworks, such as Quarkus.

- Svelte3SSR --> [https://github.com/tncrazvan/java-svelte3-ssr](https://github.com/tncrazvan/java-svelte3-ssr)
- Svelte3DOM --> [https://github.com/tncrazvan/java-svelte3-dom](https://github.com/tncrazvan/java-svelte3-dom)

### Svelte3SSR
This code base offers the class ```com.github.tncrazvan.svelte3ssr.Svelte3SSR```, which offers a few methods you can make use of in order to render you ```.svelte``` files.
- Svelte3SSR::compile(String source)
This method offers the main functionality of the whole project.
Upon being called, it will compile the given svelte ```source``` code into a ```cjs``` script that can be executed by the javascript engine in order to then obtain a pre rendered html and css output.
This is the actual Svelte compiler in action and it makes use of the ```./compiler.js``` file inside your project root directory.

- Svelte3SSR::render(String compiledSource, HashMap<String,Object> props)
After compiling your source code, you can then pass it to the rendering method, which will simply wrap the input compiled source code in an anonymus function and execute in the javascript engine in order to obtain a JSON object.
The resulting object is documented in the official Svelte documentation, which you can find here: [https://svelte.dev/docs#Server-side_component_API ](https://svelte.dev/docs#Server-side_component_API)
This method will return a result of type ```org.graalvm.polyglot.Value``` which represents your JSON object.
You can extract individual members of the object:
```java
Value renderedObject = this.render(this.compile(contents));
Svelte3SSRResult result = new Svelte3SSRResult();
result.head = renderedObject.getMember("head").asString();
result.html = renderedObject.getMember("html").asString();
result.css = renderedObject.getMember("css").getMember("code").asString();
```
where ```Svelte3SSRResult``` would be simple wrapper object.

### Svelte3DOM
Rendering svelte server is is pretty neat, but that might not be enough for your needs.
Once the client loads your prerendered page there will be no dynamic functionalities and that's because server side javascript has no notion of what a DOM is, thus you will not be able to attach events or other interactive features to your client's DOM.

In order to make the DOM interactive and enable all the features Svelte offers, you will need to also prepare a bundle for your client, and you can do that with ```com.github.tncrazvan.svelte3dom.Svelte3DOM```.
- Svelte3DOM::compile(String source)
Just as ```Svelte3SSR```, ```Svelte3DOM``` offers a compiling method, except this time the compiling type is not ```ssr``` but ```dom``` (for more details on this check [https://svelte.dev/docs#svelte_compile])(https://svelte.dev/docs#svelte_compile).
Just as ```Svelte3SSR::compile```, this method will generate a script that can be executed, except this one will manage your client's dom instead of rendering it on the server, so there's no need to execute it, simply serve it.
- Svelte3DOM::bundle(String compiledSource, LinkedHashMap<String,Object> props)
Bundle your compiled script and prepare it to be attatched to a ```<script deffer type='module'>...</script>``` tag.
The resulting script will also execute a ```document.body.innerHTML = '';``` instruction right before rendering to the dom.

---
### Get started with the Template
1. clone the main template
```git clone https://github.com/tncrazvan/java-springboot-svelte3-ssr-template```
1. install npm dependencies
```npm i```
1. run your spring boot server
```mvn spring-boot:dev```
This will take a while the first time your run it since maven will have to download all required dependencies including Svelte3SSR, Svelte3DOM and spring boot itself.

You should be able to visit your website at http://localhost:8080

I haven't tested every single thing, but most things should work properly.
Most of the bugs you will find will be most probably related to the "import" and "export" keywords, since I had to implement them myself from scratch and that is because the GraalVM interoperability framework does not include NodeJS as a language itself but instead it only offers the plain JavaScript languages, which as I mentioned before, is missing the ```require``` function.

In any case if you find any bugs feel free to submit an issue to the repository, I'll try to look into it and fix it.

I hope you find it interesting, have fun and stay safe!
