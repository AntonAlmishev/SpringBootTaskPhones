package com.phone.phones.services;

import com.phone.phones.dto.AvailabilityRequest;
import com.phone.phones.dto.PhoneRequest;
import com.phone.phones.models.Availability;
import com.phone.phones.models.PhoneView;
import com.phone.phones.repository.PhoneRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static com.phone.phones.messages.ExceptionMessages.INVALID_STOCK_ID;
import static com.phone.phones.messages.OutputMessages.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@Transactional
@SpringBootTest
class PhoneServiceTest {
    @Autowired
    private PhoneService service;
    @MockBean
    private PhoneRepository repository;

    @Mock
    private PhoneRepository repository1;

    @Test
    public void deleteTest(){

        Availability avail = new Availability(111,4,"Emag",0,new BigDecimal(1100));

        when(repository.deletePhoneFromTheStore(avail.getPhoneId(),avail.getShop())).thenReturn(1);
        when(repository.isPhoneIdExist(avail.getPhoneId())).thenReturn(Optional.of(String.valueOf(avail.getPhoneId())));
        when(repository.isShopExist(avail.getShop())).thenReturn(Optional.of(String.valueOf(avail.getShop())));
        assertEquals(service.deletePhoneFromTheStore(avail.getPhoneId(),avail.getShop())
                ,1);
        verify(repository,times(1))
                .deletePhoneFromTheStore(avail.getPhoneId(),avail.getShop());
    }



    @Test
    public void addNewPhoneInPhonesTest(){

        PhoneRequest phone = new PhoneRequest("Elka","elca000", LocalDate.of(2019,2,1));
        when(repository.isBrandExist(phone.getModel())).thenReturn(Optional.of(phone.getModel()));
        when(repository.addPhoneInPhones(phone)).thenReturn(anyInt());
        assertEquals(repository.addPhoneInPhones(phone),service.addPhoneInPhones(phone));
        verify(repository,times(2)).addPhoneInPhones(phone);
    }

    @Test
    public void addNewPhoneInTheMarket(){

        AvailabilityRequest  availability =  new AvailabilityRequest(7,"Emag",new BigDecimal(1300),3);
        when(repository.isPhoneIdExist(7)).thenReturn(Optional.of(String.valueOf(7)));
        when(repository.isShopExist(availability.getShop())).thenReturn(Optional.of(availability.getShop()));
        when(repository.addPhoneInStocks(availability)).thenReturn(anyInt());
        assertEquals(repository.addPhoneInStocks(availability),service.addPhoneInStocks(availability));
        verify(repository,times(2)).addPhoneInStocks(availability);
    }


    @Test
    public void giveAvByPhoneIdAndShopTest(){

        Availability avail = new Availability(6,2,"Zora",10,new BigDecimal(1300));
        when(repository.isPhoneIdExist(2)).thenReturn(Optional.of(String.valueOf(2)));
        when(repository.isShopExist("Zora")).thenReturn(Optional.of("Zora"));
        when(repository.giveAvByPhoneIdAndShop(2,"Zora")).thenReturn(avail);
        assertEquals(service.giveAvByPhoneIdAndShop(2,"Zora").getStockId(),avail.getStockId());
        verify(repository,times(1)).giveAvByPhoneIdAndShop(2,"Zora");
    }

    @Test
    public void giveIncorrectShopAvByPhoneIdAndShopTest(){

        Availability avail = new Availability(3,1,"Zora",1,new BigDecimal(1600));
        when(repository.giveAvByPhoneIdAndShop(1,"Zora")).thenReturn(avail);
        assertEquals(service.giveAvByPhoneIdAndShop(1,"Zora").getStockId(),avail.getStockId());
        assertNotEquals(service.giveAvByPhoneIdAndShop(1,"Zora").getShop(),"Emag");
        verify(repository,times(2)).giveAvByPhoneIdAndShop(7,"Zora");
    }
    @Test
    public void givePhoneBrandWithMinPriceTest(){


        PhoneView phone = new PhoneView("Samsung","s20ultra",new BigDecimal(1500));

        when(repository.showPhoneWithMinPriceByBrand("Samsung"))
                .thenReturn(new PhoneView("Samsung","s20ultra",new BigDecimal(1500)));
        assertEquals(repository.showPhoneWithMinPriceByBrand("Samsung").getPrice(),
                phone.getPrice());
        assertEquals(repository.showPhoneWithMinPriceByBrand("Samsung").getBrand(),
                phone.getBrand());
        assertEquals(repository.showPhoneWithMinPriceByBrand("Samsung").getModelPhone(),
                phone.getModelPhone());

        verify(repository,times(3)).showPhoneWithMinPriceByBrand("Samsung");

    }

    @Test
    public void wrongGivePhoneBrandWithMinPriceTest(){

    //     Assert.fail("The model phone is different");
        PhoneView phone = new PhoneView("Samsung","s20ultra",new BigDecimal(1500));

        when(repository.showPhoneWithMinPriceByBrand("Samsung"))
                .thenReturn(new PhoneView("Samsung","s20ultra",new BigDecimal(1500)));
        assertEquals(repository.showPhoneWithMinPriceByBrand("Samsung").getPrice(),
                phone.getPrice());
        assertNotEquals(repository.showPhoneWithMinPriceByBrand("Samsung").getBrand(),
                "Nokika");
        assertEquals(repository.showPhoneWithMinPriceByBrand("Samsung").getModelPhone(),
                phone.getModelPhone());

        verify(repository,times(3)).showPhoneWithMinPriceByBrand("Samsung");

    }


    @Test
    public void givePhoneBrandWithMinPriceTestAndYear(){


        PhoneView phone = new PhoneView("Samsung","s20ultra",new BigDecimal(1500));

        when(repository.showPhoneWithMinPriceByBrand("Samsung"))
                .thenReturn(new PhoneView("Samsung","s20ultra",new BigDecimal(1500)));
        assertEquals(repository.showPhoneWithMinPriceByBrand("Samsung").getPrice(),
                phone.getPrice());
        assertEquals(repository.showPhoneWithMinPriceByBrand("Samsung").getBrand(),
                phone.getBrand());
        assertEquals(repository.showPhoneWithMinPriceByBrand("Samsung").getModelPhone(),
                phone.getModelPhone());

        verify(repository,times(3)).showPhoneWithMinPriceByBrand("Samsung");

    }

    @Test
    public void testChangeQuantity() {

        when(repository.updateQuantity(5, 9)).thenReturn(SUCCESSFUL_UPDATING);
        when(repository.isStockIdExist(9)).thenReturn(Optional.of(String.valueOf(9)));
         assertEquals(repository.updateQuantity(5,9),
                 service.updateQuantity(5, 9));
        verify(repository,times(2)).updateQuantity(5,9);
    }


}