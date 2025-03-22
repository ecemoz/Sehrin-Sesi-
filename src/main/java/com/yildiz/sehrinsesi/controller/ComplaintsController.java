package com.yildiz.sehrinsesi.controller;

import com.yildiz.sehrinsesi.dto.ComplaintCreateDTO;
import com.yildiz.sehrinsesi.dto.ComplaintResponseDTO;
import com.yildiz.sehrinsesi.security.AuthSecurity;
import com.yildiz.sehrinsesi.service.ComplaintsService;
import com.yildiz.sehrinsesi.utils.ComplaintsStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/complaints")
public class ComplaintsController {

    private final ComplaintsService complaintsService;
    private final AuthSecurity authSecurity;

    public ComplaintsController(ComplaintsService complaintsService, AuthSecurity authSecurity) {
        this.complaintsService = complaintsService;
        this.authSecurity = authSecurity;
    }

    // Create a new complaint (accessible by any authenticated user)
    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ComplaintResponseDTO> createComplaint(@RequestBody ComplaintCreateDTO dto) {
        Long userId = authSecurity.getUserId();
        ComplaintResponseDTO response = complaintsService.createComplaint(userId, dto);
        return ResponseEntity.ok(response);
    }

    // Retrieve all complaints (accessible by ADMIN and HANDLER roles)
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'HANDLER')")
    public ResponseEntity<List<ComplaintResponseDTO>> getAllComplaints() {
        return ResponseEntity.ok(complaintsService.getAllComplaints());
    }

    // Retrieve a complaint by its ID (accessible by ADMIN and HANDLER roles)
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'HANDLER')")
    public ResponseEntity<ComplaintResponseDTO> getComplaintById(@PathVariable Long id) {
        return ResponseEntity.ok(complaintsService.getComplaintById(id));
    }

    // Update the status of a complaint (accessible by ADMIN and HANDLER roles)
    @PatchMapping("/{id}/status")
    @PreAuthorize("hasAnyRole('ADMIN', 'HANDLER')")
    public ResponseEntity<ComplaintResponseDTO> updateStatus(@PathVariable Long id, @RequestParam String status) {
        return ResponseEntity.ok(complaintsService.updateComplaintStatus(id, status));
    }

    // Retrieve complaints by user ID (accessible by the user themselves or ADMIN role)
    @GetMapping("/by-user/{userId}")
    @PreAuthorize("@authSecurity.isSelf(#userId) or hasRole('ADMIN')")
    public ResponseEntity<List<ComplaintResponseDTO>> getComplaintsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(complaintsService.getComplaintsByUser(userId));
    }

    // Retrieve complaints by status (accessible by ADMIN and HANDLER roles)
    @GetMapping("/by-status")
    @PreAuthorize("hasAnyRole('ADMIN', 'HANDLER')")
    public ResponseEntity<List<ComplaintResponseDTO>> getComplaintsByStatus(@RequestParam ComplaintsStatus status) {
        return ResponseEntity.ok(complaintsService.getComplaintsByStatus(status));
    }

    // Delete a complaint (accessible by the user themselves, ADMIN, or HANDLER roles)
    @DeleteMapping("/{id}")
    @PreAuthorize("@authSecurity.isComplaintOwner(#id) or hasAnyRole('ADMIN', 'HANDLER')")
    public ResponseEntity<Void> deleteComplaint(@PathVariable Long id) {
        complaintsService.deleteComplaint(id);
        return ResponseEntity.noContent().build();
    }
}