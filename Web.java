import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

public class Web {
    public static void main(String[] args) {
        String[] currencies = {"GBP","EUR","JPY","CHF","USD","ARS","AED","AUD"};
        int first = (int) (Math.random() * currencies.length);
        int second = (int) (Math.random() * currencies.length);
        String firstCur = currencies[first];
        String secondCur = currencies[second];
        double amount = (double) ((int) (Math.random() * 10000)) / 100;
        String xr = Xrates(String.valueOf(amount), firstCur, secondCur);
        String cn = calcNet(String.valueOf(amount), firstCur, secondCur);
        System.out.println(firstCur + " " + amount + " = " + secondCur + " " + xr);
        System.out.println(firstCur + " " + amount + " = " + secondCur + " " + cn);
    }

    //Doesn't allow auto extraction :(
    public static String XEchange(String amount, String from, String to) {
        String[] array = {"Amount", "From", "To"};
        ArrayList<String> para = new ArrayList<String>(Arrays.asList(array));
        ArrayList<String> val = new ArrayList<String>();
        val.add(amount);
        val.add(from);
        val.add(to);
        Exchange xeapi = new Exchange("https://www.xe.com/currencyconverter/convert", para, val, "converterresult-toAmount");
        String response = xeapi.call();
        return response;
    }

    public static String Xrates(String amount, String from, String to) {
        String[] array = {"amount", "from", "to"};
        ArrayList<String> para = new ArrayList<String>(Arrays.asList(array));
        ArrayList<String> val = new ArrayList<String>();
        val.add(amount);
        val.add(from);
        val.add(to);
        Exchange xrateapi = new Exchange("https://www.x-rates.com/calculator/", para, val, "ccOutputRslt\">");
        String response = xrateapi.call();
        return response;
    }

    public static String calcNet(String amount, String from, String to) {
        String[] array = {"eamount", "efrom", "eto", "type", "x", "y"};
        ArrayList<String> para = new ArrayList<String>(Arrays.asList(array));
        ArrayList<String> val = new ArrayList<String>();
        val.add(amount);
        val.add(from);
        val.add(to);
        val.add("1");
        val.add("0");
        val.add("0");
        Exchange xrateapi = new Exchange("https://www.calculator.net/currency-calculator.html", para, val, "<font color=green><b>");
        String response = xrateapi.call();
        return response;
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
