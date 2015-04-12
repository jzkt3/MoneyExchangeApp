package jzkt3.umkc.edu.testtestetestsetestsetes;

/**
 * Created by jzz on 3/1/2015.
 */
public class Rate {
    private String name;
    private double exchangeRate;
    private String fullName;

    public Rate(){

    }

    public Rate(String name,double exchangeRate,String fullName){

        this.name = name;
        this.exchangeRate = exchangeRate;
        this.fullName = fullName;
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

    public void setFullName(String fullName){
        this.fullName = fullName;
    }
    public String getFullName(){
        return fullName;
    }

    @Override
    public String toString(){
        return  fullName;
    }

}



