package com.yildiz.sehrinsesi.controller;

import com.yildiz.sehrinsesi.dto.ComplaintCreateDTO;
import com.yildiz.sehrinsesi.dto.ComplaintResponseDTO;
import com.yildiz.sehrinsesi.service.ComplaintsService;
import com.yildiz.sehrinsesi.utils.ComplaintsStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/complaints")
public class ComplaintsController {

    private final ComplaintsService complaintsService;

    public ComplaintsController(ComplaintsService complaintsService) {
        this.complaintsService = complaintsService;
    }

    @PostMapping
    public ResponseEntity<ComplaintResponseDTO> createComplaint(@RequestParam Long userId,
                                                                @RequestBody ComplaintCreateDTO dto) {
        ComplaintResponseDTO response = complaintsService.createComplaint(userId, dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<ComplaintResponseDTO>> getAllComplaints() {
        return ResponseEntity.ok(complaintsService.getAllComplaints());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ComplaintResponseDTO> getComplaintById(@PathVariable Long id) {
        return ResponseEntity.ok(complaintsService.getComplaintById(id));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ComplaintResponseDTO> updateStatus(@PathVariable Long id,
                                                             @RequestParam String status) {
        return ResponseEntity.ok(complaintsService.updateComplaintStatus(id, status));
    }

    @GetMapping("/by-user")
    public ResponseEntity<List<ComplaintResponseDTO>> getComplaintsByUser(@RequestParam Long userId) {
        return ResponseEntity.ok(complaintsService.getComplaintsByUser(userId));
    }

    @GetMapping("/by-status")
    public ResponseEntity<List<ComplaintResponseDTO>> getComplaintsByStatus(@RequestParam ComplaintsStatus status) {
        return ResponseEntity.ok(complaintsService.getComplaintsByStatus(status));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComplaint(@PathVariable Long id) {
        complaintsService.deleteComplaint(id);
        return ResponseEntity.noContent().build();
    }

}