package com.yildiz.sehrinsesi.mapper;

import com.yildiz.sehrinsesi.dto.ComplaintCreateDTO;
import com.yildiz.sehrinsesi.dto.ComplaintResponseDTO;
import com.yildiz.sehrinsesi.model.Complaints;
import com.yildiz.sehrinsesi.model.User;
import com.yildiz.sehrinsesi.utils.ComplaintsStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ComplaintsMapper {

    public Complaints fromCreateDto(ComplaintCreateDTO dto, User user) {
        if (dto == null || user == null) {
            return null;
        }
        Complaints complaint = new Complaints();
        complaint.setTitle(dto.getTitle());
        complaint.setDescription(dto.getDescription());
        complaint.setLatitude(dto.getLatitude());
        complaint.setLongitude(dto.getLongitude());
        complaint.setCreatedAt(LocalDateTime.now());
        complaint.setStatus(ComplaintsStatus.OPEN);
        complaint.setUser(user);
        return complaint;
    }

    public ComplaintResponseDTO toResponseDto(Complaints complaint) {
        if (complaint == null) {
            return null;
        }
        return ComplaintResponseDTO.builder()
                .id(complaint.getId())
                .title(complaint.getTitle())
                .description(complaint.getDescription())
                .latitude(complaint.getLatitude())
                .longitude(complaint.getLongitude())
                .createdAt(complaint.getCreatedAt())
                .status(complaint.getStatus())
                .username(complaint.getUser().getUsername())
                .build();
    }

    public List<ComplaintResponseDTO> toResponseDtoList(List<Complaints> complaints) {
        if (complaints == null || complaints.isEmpty()) {
            return Collections.emptyList();
        }
        return complaints.stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }
}
