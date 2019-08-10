
import com.opencsv.CSVWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
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

    public static void main(String[] args) throws IOException {
        String outputPath = "/tmp/";
        Writer writer = new FileWriter(new File(outputPath + File.separator + "data.csv"));
        CSVWriter csvWriter = new CSVWriter(writer);
        String[] header = new String[] {"N", "q(N)", "min(q(N))", "max(q(N))"};
        csvWriter.writeNext(header);
        int nSamples = 1000;
        int minN = 2;
        int maxN = 200;
        for (int N = minN; N < maxN; N++) {
            LOG.log(Level.INFO, "** N : {0}", N);
            double qMean = 0.0;
            int minQ = N-1;
            int maxQ = N * (N - 1) / 2;
            for (int j = 0; j < nSamples; j++) {
                final User.DummyUser user = new User.DummyUser();
                List<Item.DummyItem> unsortedItems = new ArrayList<>(N);
                for (int i = 0; i < N; i++) {
                    Item.DummyItem item = new Item.DummyItem(i);
                    unsortedItems.add(item);
                    user.addItem(item);
                }
                Collections.shuffle(unsortedItems);
                Solver<Item.DummyItem> solver = new Solver.FromTreeSet<>();
                Item.DummyItem[] sortedItems = new Item.DummyItem[N];
                solver.sort(user, unsortedItems, sortedItems);
                qMean += (double) solver.getOrder();
            }
            qMean /= nSamples;
            String[] values = new String[] {Integer.toString(N), Double.toString(qMean), Integer.toString(minQ), Integer.toString(maxQ)};
            csvWriter.writeNext(values);
        }
        writer.close();
    }
}
