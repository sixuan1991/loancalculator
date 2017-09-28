package helper;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import entity.LenderOffer;

@RunWith(MockitoJUnitRunner.class)
public class CsvLoaderTest {

    @Mock
    private BufferedReader bufferedReader;

    private CsvLoader csvLoader;

    @Before
    public void setUp() {

        csvLoader = new CsvLoader("dummy_path.csv");
    }

    @Test
    public void shouldBuildAListWithThreeOffers() throws IOException {
        when(bufferedReader.readLine()).
                thenReturn("John, 0.07, 480", "David, 0.06, 490", "corrupted, corrupted, corrupted", "Amy, 0.08, 600", null);

        List<LenderOffer> lenderOffers = csvLoader.
                extractLenderOffersFromReader(bufferedReader);

        List<LenderOffer> expectedLendOffers = new LinkedList<>();
        expectedLendOffers.add(new LenderOffer("John", 0.07, 480));
        expectedLendOffers.add(new LenderOffer("David", 0.06, 490));
        expectedLendOffers.add(new LenderOffer("Amy", 0.08, 600));
        assertThat(lenderOffers, is(expectedLendOffers));
    }
}