
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SunKnee {

    private static final Logger LOG = Logger.getLogger(SunKnee.class.getName());

    public static void main(String[] args) {
        double qMean = 0.0;
        int nSamples = 10000;
        for (int j = 0; j < nSamples; j++) {
            int N = 10;
            final User.DummyUser user = new User.DummyUser();
            List<Item.DummyItem> unsortedItems = new ArrayList<>(N);
            for (int i = 0; i < N; i++) {
                Item.DummyItem item = new Item.DummyItem(i);
                unsortedItems.add(item);
                user.addItem(item);
            }
            Collections.shuffle(unsortedItems);
            MyComparator<Item.DummyItem> comparator = new MyComparator<>(user);
            Set<Item.DummyItem> sortedItems = new TreeSet<>(comparator);
            Iterator<Item.DummyItem> iterator = unsortedItems.iterator();
            while (iterator.hasNext()) {
                Item.DummyItem item = iterator.next();
                sortedItems.add(item);
            }
            LOG.log(Level.INFO, String.format("N=%d, min(q(N))=%d, q=%d, max(q(N))=%d", N, N - 1, comparator.getQ(), N * (N - 1) / 2));
            LOG.log(Level.INFO, sortedItems.toString());
            qMean += (double) comparator.getQ();
        }
        qMean /= nSamples;
        LOG.log(Level.INFO, String.format("qMean=%f, nSamples=%d", qMean, nSamples));
    }

    private static class MyComparator<T extends Item> implements Comparator<T> {

        private final User user_;
        private int q_;

        public MyComparator(User user) {
            user_ = user;
            q_ = 0;
        }

        public int getQ() {
            return q_;
        }

        @Override
        public int compare(T t, T t1) {
            q_++;
            return (user_.compare(t, t1));
        }

    }

    public static class Node<T extends Item> {

        T item_;
        Node left_;
        Node right_;

        Node(T item) {
            item_ = item;
            left_ = null;
            right_ = null;
        }
    }

    public static class BinaryTree {

        Node root;

        // ...
    }
}
