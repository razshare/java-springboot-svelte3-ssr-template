module.exports = {
    componentDom: function(filename){
        return eval(
            dom.compile(
                FileReaderJS.readString(filename)
            )
        );
    }
};