
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

public class Web {

    public static void main(String[] args) { 

        String from = Util.randomCurrency();
        String to = Util.randomCurrency();
        double amount = Util.random_money(10000); //partition testing?
        
        //exchange(String.format("https://www.xe.com/currencyconverter/convert/?Amount=%s&From=%s&To=%s", "60.06", "AUD", "GBP"), "converterresult-toAmount");
        exchange(String.format("https://www.x-rates.com/calculator/?amount=%s&from=%s&to=%s", "60.06", "AUD", "GBP"), "ccOutputRslt\">");
        exchange(String.format("https://www.calculator.net/currency-calculator.html?eamount=%s&efrom=%s&eto=%s&type=1&x=0&y=0", "60.06", "AUD", "GBP"), "<font color=green><b>");
    }

    public static String exchange(String address, String tag_class) {
        //

        try {
            //Request
            URL url = new URL(address);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            //Retrieve
            InputStream stream = con.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(stream));
            String result = "";
            String line;
            while ((line = br.readLine()) != null) {
                if (line.contains(tag_class)) {
                    result = line.substring(line.indexOf(tag_class) + tag_class.length(), line.length());
                    result = result.substring(0, result.indexOf("<"));
                    break;
                }
            }
            //Stop
            br.close();
            con.disconnect();
            return result;
        } catch (Exception e) {
            System.out.println("Help");
        }
        return "";
    }

}

