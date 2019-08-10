
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

    public abstract void sort(User user, List<T> unsortedItems, T[] sortedItems);

    public static class FromTreeSet<U extends Item> extends Solver<U> {

        @Override
        public void sort(User user, List<U> unsortedItems, U[] sortedItems) {
            MyComparator<U> comparator = new MyComparator<>(user);
            Set<U> sortedItemsSet = new TreeSet<>(comparator);
            Iterator<U> iterator = unsortedItems.iterator();
            while (iterator.hasNext()) {
                U item = iterator.next();
                sortedItemsSet.add(item);
            }
            iterator = sortedItemsSet.iterator();
            int itemIndex = -1;
            while (iterator.hasNext()) {
                U item = iterator.next();
                itemIndex ++;
                sortedItems[itemIndex] = item;
            }
            order_ = comparator.order_ - 1;
        }

        private static class MyComparator<T extends Item> implements Comparator<T> {

            private final User user_;
            private int order_;

            public MyComparator(User user) {
                user_ = user;
                order_ = 0;
            }

            @Override
            public int compare(T t, T t1) {
                order_++;
                return (user_.compare(t, t1));
            }

        }

    }

}
