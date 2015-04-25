package jzkt3.umkc.edu.testtestetestsetestsetes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by jzz on 4/24/2015.
 */
public class Sorter {
    public void sortByRate(ArrayList<Rate> rates){
        Collections.sort(rates,new Comparator<Rate>() {
            @Override
            public int compare(Rate lhs, Rate rhs) {
                String x = Double.toString(lhs.getExchangeRate());
                String y = Double.toString(rhs.getExchangeRate());
                return x.compareTo(y);
            }
        });
    }
}
