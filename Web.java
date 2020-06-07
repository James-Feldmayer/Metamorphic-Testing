import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

public class Web {

    public static void main(String[] args) { 

        //mine should do the same

        double amount = Util.random_money(10000); //partition testing?
        String from = Util.randomCurrency();
        String to = Util.randomCurrency();

        //

        String a = exchange(xr_url(amount, from, to), "ccOutputRslt\">");
        System.out.println(a);
    
        String b = exchange(cn_url(amount, from, to), "<font color=green><b>");
        System.out.println(b);

        //String c = exchange(xe_url(amount, from, to), "converterresult-toAmount");
        //System.out.println(c);

        return;
    }

    public static String xr_url(double amount, String from, String to) {
        return String.format("https://www.x-rates.com/calculator/?amount=%s&from=%s&to=%s", amount, from, to);
    }

    public static String cn_url(double amount, String from, String to) {
        return String.format("https://www.calculator.net/currency-calculator.html?eamount=%s&efrom=%s&eto=%s&type=1&x=0&y=0", amount, from, to);
    }

    public static String xe_url(double amount, String from, String to) {
        return String.format("https://www.xe.com/currencyconverter/convert/?Amount=%s&From=%s&To=%s", amount, from, to);
    }

    public static String exchange(String address, String tag_class) {
        try {
            //Request
            URL url = new URL(address);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
        
            //Retrieve
            InputStream stream = con.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(stream));
            String result = text_tag(br, tag_class);
            
            //Stop
            con.disconnect();
            br.close();
        
            return result;
        } 

        catch (MalformedURLException e) {
            System.err.println("Error: " + address + " is faulty");
        }
        
        catch (IOException e) {
            System.err.println("Error: Problem reading html file");
        }

        return "Error";
    }

    public static String text_tag(BufferedReader html, String tag_class) {
        boolean resultFlag = false;
        String line;
        String innerText = "";
        int taglv = 1;
        
        try {
			htmlLoop: while ((line = html.readLine()) != null) {
			    if (line.contains(tag_class)) {
                    resultFlag = true;
                    line = line.substring(line.indexOf(tag_class));
                }

                if (!resultFlag) continue;

                //Checking within the lines
                while (line.indexOf(">") > 0) {
                    
                    //Moves to start of innerText for next tag
                    line = line.substring(line.indexOf(">") + 1);

                    //Results between start tag and next tag
                    innerText += line.substring(0, line.indexOf("<"));
                    if (line.indexOf("<") < 0) break;

                    //Alters levels deep in the html tags through opening and closing tags
                    if (line.indexOf("</") == line.indexOf("<")) taglv--;
                    else taglv++;
                    if (taglv < 1) break htmlLoop;
                }
			}
        } 
        
        catch (IOException e) {
			System.err.println("Error: Problem reading html file");
        }

        //Removes all alphabetical symbols
        innerText = innerText.replaceAll("[^\\d.]", "");
        return innerText;
    }
}
