package kernel;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author loux
 * @param <T>
 */
public abstract class User <T extends Item> {
    private static final Logger LOG = Logger.getLogger(User.class.getName());
    private final Map<UUID, T> items_;
    
    public User() {
        items_ = new HashMap<>();
    }
    
    public boolean addItem(T item) {
        if (hasItem(item)) {
            LOG.log(Level.SEVERE, "Item {0} already exists", item.getName());
            return false;
        }
        items_.put(item.getID(), item);
        return true;
    }
    
    public boolean hasItem(T item) {
        return (items_.containsKey(item.getID()));
    }
    
    public List<T> getItems() {
        return new ArrayList<>(items_.values());
    }
    
    public int compare(T item, T item1) {
        if (!hasItem(item)) {
            RuntimeException ex = new RuntimeException("User does not have item " + item.getName());
            LOG.log(Level.SEVERE, ex.getMessage());
            throw ex;
        }
        if (!hasItem(item1)) {
            RuntimeException ex = new RuntimeException("User does not have item " + item1.getName());
            LOG.log(Level.SEVERE, ex.getMessage());
            throw ex;
        }
        return (compare_(item, item1));
    }
    
    
    
    protected abstract int compare_(T item, T item1);
    
    public static class DummyUser extends User<Item.DummyItem> {

        @Override
        public int compare_(Item.DummyItem item, Item.DummyItem item1) {
            return (item.getItem() - item1.getItem());
        }
        
    }
    
    
    
}
