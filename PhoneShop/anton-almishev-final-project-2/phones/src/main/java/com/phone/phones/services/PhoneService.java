package com.phone.phones.services;

import com.phone.phones.dto.AvailabilityRequest;
import com.phone.phones.dto.PhoneRequest;
import com.phone.phones.exception.InvalidException;
import com.phone.phones.models.Availability;
import com.phone.phones.models.Phone;
import com.phone.phones.models.PhoneView;
import com.phone.phones.repository.PhoneRepository;
import com.phone.phones.vallidations.Validation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.relational.core.sql.In;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

import static com.phone.phones.messages.ExceptionMessages.*;
import static com.phone.phones.messages.OutputMessages.*;

@Service
public class PhoneService {


    private final PhoneRepository phoneRepository;
    private final Validation validation;

    public PhoneService(PhoneRepository phoneRepository, Validation validation) {
        this.phoneRepository = phoneRepository;
        this.validation = validation;
    }

    public Integer addPhoneInStocks(AvailabilityRequest av) {
        phoneRepository.isPhoneIdExist(av.getPhoneId()).orElseThrow(() -> new InvalidException(INVALID_PHONE_ID));
        phoneRepository.isShopExist(av.getShop()).orElseThrow(() -> new InvalidException(INVALID_SHOP_NAME));
        validation.priceMustBeBiggerThanZero(av.getPrice());
        validation.quantityMustBeBiggerThanZero(av.getQuantity());
        return phoneRepository.addPhoneInStocks(av);

    }

    public Integer addPhoneInPhones(PhoneRequest newPhone) {
        validation.dateMustBeEarlierThanToday(newPhone.getDate());
        if(phoneRepository.isTheSamePhoneExist(newPhone.getModel())){
            throw new InvalidException(INVALID_BRAND);
        }
        return phoneRepository.addPhoneInPhones(newPhone);

    }

    public Integer deletePhoneFromTheStore(int phoneId, String shop) {

        phoneRepository.isPhoneIdExist(phoneId).orElseThrow(() -> new InvalidException(INVALID_PHONE_ID));
        phoneRepository.isShopExist(shop).orElseThrow(() -> new InvalidException(INVALID_SHOP_NAME));
        if (phoneRepository.getQuantityByPhoneIdAndShop(phoneId, shop) > 0) {
            throw new InvalidException(INVALID_QUANTITY);
        }

        return phoneRepository.deletePhoneFromTheStore(phoneId, shop);
    }

    public String updateQuantity(int quantity, int stockId) {
        phoneRepository.isStockIdExist(stockId).orElseThrow(() -> new InvalidException(INVALID_STOCK_ID));
        validation.quantityMustBeBiggerThanZero(quantity);
        phoneRepository.updateQuantity(quantity, stockId);
        return SUCCESSFUL_UPDATING;
    }


    public Availability giveAvByPhoneIdAndShop(int phoneId, String shop) {
        phoneRepository.isPhoneIdExist(phoneId).orElseThrow(()-> new InvalidException(INVALID_PHONE_ID));
        phoneRepository.isShopExist(shop).orElseThrow(()-> new InvalidException(INVALID_SHOP_NAME));
        return phoneRepository.giveAvByPhoneIdAndShop(phoneId, shop);

    }

    public PhoneView givePhoneBrandWithMinPrice(String brand) {
        phoneRepository.isBrandExist(brand).orElseThrow(() -> new InvalidException(INVALID_BRAND));
        return phoneRepository.showPhoneWithMinPriceByBrand(brand);
    }

    public PhoneView givePhoneBrandWithMinPriceAndYear(String brand, int year) {
        phoneRepository.isBrandExist(brand).orElseThrow(() -> new InvalidException(INVALID_BRAND));
        validation.yearMustBeReal(year);
        return phoneRepository.showPhoneWithMinPriceByBrandAndYear(brand,year);
    }

}
