# Svelte SSR for Spring Boot

This project offers a template for getting started to use Java Spring Boot and render Svelte on the server.
This project is still being tested and not all features might work correctly.

Most of the bugs have root in the ```require``` function of nodejs.

The svelte compiler is ofcourse written with nodejs in mind not plain javascript, this means that the original project will make use of the ```require``` function in many ways.

This project makes use of 3 different items in order to achieve server side rendering.

1. GraalVM
1. Svelte3SSR
1. Svelte3DOM

----

## GraalVM
In order to get started you will need to download GraalVM.
The official projects offers a free and open source commmunity edition, you can download it here: https://www.graalvm.org/downloads/

Fortunately JavaScripts is included in the main bundle by default, this means there's not extra setup you have to do.
Simply download a release and add the binaries to your global path variable.

---

## Svelte3SSR & Svelte3DOM
These 2 items are 2 maven dependency that your template includes.
You don't really have to setup anything here, but it is important to know that these 2 dependency are separate projects.
My goal is to keep these 2 projects independent from Spring Boot, so that in the future it will become easier to implement them in other frameworks, such as Quarkus.

- Svelte3SSR --> https://github.com/tncrazvan/java-svelte3-ssr
- Svelte3DOM --> https://github.com/tncrazvan/java-svelte3-dom

### Svelte3SSR
This code base offers the class ```com.github.tncrazvan.svelte3ssr.Svelte3SSR```, which offers a few methods you can make use of in order to render you ```.svelte``` files.
- Svelte3SSR::compile(String source)
This method offers the main functionality of the whole project.
Upon being called, it will compile the given svelte ```source``` code into a ```cjs``` script that can be executed by the javascript engine in order to then obtain a pre rendered html and css output.
This is the actual Svelte compiler in action and it makes use of the ```./compiler.js``` file inside your project root directory.

- Svelte3SSR::render(String compiledSource)
After compiling your source code, you can then pass it to the rendering method, which will simply wrap the input compiled source code in an anonymus function and execute in the javascript engine in order to obtain a JSON object.
The resulting object is documented in the official Svelte documentation, which you can find here: https://svelte.dev/docs#Server-side_component_API 
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
