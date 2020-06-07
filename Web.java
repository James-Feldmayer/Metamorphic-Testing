import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

public class Web {
    public static void main(String[] args) {
        String from = Web.randomCurrency();
        String to = Web.randomCurrency();
 
        double amount = (double) ((int) (Math.random() * 10000)) / 100;

        String xr = Xrates(String.valueOf(amount), from, to);
        String cn = calcNet(String.valueOf(amount), from, to);

        System.out.println(from + " " + amount + " = " + to + " " + xr);
        System.out.println(from + " " + amount + " = " + to + " " + cn);
    }

    static String randomCurrency() {
        String[] currencies = {"GBP","EUR","JPY","CHF","USD","ARS","AED","AUD"};
 
        int randomIndex = (int) (Math.random() * currencies.length);
 
        return currencies[randomIndex];
    }

    //Doesn't allow auto extraction :(
    public static String XEchange(String amount, String from, String to) {
        String address = String.format("https://www.xe.com/currencyconverter/convert/?Amount=%s&From=%s&To=%s", amount, from, to);
        Exchange xeapi = new Exchange(address, "converterresult-toAmount");
        String response = xeapi.call();
        return response;
    }

    public static String Xrates(String amount, String from, String to) {
        String address = String.format("https://www.x-rates.com/calculator/?amount=%s&from=%s&to=%s", amount, from, to);
        Exchange xrateapi = new Exchange(address, "ccOutputRslt\">");
        String response = xrateapi.call();
        return response;
    }

    public static String calcNet(String amount, String from, String to) {
        String address = String.format("https://www.calculator.net/currency-calculator.html?eamount=%s&efrom=%s&eto=%s&type=1&x=0&y=0", amount, from, to);
        Exchange xrateapi = new Exchange(address, "<font color=green><b>");
        String response = xrateapi.call();
        return response;
    }
}


class Exchange {
    private String address;
    private String tagClass;

    Exchange(String address, String tagClass) {
        this.address = address;
        this.tagClass = tagClass;
    }

    public String call() {  
        System.out.println(address);
        try {
            //Request
            URL url = new URL(address);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            //Retrieve
            InputStream stream = con.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(stream));
            String result = textTag(br);
            //Stop
            con.disconnect();
            br.close();
            return result;
        } catch (Exception e) {
            System.out.println("Help");
        }
        return "";
    }

    public String textTag(BufferedReader html) {
        boolean resultFlag = false;
        String line;
        String innerText = "";
        int taglv = 1;
        //If </ at next char +1, else -1
        try {
			htmlLoop: while ((line = html.readLine()) != null) {
			    if (line.contains(tagClass)) {
                    resultFlag = true;
                    line = line.substring(line.indexOf(tagClass));
                }
                if (!resultFlag) continue;
                //Checking within the lines
                while (line.indexOf(">") > 0) {
                    line = line.substring(line.indexOf(">") + 1);
                    innerText += line.substring(0, line.indexOf("<")); //Results between start tag and next tag
                    if (line.indexOf("<") < 0) break;
                    //Checking levels deep in the html tags
                    if (line.indexOf("</") == line.indexOf("<")) taglv--;
                    else taglv++;
                    if (taglv < 1) break htmlLoop;
                }
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
        }
        innerText = innerText.replaceAll("[^\\d.]", "");
        return innerText;
    }
}
