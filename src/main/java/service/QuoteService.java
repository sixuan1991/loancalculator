package service;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import entity.LenderOffer;
import entity.Quote;
import exceptions.QuoteUnavailableException;
import helper.CsvLoader;
import helper.QuoteCalculator;

public class QuoteService {

    private CsvLoader csvLoader;
    private QuoteCalculator quoteCalculator;

    public QuoteService(CsvLoader csvLoader, QuoteCalculator quoteCalculator) {
        this.csvLoader = csvLoader;
        this.quoteCalculator = quoteCalculator;
    }

    public Quote generateQuote(int requestedAmount) throws Exception {
        List<LenderOffer> lenderOffersAvailable = csvLoader.loadLenderOffersFromFile();

        List<LenderOffer> selectedOffers = selectBestOffers(requestedAmount, lenderOffersAvailable);

        return quoteCalculator.calculateQuote(requestedAmount, selectedOffers);
    }

    private List<LenderOffer> selectBestOffers(int requestedAmount, List<LenderOffer> candidateOffers) throws Exception {
        Collections.sort(candidateOffers);

        List<LenderOffer> selectedOffers = new LinkedList<>();
        int moneyLeftToLoan = requestedAmount;

        for (LenderOffer LenderOffer : candidateOffers) {
            if(moneyLeftToLoan <= 0) {
                break;
            }

            if (LenderOffer.getAmount() <= moneyLeftToLoan) {
                selectedOffers.add(LenderOffer);
                moneyLeftToLoan -= LenderOffer.getAmount();
            } else {
                selectedOffers.add(new LenderOffer(LenderOffer.getLender(), LenderOffer.getRate(), moneyLeftToLoan));
                moneyLeftToLoan = 0;
            }
        }

        if (moneyLeftToLoan != 0 ) {
            throw new QuoteUnavailableException(requestedAmount);
        }

        return selectedOffers;
    }
}
