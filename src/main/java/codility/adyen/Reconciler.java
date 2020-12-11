package codility.adyen;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Reconciler {

    Stream<PendingTransaction> reconcile(Stream<PendingTransaction> pending, Stream<Stream<ProcessedTransaction>> processed) {
        if (pending == null) {
            return Stream.empty();
        }

        Set<Long> processedIds = processed == null ? new HashSet<>() : processed
                .flatMap(Function.identity())
                .filter(it -> it != null && it.getId() != null && it.getId().length() > 0
                        && it.getStatus() != null && "done".equalsIgnoreCase(it.getStatus().orElse("")))
                .map(it -> Long.parseLong(it.getId()))
                .collect(Collectors.toSet());

        return pending.filter(p -> p != null && p.getId() != null && processedIds.contains(p.getId()));
    }


    public static class PendingTransaction {
        private Long id;

        public PendingTransaction(Long id) {
            this.id = id;
        }

        public Long getId() {
            return id;
        }
    }

    public static class ProcessedTransaction {
        private String id;
        private Optional<String> status;

        public ProcessedTransaction(String id, Optional<String> status) {
            this.id = id;
            this.status = status;
        }

        public String getId() {
            return id;
        }

        public Optional<String> getStatus() {
            return status;
        }
    }

}
