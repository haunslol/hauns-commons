package com.hauns.security.model;

import java.security.Principal;
import java.util.Set;

public interface User extends Principal {

    Object getId();

    Set<? extends Role> getRoles();
}
