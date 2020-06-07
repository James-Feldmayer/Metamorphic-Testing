
import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;
import java.util.Arrays;

public class Client 
{
    public static void main(String[] args) 
    {   
        Oracle oracle = new Oracle();
        oracle.start();
    }    
}

/*
Calculator - SUT
Client - Main method
Oracle - Our solution
OracleTest - TDD (junit) our solution
*/ 
