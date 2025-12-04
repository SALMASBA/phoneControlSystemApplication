package com.example.phonecontrolsystem.dao.repositories;

import com.example.phonecontrolsystem.dao.entities.Phone;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PhoneRepository extends JpaRepository<Phone, Long> {
    List<Phone> findByModelIgnoreCase(String model);
    List<Phone> findByModelIgnoreCaseAndPriceLessThanEqual(String model, Float price);
    Optional<Phone> findByImei(String imei);
    List<Phone> findByPriceBetween(Float min, Float max);
}