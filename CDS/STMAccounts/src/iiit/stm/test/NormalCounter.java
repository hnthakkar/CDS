package iiit.stm.test;

import jvstm.VBox;
import jvstm.Atomic;

public class NormalCounter implements Counter 
{
    private VBox<Long> count = new VBox<Long>(0L);

    public long getCount() {
        return count.get();
    }

    public @Atomic void inc() {
        count.put(getCount() + 1);
    }
}