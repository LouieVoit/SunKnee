
import java.util.UUID;

public abstract class Item {
    private final UUID id_;
    
    public Item(UUID id) {
        id_ = id;
    }
    
    public final UUID getID() {
        return id_;
    }
    
    public abstract String getName();
    
    public static class DummyItem extends Item {
        private final int item_;
        
        
        public DummyItem(int item) {
            super(UUID.randomUUID());
            item_ = item;
        }

        @Override
        public String getName() {
            return Integer.toString(item_);
        }
        
        public int getItem() {
            return item_;
        }

        @Override
        public String toString() {
            return (this.getName());
        }
        
        
        
    }
}
