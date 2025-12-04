package com.example.phonecontrolsystem.service.mappers;

import com.example.phonecontrolsystem.dao.entities.Phone;
import com.example.phonecontrolsystem.service.dtos.PhoneDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class PhoneMapper {

    private final ModelMapper modelMapper;

    public PhoneMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public PhoneDTO toDto(Phone phone) {
        return modelMapper.map(phone, PhoneDTO.class);
    }

    public Phone toEntity(PhoneDTO dto) {
        return modelMapper.map(dto, Phone.class);
    }
}
