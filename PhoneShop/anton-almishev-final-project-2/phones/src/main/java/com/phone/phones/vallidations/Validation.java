package com.phone.phones.vallidations;

import com.phone.phones.exception.InvalidException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Year;

import static com.phone.phones.messages.OutputMessages.*;

@Component
public class Validation {



    public void priceMustBeBiggerThanZero(BigDecimal price){
        if (price.compareTo(BigDecimal.ZERO)<0){
            throw new InvalidException(INVALID_PRICE);
        }

    }
    public void quantityMustBeBiggerThanZero(int quantity) {
        if (quantity < 0) {
            throw new InvalidException(INVALID_QUANTITY);
        }
    }


    public void dateMustBeEarlierThanToday(LocalDate date){
        if(date.compareTo(LocalDate.now())>0){ //isbefore
           throw new InvalidException(INVALID_DATE);
        }

    }

    public  void yearMustBeReal(int year){
        if(year>Year.now().getValue() || year<1877){
            throw  new InvalidException(INVALID_YEAR);
        }
    }
}
