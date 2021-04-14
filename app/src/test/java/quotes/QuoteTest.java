package quotes;
import org.junit.Test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;
public class QuoteTest {
    @Test public void testGetRandomQuote() {
        App classUnderTest = new App();
        Quote quote = classUnderTest.getQuote("../app/src/main/resources/recentquotes.json");
        assertEquals("Test the Type Of the Output of getQuote Method", true, quote instanceof Quote);
        assertEquals("Is author name in the json File",true,DataForTest.isAuthorInJsonFile(quote.getAuthor()));
        assertEquals("The first part of the output","The quote is",quote.toString().split(":")[0]);
        assertEquals("The text of the quote != null",false,quote.toString().split(":")[1] == null);
    }

    // Stretch Test Goals get By Word without command line
    @Test public void testGetByWord() {
        App classUnderTest = new App();
        ArrayList<Quote> quotes = classUnderTest.getByWord("../app/src/main/resources/recentquotes.json","rectangle");
        assertEquals("[The quote is: \"Tom put all my records into this rectangle!\"\n" +
                "Written by: Ron Swanson, The quote is: \"Tom put all my records into this rectangle!\"\n" +
                "Written by: Ron Swanson]",quotes.toString());
    }

    // Stretch Test Goals get By Word without command line
    @Test public void testGetByAuthorName() {
        App classUnderTest = new App();
        ArrayList<Quote> quotes = classUnderTest.getByAuthorName("../app/src/main/resources/recentquotes.json","Tove Jansson");
        assertEquals("[The quote is:  “If you're not afraid, how can you be really brave?” \n" +
                "Written by: Tove Jansson, The quote is:  “Seine Gedanken oder plötzlichen Wünsche flogen wie die Laune des Meeres über das Wasser, mal hierhin, mal dorthin, und er lebte ununterbrochen in einer stillen Spannung.” \n" +
                "Written by: Tove Jansson]",quotes.toString());
    }

    // Stretch Test Goals get By Word without command line
    @Test public void testGetFromApi() {
        // Get Json From 2nd API
        try {
            // first api
            URL url2nd = new URL("http://ron-swanson-quotes.herokuapp.com/v2/quotes");
            String jsonData2ndApi = App.getJsonFromAPI(url2nd);
            String quote2ndApi = App.getRonSwansonQuotesObject(jsonData2ndApi);
            assertEquals("is the output not empty","The quote is",quote2ndApi.toString().split(":")[0]);
            assertEquals("is the output not empty",true,quote2ndApi.toString().split(":")[1] != "" && quote2ndApi.toString().split(":")[1] != null);

            // 2nd api
            URL url = new URL("http://api.forismatic.com/api/1.0/?method=getQuote&format=json&lang=en");
            String jsonData =  App.getJsonFromAPI(url);
            FormismaticQuote quoteApi =  App.getFormismaticQuoteObject(jsonData);
            assertEquals("is the output not empty","The quote is",quoteApi.toString().split(":")[0]);
            assertEquals("is the output not empty",true,quoteApi.toString().split(":")[1] != "" && quoteApi.toString().split(":")[1] != null);
            assertEquals("is the output not empty author name",true,quoteApi.getQuoteAuthor() != null);
        }
        catch (Exception ex){
            System.out.println(ex);
        }

    }

    @Test public void testAddFromApi() {
        // Get Json From 2nd API
        try {
            // 2nd api
            URL url = new URL("http://api.forismatic.com/api/1.0/?method=getQuote&format=json&lang=en");
            String jsonData =  App.getJsonFromAPI(url);
            FormismaticQuote quoteApi =  App.getFormismaticQuoteObject(jsonData);
            ArrayList<Quote> quotesBefore =  App.getAllQuote("../app/src/main/resources/recentquotes.json");
            Quote quote = new Quote(quoteApi.getQuoteText(),quoteApi.getQuoteAuthor());
            App.addToJsonFile("../app/src/main/resources/recentquotes.json",quoteApi,quote);
            ArrayList<Quote> quotesAfter =  App.getAllQuote("../app/src/main/resources/recentquotes.json");

            assertEquals("The num of quotes increase",true,quotesAfter.size() == quotesBefore.size() + 1 );
            assertEquals("the last quote author is equal to the new quote author",quote.getAuthor(),quotesAfter.get(quotesAfter.size() -1).getAuthor());
            assertEquals("the last quote text is equal to the new quote text",quote.getText(),quotesAfter.get(quotesAfter.size() -1).getText());

        }
        catch (Exception ex){
            System.out.println(ex);
        }

    }
}
