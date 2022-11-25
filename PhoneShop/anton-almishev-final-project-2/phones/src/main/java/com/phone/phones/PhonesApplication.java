package com.phone.phones;

import com.phone.phones.dto.AvailabilityRequest;
import com.phone.phones.models.Phone;
import com.phone.phones.repository.PhoneRepository;
import com.phone.phones.services.PhoneService;
import com.phone.phones.vallidations.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Year;
import java.util.Date;

@SpringBootApplication
public class PhonesApplication implements CommandLineRunner {
	@Autowired
	private PhoneRepository phoneRepository;
	@Autowired
	PhoneService phoneService;
	@Autowired
	Validation validation;



	public static void main(String[] args) {
		SpringApplication.run(PhonesApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

	}
}
