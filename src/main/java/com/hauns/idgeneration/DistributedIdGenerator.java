package com.hauns.quickjobsbackend.framework.idgeneration;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;

public class DistributedIdGenerator implements IdentifierGenerator {
    private DistributedIdGenerationWorker worker = new DistributedIdGenerationWorker();

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        return worker.nextId();
    }
}