package quotes;
import org.junit.Test;

import java.util.ArrayList;

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
}
