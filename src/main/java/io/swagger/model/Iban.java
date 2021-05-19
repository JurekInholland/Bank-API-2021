package io.swagger.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Random;

public class Iban {
    @JsonProperty("iban")
    private String iban = null;

    public Iban() {

    }

    // TODO: Move this to a IBAN service
    private Integer randNumber() {
        Random rand = new Random();

        return rand.nextInt(10);
    }

    // TODO: Move this to a IBAN service
    private String generateIban() {
        StringBuilder ibanBuilder = new StringBuilder("NL");
        ibanBuilder.append(this.randNumber());
        ibanBuilder.append(this.randNumber());
        ibanBuilder.append("INHO0");
        ibanBuilder.append(this.randNumber());
        ibanBuilder.append(this.randNumber());
        ibanBuilder.append(this.randNumber());
        ibanBuilder.append(this.randNumber());
        ibanBuilder.append(this.randNumber());
        ibanBuilder.append(this.randNumber());
        ibanBuilder.append(this.randNumber());
        ibanBuilder.append(this.randNumber());
        ibanBuilder.append(this.randNumber());

        return ibanBuilder.toString();
    }

    // TODO: Move this to a IBAN service
    public String generateUniqueIban() {
        String iban = this.generateIban();

        while (!this.isUnique(iban)) {
            iban = this.generateIban();
        }

        this.iban = iban;

        return this.iban;
    }

    // TODO: Move this to a IBAN service
    public boolean isUnique(String iban) {
        // TODO: Call account service and check IBAN
        return true;
    }

    public String getIban() {
        return this.iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }
}
