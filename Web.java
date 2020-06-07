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

        //

        double amount = Util.random_money(10000); //partition testing?
        String from = Util.randomCurrency();
        String to = Util.randomCurrency();

        //

        String xr_url = String.format("https://www.x-rates.com/calculator/?amount=%s&from=%s&to=%s", amount, from, to);
        String xr_tag_class = "ccOutputRslt\">";
        String xr_output = exchange(xr_url, xr_tag_class);
        System.out.println(xr_output);

        String cn_url = String.format("https://www.calculator.net/currency-calculator.html?eamount=%s&efrom=%s&eto=%s&type=1&x=0&y=0", amount, from, to); 
        String cn_tag_class = "<font color=green><b>";
        String cn_output = exchange(cn_url, cn_tag_class);
        System.out.println(cn_output);

        //String xe_url = String.format("https://www.xe.com/currencyconverter/convert/?Amount=%s&From=%s&To=%s", amount, from, to);
        //String xe_tag_class = "converterresult-toAmount";
        //String xe_output = exchange(xe_url, xe_tag_class);
        //System.out.println(xe_output);

        return;
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
        catch (Exception e) {
            System.err.println("Http url connection");
            System.err.println("Buffered reader");
        }

        return "Error";
    }

    //confusing
    public static String text_tag(BufferedReader html, String tag_class) {
        boolean resultFlag = false;
        String line;
        String innerText = "";
        int taglv = 1;
        //If </ at next char +1, else -1
        try {
			htmlLoop: while ((line = html.readLine()) != null) {
			    if (line.contains(tag_class)) {
                    resultFlag = true;
                    line = line.substring(line.indexOf(tag_class));
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
            
            //can you please name your errors?

            //System.err.println
        }
        innerText = innerText.replaceAll("[^\\d.]", "");
        return innerText;
    }

}

