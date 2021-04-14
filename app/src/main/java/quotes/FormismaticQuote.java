package quotes;

public class FormismaticQuote {
    public String getQuoteAuthor() {
        return quoteAuthor;
    }

    public void setQuoteAuthor(String quoteAuthor,String quoteText) {
        this.quoteAuthor = quoteAuthor;
        this.quoteText = quoteText;
    }

    public String getQuoteText() {
        return quoteText;
    }

    public void setQuoteText(String quoteText) {
        this.quoteText = quoteText;
    }

    private String quoteAuthor;
    private String  quoteText;

    public FormismaticQuote() {
    }

    public FormismaticQuote(String author, String body) {
        this.quoteAuthor = author;
        this.quoteText = body;
    }


    @Override
    public String toString() {
        return "The quote is: " + this.quoteText+ '\n'+
                "Written by: " + this.quoteAuthor;
    }
}
