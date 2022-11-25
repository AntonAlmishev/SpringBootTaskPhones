package com.phone.phones.repository;

import com.phone.phones.dto.AvailabilityRequest;
import com.phone.phones.dto.PhoneRequest;
import com.phone.phones.models.Availability;
import com.phone.phones.models.PhoneView;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
@RunWith(SpringRunner.class)
class PhoneRepositoryTest {

    @Autowired
   private PhoneRepository repository;

    @Test
    public void testDelete(){

        repository.deletePhoneFromTheStore(11,"Zora");
        assertNull(repository.giveAvByPhoneIdAndShop(11,"Zora"));
    }

    @Test
    public void testGiveAvByPhoneIdAndShop(){
        Availability avv = repository.giveAvByPhoneIdAndShop(5,"Zora");
        assertEquals("Zora",avv.getShop());
    }

    @Test
    public void getPhoneWithMinPriceByBrand(){
        PhoneView view = repository.showPhoneWithMinPriceByBrand("Samsung");
        assertEquals(view.getPrice(),new BigDecimal(1500));
    }

    @Test
    public void testCreatePhone(){
        PhoneRequest phone = new PhoneRequest("Siemens","c25",LocalDate.of(2004, Month.APRIL, 1));
         int n = repository.addPhoneInPhones(phone);
        assertThat(n>0);

    }


    @Test
    public void testInsertInTheStock(){

        AvailabilityRequest avv = new AvailabilityRequest(5,"Emag",new BigDecimal(400) ,10);
        int n = repository.addPhoneInStocks(avv);
        assertThat(n>0);

    }





}