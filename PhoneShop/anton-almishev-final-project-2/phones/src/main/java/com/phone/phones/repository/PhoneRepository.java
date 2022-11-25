package com.phone.phones.repository;

import com.phone.phones.dto.AvailabilityRequest;
import com.phone.phones.dto.PhoneRequest;
import com.phone.phones.models.Availability;
import com.phone.phones.models.Phone;
import com.phone.phones.models.PhoneView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.phone.phones.messages.OutputMessages.*;

@Repository
public class PhoneRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Autowired
    JdbcTemplate jdbcTemplate;


    public PhoneRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public int deletePhoneFromTheStore(int phoneId,String shop){

        String sql ="" +
                "delete from              " +
                "availablities            " +
                "where phoneId =:phoneId  " +
                "and                      " +
                "shop  =:shop          " +
                "and                      " +
                "quantity=0               ";

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
                .addValue("phoneId", phoneId).addValue("shop",shop);

       return   namedParameterJdbcTemplate.update(sql, mapSqlParameterSource);

    }


    public Integer addPhoneInStocks(AvailabilityRequest availabilityRequest){
        String sql = "insert into availablities (phoneId,shop,price,quantity) values(:phoneId,:shop,:price,:quantity)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("phoneId", availabilityRequest.getPhoneId());
        mapSqlParameterSource.addValue("shop",availabilityRequest.getShop());
        mapSqlParameterSource.addValue("price",availabilityRequest.getPrice());
        mapSqlParameterSource.addValue("quantity",availabilityRequest.getQuantity());

          namedParameterJdbcTemplate.update(sql,mapSqlParameterSource,keyHolder, new String[]{"stockId"});

          return  Objects.requireNonNull(keyHolder.getKey()).intValue();


    }

    public Integer addPhoneInPhones(PhoneRequest phone){

        String sql = "" +
                "    insert into phones (brand,modelPhone,dateMaiden) values   " +
                "(:brand,:modelPhone,:dateMaiden)                              ";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("brand",phone.getBrand());
        mapSqlParameterSource.addValue("modelPhone",phone.getModel());
        mapSqlParameterSource.addValue("dateMaiden",phone.getDate());

         namedParameterJdbcTemplate.update(sql,mapSqlParameterSource,keyHolder, new String[]{"phoneId"});
         return Objects.requireNonNull(keyHolder.getKey()).intValue();
    }


    public Optional<String> isPhoneIdExist(int phoneId) {

        String sql ="select phoneId from phones where phoneId=:phoneId";

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
                .addValue("phoneId",phoneId);

        try {
            return Optional
                    .ofNullable(namedParameterJdbcTemplate
                            .queryForObject(sql, mapSqlParameterSource, String.class));
        } catch (EmptyResultDataAccessException ex) {
            return Optional.empty();
        }
    }

    public Optional<String> isStockIdExist(int stockId) {

        String sql ="select stockId from availablities where stockId =:stockId";

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
                .addValue("stockId",stockId);

        try {
            return Optional
                    .ofNullable(namedParameterJdbcTemplate
                            .queryForObject(sql, mapSqlParameterSource, String.class));
        } catch (EmptyResultDataAccessException ex) {
            return Optional.empty();
        }
    }



    public Optional<String> isShopExist(String shop) {

        String sql ="SELECT DISTINCT shop FROM availablities WHERE shop =:shop";

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
                .addValue("shop",shop);

        try {
            return Optional
                    .ofNullable(namedParameterJdbcTemplate
                            .queryForObject(sql, mapSqlParameterSource, String.class));
        } catch (EmptyResultDataAccessException ex) {
            return Optional.empty();
        }
    }





    public PhoneView showPhoneWithMinPriceByBrandAndYear(String brand ,int year){
        String sql = "" +
                "SELECT                                                                            " +
                " p.brand                                                                          " +
                ",p.modelPhone                                                                     " +
                ",a.shop                                                                           " +
                ",a.price                                                                          " +
                "FROM                                                                              " +
                "phones p inner join availablities a on p.phoneId = a.phoneId                      " +
                "where a.price =                                                                   " +
                "(select min(price) from availablities aa INNER JOIN phones p ON aa.phoneId=p.phoneID" +
                " where brand like :brand) " +
                " AND BRAND like :brand                                                            " +
                " AND EXTRACT(YEAR FROM dateMaiden) =:dateMaiden                                   " ;


        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("brand",brand);
        mapSqlParameterSource.addValue("dateMaiden",year);

        return namedParameterJdbcTemplate.queryForObject(sql,mapSqlParameterSource,(rs, rowNum) -> {
            PhoneView view = new PhoneView();
            view.setBrand(rs.getString("brand"));
            view.setModelPhone(rs.getString("modelPhone"));
            view.setPrice(rs.getBigDecimal("price"));
            return view;
        });

    }


    public PhoneView showPhoneWithMinPriceByBrand(String brand){
        String sql = "" +
                "SELECT                                                                            " +
                " p.brand                                                                          " +
                ",p.modelPhone                                                                     " +
                ",a.shop                                                                           " +
                ",a.price                                                                          " +
                "FROM                                                                              " +
                "phones p inner join availablities a on p.phoneId = a.phoneId                      " +
                "where a.price =                                                                   " +
                "( select min(price) from availablities a INNER JOIN phones p ON a.phoneId=p.phoneID" +
                " where  p.brand =:brand )                                                          " ;

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource().addValue("brand",brand);

        return namedParameterJdbcTemplate.queryForObject(sql,mapSqlParameterSource,(rs, rowNum) -> {
            PhoneView view = new PhoneView();
            view.setBrand(rs.getString("brand"));
            view.setModelPhone(rs.getString("modelPhone"));
            view.setPrice(rs.getBigDecimal("price"));
            return view;
        });

    }

    public Availability giveAvByPhoneIdAndShop(int phoneId, String shop){

        String sql = "select stockId,phoneId,shop,quantity,price from availablities where phoneId =:phoneId and shop like :shop";
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("phoneId",phoneId);
        mapSqlParameterSource.addValue("shop",shop);
        try {
            return  namedParameterJdbcTemplate.queryForObject(sql, mapSqlParameterSource, (rs, rowNum) -> {
                Availability view = new Availability();
                view.setStockId(rs.getInt("stockId"));
                view.setPhoneId(rs.getInt("phoneId"));
                view.setShop(rs.getString("shop"));
                view.setPrice(rs.getBigDecimal("price"));
                view.setQuantity(rs.getInt("quantity"));

                return view;
            });
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
        }

    public Optional<String> isBrandExist(String brand) {

        String sql ="" +
                "SELECT DISTINCT  phones.brand FROM availablities           " +
                "inner join phones on phones.phoneid = availablities.phoneid" +
                " WHERE phones.brand =:brand                                  ";

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
                .addValue("brand",brand);

        try {
            return Optional
                    .ofNullable(namedParameterJdbcTemplate
                            .queryForObject(sql, mapSqlParameterSource, String.class));
        } catch (EmptyResultDataAccessException ex) {
            return Optional.empty();
        }
    }

    public  String updateQuantity(int quantity,int stockId){
        String sql = "update availablities set quantity =:quantity  where stockId =:stockId ";
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("quantity",quantity);
        mapSqlParameterSource.addValue("stockId",stockId);

            namedParameterJdbcTemplate.update(sql, mapSqlParameterSource);
            return SUCCESSFUL_UPDATING;
    }


    public boolean isTheSamePhoneExist(String modelPhone){

        String sql =" select modelphone from phones where modelphone like :modelPhone";

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
                .addValue("modelPhone",modelPhone);
        List<Object> models = namedParameterJdbcTemplate.query(sql, mapSqlParameterSource,(rs, rowNum) -> {
            List<String> view = new ArrayList<>();
            view.add(rs.getString("modelPhone"));
            return view;
        });
        return models.size() > 0;
    }

    public Integer getQuantityByPhoneIdAndShop(int phoneId,String shop){
        String sql = "select quantity from availablities where phoneId =:phoneId  and shop  =:shop";
        MapSqlParameterSource mapSqlParameterSource= new MapSqlParameterSource();
        mapSqlParameterSource.addValue("phoneId",phoneId);
        mapSqlParameterSource.addValue("shop",shop);

        return  namedParameterJdbcTemplate.queryForObject(sql,mapSqlParameterSource,Integer.class);

    }

}


