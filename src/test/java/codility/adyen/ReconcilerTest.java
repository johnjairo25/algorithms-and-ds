package codility.adyen;

import codility.adyen.Reconciler.PendingTransaction;
import codility.adyen.Reconciler.ProcessedTransaction;
import org.junit.Test;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ReconcilerTest {

    private Reconciler reconciler = new Reconciler();

    @Test
    public void test() {
        Stream<PendingTransaction> pendings = Stream.of(pending(10), pending(3));
        Stream<Stream<ProcessedTransaction>> processed = Stream.of(Stream.of(processed("10", true), processed("", true)));
        Stream<PendingTransaction> reconcile = reconciler.reconcile(pendings, processed);
        List<PendingTransaction> collect = reconcile.collect(Collectors.toList());
        System.out.println(collect);
    }

    private PendingTransaction pending(int value) {
        return new PendingTransaction((long) value);
    }

    private ProcessedTransaction processed(String id, boolean processed) {
        String s = processed ? "done" : "";
        return new ProcessedTransaction(id, Optional.of(s));
    }

}
