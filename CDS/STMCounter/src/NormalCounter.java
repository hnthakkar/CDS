
import jvstm.Atomic;
import jvstm.VBox;



public class NormalCounter implements Counter {
    private VBox<Long> count = new VBox<Long>(0L);

    public long getCount() {
        return count.get();
    }

    @Atomic
    public void inc() {
        count.put(getCount() + 1);
    }
}
