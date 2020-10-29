const System = Java.type("java.lang.System");
const path = Java.type("com.mycompany.arcanossrsvelte.PathJS");
const fs = Java.type("com.mycompany.arcanossrsvelte.FileSystemJS");
load('./jvm-npm.js');

const { create_ssr_component } = require('./node_modules/svelte/internal/index.js');
const { compile } = require('./compiler.js');

function main(){
	/*
	const result = compile("<p>hello world</p>",{
		generate: "ssr",
		format: "cjs"
	});
	return JSON.stringify(result)*/
	
	const Component = create_ssr_component(($$result, $$props, $$bindings, slots)=>{
		return `<p>hello world</p>`;
	});
	
	return Component.render();
}

main();