"use strict";
const Test = (function (){
    class Component {
        constructor(options) {
            console.log("component");
        }
    }

    exports.default = Component;
    return Component;
})();

new Test();