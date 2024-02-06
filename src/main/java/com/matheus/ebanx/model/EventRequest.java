package com.matheus.ebanx.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EventRequest {

    private String type;
    private Integer destination;
    private Integer amount;
    private Integer origin;
}
