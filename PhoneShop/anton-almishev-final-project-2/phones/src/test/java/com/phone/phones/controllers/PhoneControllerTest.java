package com.phone.phones.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.phone.phones.dto.AvailabilityRequest;
import com.phone.phones.dto.PhoneRequest;
import com.phone.phones.models.Availability;
import com.phone.phones.models.Phone;
import com.phone.phones.models.PhoneView;
import com.phone.phones.services.PhoneService;
import org.assertj.core.internal.bytebuddy.pool.TypePool;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;


import static com.phone.phones.messages.OutputMessages.SUCCESSFUL_UPDATING;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class PhoneControllerTest  {

    @Autowired
    public MockMvc mockMvc;
    @Autowired
    public ObjectMapper objectMapper;


    @Test
    @DisplayName("It should fail, because there are no shop with name Sofia")
    @Sql(statements ="insert into availablities (phoneId,shop,price,quantity) values(85,'Zora',1000,0)")
    public void deleteTestFault() throws Exception{
        mockMvc.perform(delete("/phone/bye/{phoneId}/{shop}",85,"Sofia"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("It should delete phone by ID and shopName  from the stock")
    @Sql(statements ="insert into availablities (phoneId,shop,price,quantity) values(85,'Zora',1000,0)")
    public void deleteTest() throws Exception{
        mockMvc.perform(delete("/phone/bye/{phoneId}/{shop}",85,"Zora"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("It should create phone with brand,model and date of maiden")
    public void createNewPhone() throws Exception
    {
       PhoneRequest phone = new PhoneRequest();
       phone.setBrand("Samsung");
       phone.setModel("A45");
       phone.setDate(LocalDate.of(2015,2,1));

       mockMvc
               .perform(post("/phone/phone")
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(objectMapper.writeValueAsString(phone)))
               .andExpect(status().isCreated())
               .andDo(print())
               .andExpect(jsonPath("$.*").isNotEmpty())
               .andExpect(jsonPath("$.*",hasSize(4)))

               .andExpect(jsonPath("$.brand").value("Samsung"))
               .andExpect(jsonPath("$.modelPhone").value("A45"));

    }

    @Test
    @DisplayName("It should insert phone in the market")
    public void insertPhoneInTheMarketWithInvalidId() throws Exception
    {
        AvailabilityRequest av = new AvailabilityRequest(41,"Emag",new BigDecimal(1199),10);

        mockMvc
                .perform(post("/phone/stock")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(av)))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    public void negativeInsertPhoneInTheMarket() throws Exception
    {
        AvailabilityRequest av = new AvailabilityRequest(41,"Emag",new BigDecimal(1199),10);

        mockMvc
                .perform(post("/phone/inStock")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(av)))
                .andExpect(status().isMethodNotAllowed())
                .andDo(print());
    }


    @Test
    @DisplayName("Inserting a phone in the market.")
    @Sql(statements = "insert into availablities (phoneId,shop,price,quantity) values(7,'Zora',1500,0)")
    public void getPhoneInfoByIdAndShopNameTest() throws Exception{

        mockMvc.perform(get("/phone/7/Zora"))

                 .andExpect(status().isOk())
                 .andDo(print());
    }

    @Test
    public void wrongPhoneIdGetPhoneInfoByIdAndShopNameTest() throws Exception{

        mockMvc.perform(get("/phone/500/Emag"))

                .andExpect(status().isNotFound())
                .andDo(print());

    }


    @Test
    @DisplayName("It get phone with min price and the same brand.")
    public void getPhoneWithMinPriceForBrandTest()  throws Exception {


             mockMvc.perform(get("/phone/Samsung")).andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.*",hasSize(3)))
                .andExpect(jsonPath("$.*").isNotEmpty())
                .andExpect(jsonPath("$.brand").value("Samsung"))
                .andExpect(jsonPath("$.modelPhone").value("s20fe"))
                .andExpect(jsonPath("$.price").value(new BigDecimal(1200)));
    }

    @Test
    public void wrongGetPhoneWithMinPriceForBrandTest()  throws Exception {


        mockMvc.perform(get("/Phone/Samsungg")).
                andExpect(status().is4xxClientError())
                .andDo(print());

    }

    @Test
    public void getPhoneWithMinPriceForBrandAndYearTest()  throws Exception {


        mockMvc.perform(get("/phone/year/Samsung/2020"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.*",hasSize(3)))
                .andExpect(jsonPath("$.*").isNotEmpty())
                .andExpect(jsonPath("$.brand").value("Samsung"))
                .andExpect(jsonPath("$.modelPhone").value("s20fe"))
                .andExpect(jsonPath("$.price").value(new BigDecimal(1200)));
    }

    @Test
    @DisplayName("It should update quantity in the shop")
    @Sql(statements = "insert into availablities (phoneId,shop,price,quantity) values(7,'Zora',1500,0)")
    public void updateQuantityTest() throws Exception{
        mockMvc.perform(patch("/phone/quantity/")
                        .queryParam("quantity","200")
                        .queryParam("stockId","1"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void updateQuantityWishWrongId() throws Exception
    {
        mockMvc.perform(patch("/phone/quantity/")
                        .queryParam("quantity","200")
                        .queryParam("stockId","1O00"))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }


}
