package com.hauns.idgeneration;

import org.hibernate.jpa.spi.IdentifierGeneratorStrategyProvider;

import java.util.HashMap;
import java.util.Map;

public class DistributedIdGeneratorStrategyProvider implements IdentifierGeneratorStrategyProvider {
    private static Map<String, Class<?>> strategies;

    static {
        strategies = new HashMap<>();
        strategies.put("com.hauns.commons.hibernate.DistributedIdGenerator", DistributedIdGenerator.class);
        strategies.put("org.hibernate.envers.DefaultRevisionEntity", DistributedIdGenerator.class);
        strategies.put("native", DistributedIdGenerator.class);
    }

    @Override
    public Map<String, Class<?>> getStrategies() {
        return strategies;
    }
}