package helper;

import static junit.framework.TestCase.assertEquals;

import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import entity.LenderOffer;
import entity.Quote;

public class QuoteCalculatorTest {

    private List<LenderOffer> selectedOffers = new LinkedList<>();

    private int requestedAmount;

    private Quote expectedQuote;

    private QuoteCalculator quoteCalculator;

    @Before
    public void setUp() throws Exception {
        int loanPeriodInMonth = 36;

        selectedOffers.add(new LenderOffer("John", 0.07, 480));
        selectedOffers.add(new LenderOffer("David", 0.06, 490));
        selectedOffers.add(new LenderOffer("Amy", 0.08, 30));

        requestedAmount = 1000;

        expectedQuote = new Quote(1000, 0.065, 30.58, 1100.94);

        quoteCalculator = new QuoteCalculator(loanPeriodInMonth);
    }

    @Test
    public void shouldReturnExpectedQuote() {
        Quote quote = quoteCalculator.calculateQuote(requestedAmount, selectedOffers);

        assertEquals("Should return expectedQuote",
                expectedQuote, quote);

    }
}