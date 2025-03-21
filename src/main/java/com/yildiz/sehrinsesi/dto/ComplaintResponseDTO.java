package com.yildiz.sehrinsesi.dto;

import com.yildiz.sehrinsesi.utils.ComplaintsStatus;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class ComplaintResponseDTO {

    private Long id;
    private String title;
    private String description;
    private double latitude;
    private double longitude;
    private LocalDateTime createdAt;
    private ComplaintsStatus status;
    private String username;
}
