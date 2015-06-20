package iiit.stm.test;

import jvstm.*;

public class CFOCounter implements Counter {
    private VBox<Long> count = new VBox<Long>(0L);

    private PerTxBox<Long> perTxBox = new PerTxBox<Long>(0L)
                                   {
                                       public void commit(Long value) 
                                       {
                                           count.put(count.get() + value);
                                       }
                                   };

    public void inc() 
    {
     perTxBox.put(perTxBox.get() + 1);
    }

    public long getCount()
    {
        return count.get() + perTxBox.get();
    }
}