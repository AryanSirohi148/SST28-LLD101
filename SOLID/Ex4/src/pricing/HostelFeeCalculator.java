package pricing;

import common.Money;

import java.util.*;
//Responsible for Fee Calculation
public class HostelFeeCalculator {



    public static Money calculateMonthly(List<PricingComponent> list) {
        Money amount = new Money(0);
        for(PricingComponent com : list){
            amount = amount.plus(com.cost());
        }


        return amount;
    }
}
