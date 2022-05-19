package com.hauns.common.interfaces;
 
public interface IIdBaseEntity<N extends Number> {

    String ID_PROPERTY = "id";

    N getId();
}