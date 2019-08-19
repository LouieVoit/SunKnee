
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author loux
 * @param <T>
 */
public abstract class Solver<T extends Item> {

    protected int order_;

    public Solver() {
        order_ = 0;
    }

    public int getOrder() {
        return order_;
    }

    public abstract void sort(User user, T[] unsortedItems, T[] sortedItems);

    public static class FromTreeSet<U extends Item> extends Solver<U> {

        @Override
        public void sort(User user, U[] unsortedItems, U[] sortedItems) {
            ComparatorFromUserInput<U> comparator = new ComparatorFromUserInput<>(user);
            Set<U> sortedItemsSet = new TreeSet<>(comparator);
            sortedItemsSet.addAll(Arrays.asList(unsortedItems));
            Iterator<U> iterator = sortedItemsSet.iterator();
            int itemIndex = -1;
            while (iterator.hasNext()) {
                U item = iterator.next();
                itemIndex++;
                sortedItems[itemIndex] = item;
            }
            order_ = comparator.getOrder();
        }

        private static class ComparatorFromUserInput<T extends Item> implements Comparator<T> {

            private final User user_;
            private int order_;

            public ComparatorFromUserInput(User user) {
                user_ = user;
                order_ = -1;
            }

            public int getOrder() {
                return order_;
            }

            @Override
            public int compare(T t, T t1) {
                order_++;
                return (user_.compare(t, t1));
            }

        }

    }

    public static class DummySolver<U extends Item> extends Solver<U> {

        @Override
        public void sort(User user, U[] unsortedItems, U[] sortedItems) {
            order_ = 0;
            List<U> sortedItemsList = new ArrayList<>(unsortedItems.length);
            for (int i = 0; i < unsortedItems.length; i++) {
                U unsortedItem = unsortedItems[i];
                int index = 0;
                while (index < i && user.compare(sortedItemsList.get(index), unsortedItem) < 0) {
                    index++;
                    order_++;
                }
                sortedItemsList.add(index, unsortedItem);
            }
            Iterator<U> iterator = sortedItemsList.iterator();
            int itemIndex = -1;
            while (iterator.hasNext()) {
                U item = iterator.next();
                itemIndex++;
                sortedItems[itemIndex] = item;
            }
        }

    }

}
