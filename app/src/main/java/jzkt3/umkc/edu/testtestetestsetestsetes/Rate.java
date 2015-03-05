package jzkt3.umkc.edu.testtestetestsetestsetes;

/**
 * Created by jzz on 3/1/2015.
 */
public class Rate {
    private String name;
    private double exchangeRate;

    public Rate(){

    }

    public Rate(String name,double exchangeRate){

        this.name = name;
        this.exchangeRate = exchangeRate;
    }


    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return name;
    }

    public void setExchangeRate(double exchangeRate){
        this.exchangeRate = exchangeRate;
    }
    public double getExchangeRate(){
        return exchangeRate;
    }

    @Override
    public String toString(){
        return  "Name: "+name+
                ", Exchange Rate: "+exchangeRate;
    }

}



