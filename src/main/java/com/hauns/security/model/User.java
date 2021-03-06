package com.hauns.security.model;

import java.security.Principal;
import java.util.Set;

public interface User<R extends Role> extends Principal {

  Object getId();

  Set<R> getRoles();
}
