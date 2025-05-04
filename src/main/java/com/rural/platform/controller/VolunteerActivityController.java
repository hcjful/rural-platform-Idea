package com.rural.platform.controller;

import com.rural.platform.entity.VolunteerActivity;
import com.rural.platform.entity.VolunteerSignup;
import com.rural.platform.service.VolunteerActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/volunteer-activities")
@RequiredArgsConstructor
public class VolunteerActivityController {
    private final VolunteerActivityService activityService;

    @GetMapping
    public ResponseEntity<Page<VolunteerActivity>> searchActivities(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(
            activityService.searchActivities(status, category, search, PageRequest.of(page, size))
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<VolunteerActivity> getActivity(@PathVariable Long id) {
        return ResponseEntity.ok(activityService.getActivityById(id));
    }

    @PostMapping
    public ResponseEntity<VolunteerActivity> createActivity(@RequestBody VolunteerActivity activity) {
        return ResponseEntity.ok(activityService.createActivity(activity));
    }

    @PostMapping("/{id}/signup")
    public ResponseEntity<VolunteerSignup> signup(
            @PathVariable Long id,
            @RequestBody VolunteerSignup signup) {
        return ResponseEntity.ok(activityService.signup(id, signup));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Void> updateStatus(
            @PathVariable Long id,
            @RequestParam String status) {
        activityService.updateActivityStatus(id, status);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/review")
    public ResponseEntity<Void> addReview(
            @PathVariable Long id,
            @RequestParam String review,
            @RequestParam String reviewImages) {
        activityService.addActivityReview(id, review, reviewImages);
        return ResponseEntity.ok().build();
    }
} 