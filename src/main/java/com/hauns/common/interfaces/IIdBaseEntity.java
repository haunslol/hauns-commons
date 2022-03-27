package com.hauns.common.interfaces;
 
public interface IIdBaseEntity<T extends Number> {

    String ID_PROPERTY = "id";

    T getId();
}