package com.example.phonecontrolsystem.dao.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "phones")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Phone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPhone;     // id_Phone demandé (on utilise camelCase)

    private String model;
    private String color;
    @Column(unique = true)
    private String imei;
    private Float price;
}