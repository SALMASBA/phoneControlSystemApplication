package com.example.phonecontrolsystem;

import com.example.phonecontrolsystem.dao.entities.Phone;
import com.example.phonecontrolsystem.dao.repositories.PhoneRepository;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PhoneControlSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(PhoneControlSystemApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    // Initialisation de 4 phones dans la DB
    @Bean
    public CommandLineRunner dataLoader(PhoneRepository repo) {
        return args -> {
            repo.save(new Phone(null, "Galaxy S21", "Phantom Gray", "IMEI0000000001", 699.0f));
            repo.save(new Phone(null, "iPhone 12", "Blue", "IMEI0000000002", 799.0f));
            repo.save(new Phone(null, "P30 Pro", "Black", "IMEI0000000003", 499.99f));
            repo.save(new Phone(null, "Pixel 5", "Green", "IMEI0000000004", 599.0f));
        };
    }
}
