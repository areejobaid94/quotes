package quotes;

import java.util.ArrayList;
import java.util.Objects;

public class Quote {
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    private String author;
    private String  text;

    public Quote() {
    }

    public Quote(String author, String text) {
        this.author = author;
        this.text = text;
    }


    @Override
    public String toString() {
        return "The quote is: " + this.text+ '\n'+
                "Written by: " + this.author;
    }
}
