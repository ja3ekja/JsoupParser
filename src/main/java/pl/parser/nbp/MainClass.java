package pl.parser.nbp;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.Arrays;

/**
 * Created by HP on 2016-02-14.
 */
public class MainClass {

    public static void main(String args[]) throws IOException, ParserConfigurationException, SAXException {

        String link = new StringBuilder("http://api.nbp.pl/api/exchangerates/rates/C/").append(args[0].toUpperCase()).append("/").append(args[1]).append("/").append(args[2]).append("/?format=xml").toString();

        Document doc = Jsoup.connect(link).get();
        double bidAverage = Arrays.stream(doc.select("Bid").text().split(" ")).mapToDouble(a -> Double.parseDouble(a)).average().getAsDouble();
        System.out.println(bidAverage);

        final String[] asks = doc.select("Ask").text().split(" ");
        double askAverage = Arrays.stream(asks).mapToDouble(a -> Double.parseDouble(a)).average().getAsDouble();
        double askStdDev = Math.sqrt(Arrays.stream(asks).mapToDouble(a -> Math.pow(Double.parseDouble(a) - askAverage, 2)).average().getAsDouble());
        System.out.println(askStdDev);
    }

}

