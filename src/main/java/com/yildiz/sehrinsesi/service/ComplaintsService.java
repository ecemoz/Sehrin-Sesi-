package com.yildiz.sehrinsesi.service;

import com.yildiz.sehrinsesi.dto.ComplaintCreateDTO;
import com.yildiz.sehrinsesi.dto.ComplaintResponseDTO;
import com.yildiz.sehrinsesi.mapper.ComplaintsMapper;
import com.yildiz.sehrinsesi.model.Complaints;
import com.yildiz.sehrinsesi.model.User;
import com.yildiz.sehrinsesi.repository.ComplaintsRepository;
import com.yildiz.sehrinsesi.repository.UserRepository;
import com.yildiz.sehrinsesi.utils.ComplaintsStatus;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ComplaintsService {

    private final ComplaintsRepository complaintsRepository;
    private final UserRepository userRepository;
    private final ComplaintsMapper complaintsMapper;

    public ComplaintsService(ComplaintsRepository complaintsRepository, UserRepository userRepository, ComplaintsMapper complaintsMapper) {
        this.complaintsRepository = complaintsRepository;
        this.userRepository = userRepository;
        this.complaintsMapper = complaintsMapper;
    }

    public ComplaintResponseDTO createComplaint(Long userId, ComplaintCreateDTO complaintCreateDTO ) {

        User user = userRepository.findById(userId)
                .orElseThrow(()-> new RuntimeException(" User not found with ID: " + userId));

        Complaints complaints = complaintsMapper.fromCreateDto(complaintCreateDTO, user);
        Complaints saved = complaintsRepository.save(complaints);
        return complaintsMapper.toResponseDto(saved);
    }

    public List<ComplaintResponseDTO> getAllComplaints() {
        List<Complaints> all = complaintsRepository.findAll();
        return complaintsMapper.toResponseDtoList(all);
    }

    public ComplaintResponseDTO getComplaintById(Long complaintId) {
        Complaints complaint = complaintsRepository.findById(complaintId)
                .orElseThrow(() -> new RuntimeException("Complaint not found with ID: " + complaintId));
        return complaintsMapper.toResponseDto(complaint);
    }

    public ComplaintResponseDTO updateComplaintStatus(Long complaintId, String newStatus) {
        Complaints complaint = complaintsRepository.findById(complaintId)
                .orElseThrow(() -> new RuntimeException("Complaint not found with ID: " + complaintId));

        try {
            complaint.setStatus(ComplaintsStatus.valueOf(newStatus.toUpperCase()));
        } catch (IllegalArgumentException e) {

            throw new RuntimeException("Invalid complaint status: " + newStatus, e);
        }

        Complaints updated = complaintsRepository.save(complaint);
        return complaintsMapper.toResponseDto(updated);
    }

    public List<ComplaintResponseDTO> getComplaintsByUser(Long userId) {
        List<Complaints> userComplaints = complaintsRepository.findByUserId(userId);
        return complaintsMapper.toResponseDtoList(userComplaints);
    }

    public void deleteComplaint(Long complaintId) {
        if (!complaintsRepository.existsById(complaintId)) {
            throw new RuntimeException("Complaint not found with ID: " + complaintId);
        }
        complaintsRepository.deleteById(complaintId);
    }

    public List<ComplaintResponseDTO> getComplaintsByStatus(ComplaintsStatus status) {
        List<Complaints> complaints = complaintsRepository.findByStatus(status);
        return complaintsMapper.toResponseDtoList(complaints);
    }
}