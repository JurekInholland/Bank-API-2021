package io.swagger.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.service.IbanServiceImpl;
import org.springframework.validation.annotation.Validated;

import javax.persistence.Entity;
import java.util.Random;

public class Iban{
    @JsonProperty("iban")
    private String iban = null;

    public Iban() {
    }


    public String getIban() {
        return this.iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }
}
