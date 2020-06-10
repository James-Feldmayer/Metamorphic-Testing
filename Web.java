import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

abstract class Web implements CurrencyCalculator 
{
    protected String address_format;
    protected String tag_class;

    @Override
    public double calculate(final double value, final String from, final String to) {
        //Setup
        String address = String.format(address_format, value, from, to);

        try {                
            //Request
            URL url = new URL(address);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
    
            //Retrieve 
            //Get the InputStream from the HttpURLConnection and capture using a BufferedReader
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));

            //Scape
            String result = html_scape(br, tag_class);
            
            //Stop
            con.disconnect();
            br.close();

            //Sleep
            Thread.sleep(300); //prevent accidental DOS attack (300ms)

            return Double.valueOf(result);
        } 

        catch(InterruptedException e) { //related to Thread.Sleep
            System.err.print("interrupted exception"); //unsure what would cause this
        }

        catch (MalformedURLException e) {
            System.err.println("Error: " + address + " is faulty");
        }
        
        catch (IOException e) {
            System.err.println("Error: Problem reading html file");
        }

        return 0.0;
    }

    //refactor html_scape, maybe change the name?

    public static String html_scape(BufferedReader html, String tag_class) {
        String line;
        String innerText = "";
        int taglv = 0;
        
        try {
            //Find starting tag
			while ((line = html.readLine()) != null) {
			    if (line.contains(tag_class)) break;
            }

            //Moves line upto tag
            if (line == null) return "";
            line = line.substring(line.indexOf(tag_class));

            do {
                //Moves to next line if no more tags
                if (line.indexOf("<") < 0) {
                    innerText += line;
                    line = html.readLine();
                    continue;
                }

                //Alters levels deep in the html tags through opening and closing tags
                if (line.indexOf("</") == line.indexOf("<")) taglv--;
                else taglv++;
                if (taglv <= 0) break;
                
                //Moves to start of inner text for tag
                line = line.substring(line.indexOf(">") + 1);

                //Results of inner text
                innerText += line.substring(0, line.indexOf("<") < 0 ? line.length() : line.indexOf("<"));
                line = line.substring(line.indexOf("<") < 0 ? line.length() : line.indexOf("<"));
            } while (line != null);
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

abstract class XE extends Web { //this site does not allow automated requests
    public XE() {
        address_format = "https://www.xe.com/currencyconverter/convert/?Amount=%s&From=%s&To=%s";
        tag_class = "<span class=\"converterresult-toAmount\">";
    }
} 

class YourSite extends Web {
    public YourSite(String address_format, String tag_class) {
        this.address_format = address_format;
        this.tag_class = tag_class;
    }
} 
