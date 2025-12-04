package com.example.phonecontrolsystem.web;

import com.example.phonecontrolsystem.service.PhoneManager;
import com.example.phonecontrolsystem.service.dtos.PhoneDTO;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SubscriptionMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.List;
import java.util.Optional;

@Controller
public class PhoneGraphQLController {

    private final PhoneManager phoneManager;

    // Sinks pour subscriptions
    private final Sinks.Many<PhoneDTO> phoneAddedSink = Sinks.many().multicast().onBackpressureBuffer();
    private final Sinks.Many<PhoneDTO> phoneUpdatedSink = Sinks.many().multicast().onBackpressureBuffer();
    private final Sinks.Many<String> phoneDeletedSink = Sinks.many().multicast().onBackpressureBuffer();

    public PhoneGraphQLController(PhoneManager phoneManager) {
        this.phoneManager = phoneManager;
    }

    // ----- Queries -----
    @QueryMapping
    public List<PhoneDTO> getPhones() {
        return phoneManager.getPhones();
    }

    @QueryMapping
    public Optional<PhoneDTO> getPhoneById(@Argument Long id) {
        return phoneManager.getPhoneById(id);
    }

    @QueryMapping
    public List<PhoneDTO> getPhoneByModel(@Argument String model) {
        return phoneManager.getPhoneByModel(model);
    }

    @QueryMapping
    public List<PhoneDTO> getPhoneByModelAndPrice(@Argument String model, @Argument Float price) {
        return phoneManager.getPhoneByModelAndPrice(model, price);
    }

    @QueryMapping
    public Optional<PhoneDTO> getPhoneByIMEI(@Argument String imei) {
        return phoneManager.getPhoneByIMEI(imei);
    }

    @QueryMapping
    public List<PhoneDTO> getPhonesByPriceRange(@Argument Float min, @Argument Float max) {
        return phoneManager.getPhonesByPriceRange(min, max);
    }

    // ----- Mutations -----
    @MutationMapping
    public PhoneDTO savePhone(@Argument PhoneInput phone) {
        PhoneDTO dto = new PhoneDTO(null, phone.getModel(), phone.getColor(), phone.getImei(), phone.getPrice());
        PhoneDTO saved = phoneManager.savePhone(dto);
        phoneAddedSink.tryEmitNext(saved);
        return saved;
    }

    @MutationMapping
    public PhoneDTO updatePhone(@Argument Long id, @Argument PhoneInput phone) {
        PhoneDTO dto = new PhoneDTO(null, phone.getModel(), phone.getColor(), phone.getImei(), phone.getPrice());
        PhoneDTO updated = phoneManager.updatePhone(id, dto);
        phoneUpdatedSink.tryEmitNext(updated);
        return updated;
    }

    @MutationMapping
    public PhoneDTO updatePhonePrice(@Argument Long id, @Argument Float price) {
        PhoneDTO updated = phoneManager.updatePhonePrice(id, price);
        phoneUpdatedSink.tryEmitNext(updated);
        return updated;
    }

    @MutationMapping
    public PhoneDTO deletePhone(@Argument Long id) {
        PhoneDTO deleted = phoneManager.deletePhone(id);
        phoneDeletedSink.tryEmitNext(String.valueOf(id));
        return deleted;
    }

    // ----- Subscriptions -----
    @SubscriptionMapping
    public Flux<PhoneDTO> phoneAdded() {
        return phoneAddedSink.asFlux();
    }

    @SubscriptionMapping
    public Flux<PhoneDTO> phoneUpdated() {
        return phoneUpdatedSink.asFlux();
    }

    @SubscriptionMapping
    public Flux<Long> phoneDeleted() {
        // convert String back to Long
        return phoneDeletedSink.asFlux().map(Long::valueOf);
    }

    // Nested static class for GraphQL input mapping
    public static class PhoneInput {
        private String model;
        private String color;
        private String imei;
        private Float price;
        // getters & setters
        public String getModel() { return model; }
        public void setModel(String model) { this.model = model; }
        public String getColor() { return color; }
        public void setColor(String color) { this.color = color; }
        public String getImei() { return imei; }
        public void setImei(String imei) { this.imei = imei; }
        public Float getPrice() { return price; }
        public void setPrice(Float price) { this.price = price; }
    }
}