package com.hauns.idgeneration;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Random;

public class DistributedIdGenerationWorker {
    private final long twepoch = 1288834974657L;

    private final long workerIdBits = 10L;
    private final long maxWorkerId = -1L ^ (-1L << workerIdBits);
    private final long sequenceBits = 12L;

    private final long workerIdShift = sequenceBits;
    private final long timestampLeftShift = sequenceBits + workerIdBits;
    private final long sequenceMask = -1L ^ (-1L << sequenceBits);

    private long workerId = 0;

    private long sequence = 0L;

    private long lastTimestamp = -1L;

    {
        byte[] address;
        try {
            address = InetAddress.getLocalHost().getAddress();
        } catch (UnknownHostException e) {
            address = null;
        }
        // Worker id is generated by hashing of ip address which is unique in a local network, or random if unavailable.
        if (address != null) {
            for (byte x : address) {
                workerId = ((workerId << 8) - Byte.MIN_VALUE + x) & maxWorkerId;
            }
        } else {
            workerId = new Random().nextLong() & maxWorkerId;
        }
    }

    public synchronized long nextId() {
        long timestamp = timeGen();

        if (timestamp < lastTimestamp) {
            throw new RuntimeException(String.format("Clock moved backwards. Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }

        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0) {
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0;
        }

        lastTimestamp = timestamp;
        return ((timestamp - twepoch) << timestampLeftShift) | (workerId << workerIdShift) | sequence;
    }

    private long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    private long timeGen() {
        return System.currentTimeMillis();
    }
}