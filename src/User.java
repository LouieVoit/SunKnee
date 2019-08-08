
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    
    public abstract int compare(T t, T t1);
    
    public static class DummyUser extends User<Item.DummyItem> {

        @Override
        public int compare(Item.DummyItem t, Item.DummyItem t1) {
            return (t.getItem() - t1.getItem());
        }
        
    }
    
    
    
}
