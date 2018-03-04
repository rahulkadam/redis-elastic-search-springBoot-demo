package com.example.demo.dto;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

/**
 * Creating price abstract class for understanding abstract class and code refactoring
 */
public abstract class Price {
    public abstract Float getCalculatedPrice(float price);

    public Date getExpirationDate(Date creationDate , int durationDays) {
        if(creationDate == null){
            return new Date();
        }

        Date today = new Date();
        long duration  =creationDate.getTime() - creationDate.getTime();
        long noOfDays = TimeUnit.MILLISECONDS.toDays(duration);
        if (noOfDays > durationDays) {
            return today;
        } else {
            return addDays(creationDate , durationDays);
        }
    }


    public Date getExpirationDate(Date creationDate) {
        return getExpirationDate(creationDate , 15);
     }

    public Date addDays(Date date , int number) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.add(Calendar.DATE, number);
        return cal.getTime();
    }
}
