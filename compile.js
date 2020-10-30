module.exports = {
    component: function(filename){
        return eval(
            ssr.compile(
                FileReaderJS.readString(filename)
            )
        );
    }
};