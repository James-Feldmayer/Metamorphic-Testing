
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
        
        String x_rates = String.format("https://www.x-rates.com/calculator/?amount=%s&from=%s&to=%s", "60.06", "AUD", "GBP");
        String calculator_net = String.format("https://www.calculator.net/currency-calculator.html?eamount=%s&efrom=%s&eto=%s&type=1&x=0&y=0", "60.06", "AUD", "GBP");
        //String xe = String.format("https://www.xe.com/currencyconverter/convert/?Amount=%s&From=%s&To=%s", "60.06", "AUD", "GBP");

        //
    }
}
 
class Exchange {
    private int paraSize;
    private String address;
    private ArrayList<String> paramaters;
    private ArrayList<String> values;
    private String tagClass;

    Exchange(String address, ArrayList<String> paramaters, ArrayList<String> values, String tagClass) {
        this.address = address;
        this.paramaters = paramaters;
        this.values = values;
        this.tagClass = tagClass;
        if (paramaters.size() > values.size()) paraSize = values.size();
        else paraSize = paramaters.size();
    }

    public String call() {
        //Setup
        String finalAddress = address;
        if (paraSize > 0) {
            finalAddress += "?" + paramaters.get(0) + "=" + values.get(0);
        }
        for (int i = 1; i < paraSize; i++) {
            finalAddress += "&" + paramaters.get(i) + "=" + values.get(i);
        }
        System.out.println(finalAddress);

        try {
            //Request
            URL url = new URL(finalAddress);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            //Retrieve
            InputStream stream = con.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(stream));
            String result = "";
            String line;
            while ((line = br.readLine()) != null) {
                if (line.contains(tagClass)) {
                    result = line.substring(line.indexOf(tagClass) + tagClass.length(), line.length());
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

