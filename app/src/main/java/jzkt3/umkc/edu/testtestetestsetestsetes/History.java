package jzkt3.umkc.edu.testtestetestsetestsetes;

/**
 * Created by jzz on 4/9/2015.
 */
public class History {
    private String date;
    private String time;
    private String name;
    private String rate;
    private String dollars;
    private String result;

    public History() {}

    public History(String date,String time,String name,String rate,String dollars,String result){
        this.date = date;
        this.time = time;
        this.name = name;
        this.rate = rate;
        this.dollars = dollars;
        this.result = result;
    }

    public boolean setDate(String date){
        this.date = date;
        return true;
    }
    public String getDate(){
        return date;
    }

    public boolean setTime(String time){
        this.time = time;
        return true;
    }
    public String getTime(){
        return time;
    }

    public boolean setName(String name){
        this.name = name;
        return true;
    }
    public String getName(){
        return name;
    }

    public boolean setRate(String rate){
        this.rate = rate;
        return true;
    }
    public String getRate(){
        return rate;
    }

    public boolean setDollars(String dollars){
        this.dollars = dollars;
        return true;
    }
    public String getDollars(){
        return dollars;
    }

    public boolean setResult(String result){
        this.result = result;
        return true;
    }
    public String getResult(){
        return result;
    }
}
