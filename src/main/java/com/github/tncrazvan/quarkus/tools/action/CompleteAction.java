package com.github.tncrazvan.quarkus.tools.action;

/**
 * Make a callback and defined its return type (R) and its parameter type (P).
 * @author Razvan Tanase
 * @param <R> type of the returned object.
 * @param <P> type of the parameter object.
 */
public interface CompleteAction<R,P>{
    public abstract R callback(P e);
}