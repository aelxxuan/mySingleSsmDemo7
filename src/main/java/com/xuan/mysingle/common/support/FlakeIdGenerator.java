/*
 * Copyright (C) 2016 the xkw.com authors.
 * http://www.xkw.com
 */
package com.xuan.mysingle.common.support;

/**
 * @author xuanzongjun
 * @since 1.0
 * Date: 2018/5/22
 */
public class FlakeIdGenerator implements IdGenerator {
    private static final long EPOCH = 1288834974657L;
    private static final long SEQUENCE_BITS = 10L;
    private static final long SEQUENCE_MASK = 1023L;
    private static final long HOST_ID_BITS = 3L;
    private static final long HOST_ID_MAX = 7L;
    private static final long HOST_ID_SHIFT = 10L;
    private static final long TIMESTAMP_SHIFT = 13L;
    private final long hostId;
    private volatile long lastTimestamp = -1L;
    private volatile long sequence = 0L;

    public FlakeIdGenerator(long hostId) {
        this.hostId = hostId;
        if(hostId < 0L || hostId > 7L) {
            throw new IllegalStateException("Invalid host ID: " + hostId);
        }
    }

    public synchronized long generateId() throws IllegalStateException {
        long timestamp = System.currentTimeMillis();
        if(timestamp < this.lastTimestamp) {
            throw new IllegalStateException("Negative timestamp delta " + (this.lastTimestamp - timestamp) + "ms");
        } else {
            if(this.lastTimestamp == timestamp) {
                this.sequence = this.sequence + 1L & 1023L;
                if(this.sequence == 0L) {
                    timestamp = nextTimestamp(this.lastTimestamp);
                }
            } else {
                this.sequence = 0L;
            }

            this.lastTimestamp = timestamp;
            return timestamp - 1288834974657L << 13 | this.hostId << 10 | this.sequence;
        }
    }

    private static long nextTimestamp(long lastTimestamp) {
        long timestamp;
        for(timestamp = System.currentTimeMillis(); timestamp <= lastTimestamp; timestamp = System.currentTimeMillis()) {
            ;
        }

        return timestamp;
    }
}
