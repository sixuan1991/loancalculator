package helper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import entity.LenderOffer;
import exceptions.CorruptedDataException;

public class CsvLoader {
    private static final Logger logger = Logger.getLogger(CsvLoader.class.getName());

    private static final String CSV_DELIMITER = ",";

    private static final String[] HEADERS = {"Lender", "Rate", "Available"};

    private final String sourceFilePath;

    public CsvLoader(String sourceFilePath) {
        this.sourceFilePath = sourceFilePath;
    }

    public List<LenderOffer> loadLenderOffersFromFile() throws IOException {
        try(BufferedReader bf = new BufferedReader(new FileReader(sourceFilePath))){
            return extractLenderOffersFromReader(bf);
        }
    }

    List<LenderOffer> extractLenderOffersFromReader(BufferedReader bf) throws IOException {
        List<LenderOffer> lendOffers = new LinkedList<>();

        String offerLine = bf.readLine();

        if (isHeader(offerLine.split(CSV_DELIMITER))) {
            offerLine = bf.readLine();
        }

        do {
            try {
                lendOffers.add(buildLendOffer(offerLine));
            } catch (CorruptedDataException e) {
                logger.log(Level.WARNING, e.getMessage() + " Will skip this line.");
            }
        } while ((offerLine = bf.readLine()) != null);

        return lendOffers;
    }

    private boolean isHeader(String[] items) {
        if (items.length == 3) {
            for (int i = 0; i<3; i++) {
                if (!items[i].trim().equals(HEADERS[i])) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }

    private LenderOffer buildLendOffer(String offerLine) throws CorruptedDataException {
        try {
            String[] items = offerLine.split(CSV_DELIMITER);
            return new LenderOffer(items[0].trim(), Double.parseDouble(items[1].trim()), Integer.parseInt(items[2].trim()));
        } catch (Exception e) {
            throw new CorruptedDataException(String.format("Corrupted data in csv file when reading line: [%s].", offerLine));
        }
    }
}
