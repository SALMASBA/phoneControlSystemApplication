package com.example.phonecontrolsystem.service;

import com.example.phonecontrolsystem.dao.entities.Phone;
import com.example.phonecontrolsystem.dao.repositories.PhoneRepository;
import com.example.phonecontrolsystem.service.dtos.PhoneDTO;
import com.example.phonecontrolsystem.service.mappers.PhoneMapper;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class PhoneManagerAction implements PhoneManager {

    private final PhoneRepository repo;
    private final PhoneMapper mapper;
    // We'll use these sinks in controller via injection or static access — but better to let controller own them.
    public PhoneManagerAction(PhoneRepository repo, PhoneMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    @Override
    public PhoneDTO savePhone(PhoneDTO phoneDTO) throws IllegalArgumentException {
        // Vérifier l'IMEI n'existe pas
        if (phoneDTO.getImei() == null || phoneDTO.getImei().isBlank()) {
            throw new IllegalArgumentException("IMEI obligatoire");
        }
        if (repo.findByImei(phoneDTO.getImei()).isPresent()) {
            throw new IllegalArgumentException("IMEI existe déjà");
        }
        Phone p = mapper.toEntity(phoneDTO);
        p.setIdPhone(null); // pour être sûr
        Phone saved = repo.save(p);
        return mapper.toDto(saved);
    }

    @Override
    public PhoneDTO updatePhone(Long id, PhoneDTO phoneDTO) throws IllegalArgumentException {
        Phone existing = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Phone introuvable"));
        // si IMEI change, vérifier unicité
        if (phoneDTO.getImei() != null && !phoneDTO.getImei().equals(existing.getImei())) {
            repo.findByImei(phoneDTO.getImei()).ifPresent(p -> {
                throw new IllegalArgumentException("IMEI existe déjà pour un autre téléphone");
            });
            existing.setImei(phoneDTO.getImei());
        }
        if (phoneDTO.getModel() != null) existing.setModel(phoneDTO.getModel());
        if (phoneDTO.getColor() != null) existing.setColor(phoneDTO.getColor());
        if (phoneDTO.getPrice() != null) existing.setPrice(phoneDTO.getPrice());
        Phone saved = repo.save(existing);
        return mapper.toDto(saved);
    }

    @Override
    public PhoneDTO updatePhonePrice(Long id, Float price) throws IllegalArgumentException {
        Phone existing = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Phone introuvable"));
        existing.setPrice(price);
        Phone saved = repo.save(existing);
        return mapper.toDto(saved);
    }

    @Override
    public PhoneDTO deletePhone(Long id) throws IllegalArgumentException {
        Phone existing = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Phone introuvable"));
        repo.delete(existing);
        return mapper.toDto(existing);
    }

    @Override
    public Optional<PhoneDTO> getPhoneById(Long id) {
        return repo.findById(id).map(mapper::toDto);
    }

    @Override
    public List<PhoneDTO> getPhones() {
        return repo.findAll().stream().map(mapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<PhoneDTO> getPhoneByModel(String model) {
        return repo.findByModelIgnoreCase(model).stream().map(mapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<PhoneDTO> getPhoneByModelAndPrice(String model, Float price) {
        return repo.findByModelIgnoreCaseAndPriceLessThanEqual(model, price).stream()
                .map(mapper::toDto).collect(Collectors.toList());
    }

    @Override
    public Optional<PhoneDTO> getPhoneByIMEI(String imei) {
        return repo.findByImei(imei).map(mapper::toDto);
    }

    @Override
    public List<PhoneDTO> getPhonesByPriceRange(Float min, Float max) {
        return repo.findByPriceBetween(min, max).stream().map(mapper::toDto).collect(Collectors.toList());
    }
}