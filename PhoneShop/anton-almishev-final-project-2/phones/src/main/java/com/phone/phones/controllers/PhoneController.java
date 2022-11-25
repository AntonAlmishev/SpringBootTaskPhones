package com.phone.phones.controllers;

import com.phone.phones.dto.AvailabilityRequest;
import com.phone.phones.dto.PhoneRequest;
import com.phone.phones.models.Availability;
import com.phone.phones.models.Phone;
import com.phone.phones.models.PhoneView;
import com.phone.phones.services.PhoneService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/phone")
public class PhoneController {

    private final PhoneService service;


    public PhoneController(PhoneService service) {
        this.service = service;
    }

    @GetMapping("/home")
    public String welcome(){
        return  "Rest services:                                             " +
                "1. Добавяне на нов телефон към даден магазин и наличност \n" +
                "2. Изтриване на телефон от даден магазин.                \n" +
                "3.Показване на телефона с минимална цена по зададени     \n" +
                " марка и година на производство .                        \n" +
                "4.Промяна на количеството определена стока в определен     " +
                "магазин.                                                    " ;
    }



    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{phoneId}/{shop}")
    public Availability showAvByPhoneIdAndShop(@PathVariable("phoneId") Integer phoneId,@PathVariable("shop") String shop) {

        return  service.giveAvByPhoneIdAndShop(phoneId,shop);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)//OK
    @DeleteMapping("/bye/{phoneId}/{shop}")
    public void deletePhoneFromTheShop(@PathVariable("phoneId") Integer phoneId,@PathVariable("shop") String shop) {

           service.deletePhoneFromTheStore(phoneId, shop);
    }

    @GetMapping("/{brand}")
    public ResponseEntity<PhoneView> showPhoneBrandWithMinPrice (@PathVariable("brand") String brand){
        PhoneView phoneView = service.givePhoneBrandWithMinPrice(brand);
        return new ResponseEntity<>(phoneView,HttpStatus.OK);
    }


    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/year/{brand}/{year}")
    public PhoneView showPhoneBrandWithMinPriceAndYer (@PathVariable("brand") String brand,@PathVariable("year") Integer year){

        return service.givePhoneBrandWithMinPriceAndYear(brand,year);

    }


    @PostMapping("/phone")
    public ResponseEntity<Phone> createNewPhone( @RequestBody PhoneRequest phone){

        Integer id = service.addPhoneInPhones(phone);
        Phone newPhone =new Phone(id, phone.getBrand(), phone.getModel(), phone.getDate());
        return  new ResponseEntity<Phone>(newPhone, HttpStatus.CREATED);
    }


    @PostMapping("/stock")
    public ResponseEntity<String> addPhoneInTheStock(@RequestBody AvailabilityRequest av){
        Integer id = service.addPhoneInStocks(av);
        return new ResponseEntity<String>("Phone with ID "+av.getPhoneId()+" has been added in shop "+av.getShop()+
                ". It's stock witsh stockId : "+id,HttpStatus.CREATED);

    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/quantity/")
    public String changeQuantity(@RequestParam Integer quantity, @RequestParam Integer stockId) {
        return service.updateQuantity(quantity,stockId);
    }


}
