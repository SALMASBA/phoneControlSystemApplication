package com.example.phonecontrolsystem.service;

import com.example.phonecontrolsystem.service.dtos.PhoneDTO;

import java.util.List;
import java.util.Optional;

public interface PhoneManager {
    PhoneDTO savePhone(PhoneDTO phone) throws IllegalArgumentException;
    PhoneDTO updatePhone(Long id, PhoneDTO phone) throws IllegalArgumentException;
    PhoneDTO updatePhonePrice(Long id, Float price) throws IllegalArgumentException;
    PhoneDTO deletePhone(Long id) throws IllegalArgumentException;
    Optional<PhoneDTO> getPhoneById(Long id);
    List<PhoneDTO> getPhones();
    List<PhoneDTO> getPhoneByModel(String model);
    List<PhoneDTO> getPhoneByModelAndPrice(String model, Float price);
    Optional<PhoneDTO> getPhoneByIMEI(String imei);
    List<PhoneDTO> getPhonesByPriceRange(Float min, Float max);
}