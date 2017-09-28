import java.io.IOException;

import entity.Currency;
import entity.Quote;
import exceptions.InvalidInputException;
import exceptions.QuoteUnavailableException;
import helper.CsvLoader;
import service.QuoteService;
import helper.QuoteCalculator;

public class Main {

    public static void main(String[] args) throws Exception {
        int loanPeriodInMonth = 36;
        Currency currency = Currency.GBP;

        try{
            if(args.length != 2) {
                throw new InvalidInputException("You have to input exactly two arguments: csv file absolute path and money to loan." +
                        " Please check your input");
            }
            String sourceFilePath = args[0];
            int requestedAmount = parseRequestAmountArg(args[1]);

            QuoteService quoteService = new QuoteService(new CsvLoader(sourceFilePath), new QuoteCalculator(loanPeriodInMonth));
            Quote quote = quoteService.generateQuote(requestedAmount);

            printQuote(quote, currency);
        } catch (QuoteUnavailableException | InvalidInputException | IOException e) {
            System.out.println(e.getMessage());
        }

    }

    private static int parseRequestAmountArg(String requetAmountArg) throws Exception {
        try {
            int requestAmount = Integer.parseInt(requetAmountArg);
            if (requestAmount < 1000 || requestAmount > 15000 || requestAmount%100 != 0) {
                throw new InvalidInputException("");
            }
            return requestAmount;
        } catch (Exception e) {
            throw new InvalidInputException("Amount invalid. Amount should be £100 increment between £1000 and £15000 inclusive");
        }
    }
    private static void printQuote(Quote quote, Currency currency) {
        System.out.println( String.format(
                "Requested amount: %s%d \n" +
                        "Rate: %.1f%% \n" +
                        "Monthly repayment: %s%.2f \n" +
                        "Total repayment: %s%.2f",
                currency.getSymbol(), quote.getRequestedAmount(),
                quote.getRate(),
                currency.getSymbol(), quote.getMonthlyRepayment(),
                currency.getSymbol(), quote.getTotalRepayment())
        );
    }
}
