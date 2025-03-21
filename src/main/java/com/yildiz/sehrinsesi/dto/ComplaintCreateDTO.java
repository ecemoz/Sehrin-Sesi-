package com.yildiz.sehrinsesi.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ComplaintCreateDTO {

    private String title;
    private String description;
    private double latitude;
    private double longitude;
}
