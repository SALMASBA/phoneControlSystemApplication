package com.example.phonecontrolsystem.service.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhoneDTO {
    private Long idPhone;
    private String model;
    private String color;
    private String imei;
    private Float price;
}