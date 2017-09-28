package service;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.LinkedList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import entity.LenderOffer;
import exceptions.QuoteUnavailableException;
import helper.CsvLoader;
import helper.QuoteCalculator;

@RunWith(MockitoJUnitRunner.class)
public class QuoteServiceTest {

    private List<LenderOffer> candidateOffers = new LinkedList<>();

    private int requestedAmount;

    @Mock
    private CsvLoader csvLoader;
    @Mock
    private QuoteCalculator quoteCalculator;

    private QuoteService quoteService;

    @Before
    public void setUp() throws Exception {
        candidateOffers.add(new LenderOffer("John", 0.07, 480));
        candidateOffers.add(new LenderOffer("David", 0.06, 490));
        candidateOffers.add(new LenderOffer("Amy", 0.08, 600));

        when(csvLoader.loadLenderOffersFromFile()).thenReturn(candidateOffers);

        quoteService = new QuoteService(csvLoader, quoteCalculator);
    }

    @Test
    public void shouldFindBestOffersIfRequestedAmountIsLessThanTotalOffer() throws Exception {
        requestedAmount = 1000;

        quoteService.generateQuote(requestedAmount);

        List<LenderOffer> expectedSelectedOffers = new LinkedList<>();
        expectedSelectedOffers.add(new LenderOffer("David", 0.06, 490));
        expectedSelectedOffers.add(new LenderOffer("John", 0.07, 480));
        expectedSelectedOffers.add(new LenderOffer("Amy", 0.08, 30));
        verify(quoteCalculator, times(1)).calculateQuote(eq(requestedAmount), eq(expectedSelectedOffers));
    }

    @Test(expected = QuoteUnavailableException.class)
    public void shouldThrowQuoteUnavailableExceptionIfRequestedAmountIsTooLarge() throws Exception {
        requestedAmount = 100000;

        quoteService.generateQuote(requestedAmount);
    }

    @Test(expected = QuoteUnavailableException.class)
    public void shouldhrowQuoteUnavailableExceptionIfRequestedAmountIsInvalid() throws Exception {
        requestedAmount = -1;

        quoteService.generateQuote(requestedAmount);
    }

    @After
    public void tearDown() {
        candidateOffers.clear();
        requestedAmount = 0;
    }

}