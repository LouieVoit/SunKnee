
import com.opencsv.CSVWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author loux
 */
public class SolverTest {

    private static String OUTPUT_PATH;
    private static final Logger LOG = Logger.getLogger(SolverTest.class.getName());

    @BeforeClass
    public static void setUp() {
        OUTPUT_PATH = System.getProperty("user.dir") + File.separator + "DummySolverTest";
        (new File(OUTPUT_PATH)).mkdirs();
    }

    @Test
    public void statisticalStudies() throws IOException {
        int numberOfSamples = 1000;
        try (Writer writerForStatistics = new FileWriter(new File(OUTPUT_PATH + File.separator + "data_numberOfSamples_" + numberOfSamples + ".csv"))) {
            CSVWriter csvWriterForStatistics = new CSVWriter(writerForStatistics);
            csvWriterForStatistics.writeNext(new String[]{"N", "average order", "minimum order", "maximum order"});
            int minimumNumberOfItems = 2;
            int maximumNumberOfItems = 100;
            for (int numberOfItems = minimumNumberOfItems; numberOfItems < maximumNumberOfItems; numberOfItems++) {
                LOG.log(Level.INFO, "** number of items : {0}", numberOfItems);
                int minimumOrder = numberOfItems - 1;
                int maximumOrder = numberOfItems * (numberOfItems - 1) / 2;
                double averageOrder;
                try (Writer writer = new FileWriter(new File(OUTPUT_PATH + File.separator + "data_numberOfItems_" + numberOfItems + ".csv"))) {
                    CSVWriter csvWriter = new CSVWriter(writer);
                    csvWriter.writeNext(new String[]{"sample index", "order"});
                    averageOrder = 0.0;
                    for (int sampleIndex = 0; sampleIndex < numberOfSamples; sampleIndex++) {
                        User.DummyUser user = new User.DummyUser();
                        List<Item.DummyItem> expectedSortedItems = new LinkedList<>();
                        for (int i = 0; i < numberOfItems; i++) {
                            expectedSortedItems.add(new Item.DummyItem(i));
                        }
                        List<Item.DummyItem> userItems = new LinkedList<>(expectedSortedItems);
                        Collections.shuffle(userItems);
                        userItems.forEach((userItem) -> {
                            user.addItem(userItem);
                        });
                        Solver<Item.DummyItem> solver = new Solver.FromTreeSet<>();
//                        Solver<Item.DummyItem> solver = new Solver.DummySolver<>();
                        Item.DummyItem[] sortedItems = new Item.DummyItem[numberOfItems];
                        Item.DummyItem[] unsortedItems = user.getItems().toArray(new Item.DummyItem[numberOfItems]);
                        solver.sort(user, unsortedItems, sortedItems);
                        Assert.assertArrayEquals(expectedSortedItems.toArray(), sortedItems);
                        double order = (double) solver.getOrder();
                        csvWriter.writeNext(new String[]{Integer.toString(sampleIndex), Double.toString(order)});
                        averageOrder += order;
                    }
                }
                averageOrder /= numberOfSamples;
                csvWriterForStatistics.writeNext(new String[]{Integer.toString(numberOfItems), Double.toString(averageOrder), Integer.toString(minimumOrder), Integer.toString(maximumOrder)});
            }
        }
    }

    @Test
    public void test() throws IOException {

        int numberOfItems = 10;
        LOG.log(Level.INFO, "** number of items : {0}", numberOfItems);
        int minimumOrder = numberOfItems - 1;
        int maximumOrder = numberOfItems * (numberOfItems - 1) / 2;
        double averageOrder;
        averageOrder = 0.0;
        User.DummyUser user = new User.DummyUser();
        List<Item.DummyItem> expectedSortedItems = new LinkedList<>();
        for (int i = 0; i < numberOfItems; i++) {
            expectedSortedItems.add(new Item.DummyItem(i));
        }
        List<Item.DummyItem> userItems = new LinkedList<>(expectedSortedItems);
        Collections.shuffle(userItems);
        userItems.forEach((userItem) -> {
            user.addItem(userItem);
        });
        Solver<Item.DummyItem> solver = new Solver.DummySolver<>();
        Item.DummyItem[] sortedItems = new Item.DummyItem[numberOfItems];
        Item.DummyItem[] unsortedItems = user.getItems().toArray(new Item.DummyItem[numberOfItems]);
        solver.sort(user, unsortedItems, sortedItems);
        double order = (double) solver.getOrder();
        LOG.log(Level.INFO, "** order : {0}", order);
        LOG.log(Level.INFO, "** sorted items : {0}", Arrays.toString(sortedItems));
    }

}
