import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

abstract class Web implements CurrencyCalculator 
{
    protected String address_format;
    protected String tag_class;

    //@Override
    public double calculate(final double value, final String from, final String to) {
        //Setup
        String address = String.format(address_format, value, from, to);

        try {                
            //Request
            URL url = new URL(address);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            Thread.sleep(4000); //Sleep to prevent DDoS
            con.setRequestMethod("GET");
        
            //Retrieve
            InputStream stream = con.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(stream));
            String result = text_tag(br, tag_class);
            
            //Stop
            con.disconnect();
            br.close();

            return Double.valueOf(result); //result
        } 

        catch (MalformedURLException e) {
            System.err.println("Error: " + address + " is faulty");
        }
        
        catch (IOException e) {
            System.err.println("Error: Problem reading html file");
        }
        
        catch (InterruptedException e) {
            System.err.println("Error: Problem sleeping");
        }

        return 0.0;
    }

    public String text_tag(BufferedReader html, String tag_class) {
        String line;
        String innerText = "";
        int taglv = 1;
        
        try {
            //Find starting tag
			while ((line = html.readLine()) != null) {
			    if (line.contains(tag_class)) break;
            }

            //Moves line upto tag
            if (line == null) return "";
            line = line.substring(line.indexOf(tag_class) + tag_class.length() - 1);

            do {
                //Moves to next line if no more tags
                if (line.indexOf("<") < 0) {
                    innerText += line;
                    line = html.readLine();
                }
                
                //Moves to start of inner text for tag
                line = line.substring(line.indexOf(">") + 1);

                //Results of inner text
                innerText += line.substring(0, line.indexOf("<"));

                //Alters levels deep in the html tags through opening and closing tags
                if (line.indexOf("</") == line.indexOf("<")) taglv--;
                else taglv++;
            } while (line != null && taglv > 0);
        } 
        
        catch (IOException e) {
			System.err.println("Error: Problem reading html file");
        }

        //Removes all alphabetical symbols
        innerText = innerText.replaceAll("[^\\d.]", "");
        return innerText;
    }
}

class CalculateNet extends Web {
    public CalculateNet() {
        address_format = "https://www.calculator.net/currency-calculator.html?eamount=%s&efrom=%s&eto=%s&type=1&x=0&y=0";
        tag_class = "<font color=green><b>";
    }
}

class XRate extends Web {
    public XRate() {
        address_format = "https://www.x-rates.com/calculator/?amount=%s&from=%s&to=%s";
        tag_class = "<span class=\"ccOutputRslt\">";
    }
}

class XE extends Web {
    public XE() {
        address_format = "https://www.xe.com/currencyconverter/convert/?Amount=%s&From=%s&To=%s";
        tag_class = "<span class=\"converterresult-toAmount\">";
    }
} 
