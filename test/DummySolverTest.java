
import com.opencsv.CSVWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author loux
 */
public class DummySolverTest {

    private static String OUTPUT_PATH;
    private static final Logger LOG = Logger.getLogger(DummySolverTest.class.getName());
    
    @BeforeClass
    public static void setUp() {
        OUTPUT_PATH = System.getProperty("user.dir") + File.separator + "DummySolverTest";
        (new File(OUTPUT_PATH)).mkdirs();
    }

    @Test
    public void statisticalStudies() throws IOException {
        int numberOfSamples = 1000;
        try (Writer writerForStatistics = new FileWriter(new File(OUTPUT_PATH + File.separator + "data_numberOfSamples_"+numberOfSamples+".csv"))) {
            CSVWriter csvWriterForStatistics = new CSVWriter(writerForStatistics);
            csvWriterForStatistics.writeNext(new String[]{"N", "average order", "minimum order", "maximum order"});
            int minimumNumberOfItems = 2;
            int maximumNumberOfItems = 200;
            for (int numberOfItems = minimumNumberOfItems; numberOfItems < maximumNumberOfItems; numberOfItems++) {
                LOG.log(Level.INFO, "** number of items : {0}", numberOfItems);
                double averageOrder;
                try (Writer writer = new FileWriter(new File(OUTPUT_PATH + File.separator + "data_numberOfItems_"+numberOfItems+".csv"))) {
                    CSVWriter csvWriter = new CSVWriter(writer);
                    csvWriter.writeNext(new String[]{"sample index", "order"});
                    averageOrder = 0.0;
                    for (int sampleIndex = 0; sampleIndex < numberOfSamples; sampleIndex++) {
                        User.DummyUser user = new User.DummyUser();
                        for (int i = 0; i < numberOfItems; i++) {
                            user.addItem(new Item.DummyItem((int) (Math.random() * numberOfItems)));
                        }
                        Solver<Item.DummyItem> solver = new Solver.FromTreeSet<>();
                        Item.DummyItem[] sortedItems = new Item.DummyItem[numberOfItems];
                        Item.DummyItem[] unsortedItems = user.getItems().toArray(new Item.DummyItem[numberOfItems]);
                        solver.sort(user, unsortedItems, sortedItems);
                        double order = (double) solver.getOrder();
                        csvWriter.writeNext(new String[]{Integer.toString(sampleIndex), Double.toString(order)});
                        averageOrder += order;
                    }
                }
                averageOrder /= numberOfSamples;
                csvWriterForStatistics.writeNext(new String[]{Integer.toString(numberOfItems), Double.toString(averageOrder), Integer.toString(numberOfItems - 1), Integer.toString(numberOfItems * (numberOfItems - 1) / 2)});
            }
        }
    }
    

}
