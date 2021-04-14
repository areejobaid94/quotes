/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package quotes;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Scanner;
import java.net.HttpURLConnection;
import java.net.URL;

public class App {
    static ArrayList<Quote> quotes = new ArrayList<Quote>();

    public static void main(String[] args) {
        try {

            System.out.println(getQuote("./app/src/main/resources/recentquotes.json"));
            // enter the word
            System.out.println( getByWord("./app/src/main/resources/recentquotes.json"));

            // enter the author name
            System.out.println(getByAuthorName("./app/src/main/resources/recentquotes.json"));

            // Get Json From first API
            URL url = new URL("http://api.forismatic.com/api/1.0/?method=getQuote&format=json&lang=en");
            String jsonData = getJsonFromAPI(url);
            FormismaticQuote quoteApi = getFormismaticQuoteObject(jsonData);

            //Add to json file:
            Quote quote = new Quote(quoteApi.getQuoteText(),quoteApi.getQuoteAuthor());
            System.out.println(quotes);
            addToJsonFile("./app/src/main/resources/recentquotes.json",quoteApi,quote);
            System.out.println(quoteApi);

            // Get Json From 2nd API
            URL url2nd = new URL("http://ron-swanson-quotes.herokuapp.com/v2/quotes");
            String jsonData2ndApi = getJsonFromAPI(url2nd);
            String quote2ndApi = getRonSwansonQuotesObject(jsonData2ndApi);

            //Add to json file:
            Quote quote2nd = new Quote(quote2ndApi,"Not mentioned");
            addToJsonFile("./app/src/main/resources/recentquotes.json",quoteApi,quote);
            System.out.println(quote2ndApi);

        } catch (Exception e) {
            System.out.println(getQuote("./app/src/main/resources/recentquotes.json"));
        }
    }

    private static final Type REVIEW_TYPE = new TypeToken<ArrayList<Quote>>() {
    }.getType();

    private static final Type REVIEW_TYPE_Quote_Api = new TypeToken<ArrayList<String>>() {
    }.getType();

    public static ArrayList<Quote> getAllQuote(String path){
        try{
                Reader reader = new FileReader(path);
                Gson gson = new Gson();
                quotes = gson.fromJson(reader,REVIEW_TYPE);

        }catch (Exception ex){
            System.out.println(ex);
        };
        return  quotes;
    };

    public static Quote getQuote(String path){
        getAllQuote(path);
        Quote quote = quotes.get((int)(Math.random() * quotes.size()));
        return quote ;
    };

    // Stretch Goals get By Author Name with command line
    public static ArrayList<Quote> getByAuthorName(String path){
        Scanner input = new Scanner(System.in);
        System.out.println("Enter the name of the author: ");
        String author = input.nextLine();
        System.out.println(author);
        getAllQuote(path);
        ArrayList<Quote> authorQuotes = new ArrayList<Quote>();
        for (Quote quote: quotes){
            if(quote.getAuthor().equals(author)){
                authorQuotes.add(quote);
            }
        }
        return authorQuotes;
    };

    // Stretch Goals get By Author Name without command line
    public static ArrayList<Quote> getByAuthorName(String path, String author){
        getAllQuote(path);
        ArrayList<Quote> authorQuotes = new ArrayList<Quote>();
        for (Quote quote: quotes){
            if(quote.getAuthor().equals(author)){
                authorQuotes.add(quote);
            }
        }
        return authorQuotes;
    };

    // Stretch Goals get By Word with command line
    public static ArrayList<Quote> getByWord(String path){
        Scanner input = new Scanner(System.in);
        System.out.println("Enter the word: ");
        String word = input.nextLine();
        getAllQuote(path);
        ArrayList<Quote> authorQuotes = new ArrayList<Quote>();
        for (Quote quote: quotes){
            if(quote.getText().contains(word)){
                authorQuotes.add(quote);
            }
        }
        return authorQuotes;
    };

    // Stretch Goals get By Word without command line
    public static ArrayList<Quote> getByWord(String path , String word){
        getAllQuote(path);
        ArrayList<Quote> authorQuotes = new ArrayList<Quote>();
        for (Quote quote: quotes){
            if(quote.getText().contains(word)){
                authorQuotes.add(quote);
            }
        }
        return authorQuotes;
    };

    public static String getJsonFromAPI(URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        int status = connection.getResponseCode();
        String content = "";
        if(status == 200) {
            BufferedReader reader = getBufferedReader(connection);
            content = getContent(reader);
            reader.close();
        } else{
            System.out.println("Error in the API");
        }

        connection.disconnect();

        return content;
    }

    private static String getContent(BufferedReader reader) throws IOException {    //String vs StringBuilder
        StringBuilder builder = new StringBuilder();
        String currentLine = reader.readLine();

        while(currentLine != null){
            builder.append(currentLine);
            currentLine = reader.readLine();
        }
        return builder.toString();
    }

    private static BufferedReader getBufferedReader(HttpURLConnection connection) throws IOException {
        InputStream inputStream = connection.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        return new BufferedReader(inputStreamReader);
    }

    public static FormismaticQuote getFormismaticQuoteObject(String jsonData) {
        Gson gson = new Gson();
        FormismaticQuote quotesApi = gson.fromJson(jsonData, FormismaticQuote.class);
        return quotesApi;
    }
    public static String getRonSwansonQuotesObject(String jsonData) {
        Gson gson = new Gson();
        ArrayList<String> quotesApi = gson.fromJson(jsonData, REVIEW_TYPE_Quote_Api);
        return "The quote is: " + quotesApi.get(0);
    }

    public static void addToJsonFile(String path,FormismaticQuote value,Quote quote){
        try{
            // Get Json From 2nd API
            getAllQuote("./app/src/main/resources/recentquotes.json");
            quotes.add(quote);
            try (Writer writer = new FileWriter(path)) {
                Gson gson = new GsonBuilder().create();
                gson.toJson(quotes, writer);
            }
        }catch (Exception ex){
            System.out.println(ex);
        };
    }
}
