
public class Client 
{
    public static void main(String[] args) 
    {   
        Oracle oracle = new Oracle(new CalculateNet()); 
            //CalculateNet
            //YourSite
            //XRate
            //Offline

        oracle.start();
    }    
}
