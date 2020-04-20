package com.baselibrary.util;

import android.util.Log;

/**
 * https://github.com/twitter/snowflake
 */

public class IdGenerator {
    private long workerId;
    private long sequence = 0L;
    private long workerIdBits = 5L;
    private long twepoch = 1262275200L;
    private long sequenceBits = 12L;
    private long workerIdShift = this.sequenceBits;
    private long timestampLeftShift = this.sequenceBits + this.workerIdBits;
    private long sequenceMask = 0xFFFFFFFF ^ -1L << (int) this.sequenceBits;
    private long lastTimestamp = -1L;
    private static IdGenerator instance;

    public static IdGenerator getInstance() {
        if (instance == null) {
            instance = new IdGenerator(0);
        }
        return instance;
    }

    private IdGenerator(long workId) {
        this.workerId = workId;
    }

    public synchronized long nextId() {
        long timestamp = timeGen();
        if (timestamp < this.lastTimestamp) {
            Log.e("id error", String.format("clock is moving backwards.  Rejecting requests until %d.", new Object[]{Long.valueOf(this.lastTimestamp)}));
            throw new RuntimeException(String.format(
                    "Clock moved backwards.  Refusing to generate id for %d milliseconds", new Object[]{Long.valueOf(this.lastTimestamp - timestamp)}));
        }
        if (this.lastTimestamp == timestamp) {
            this.sequence = (this.sequence + 1L & this.sequenceMask);
            if (this.sequence == 0L) {
                timestamp = tilNextMillis(this.lastTimestamp);
            }
        } else {
            this.sequence = 0L;
        }
        this.lastTimestamp = timestamp;

        return timestamp - this.twepoch << (int) this.timestampLeftShift | this.workerId << (int) this.workerIdShift | this.sequence;
    }

    protected long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    protected long timeGen() {
        return System.currentTimeMillis() / 1000L;
    }
}
