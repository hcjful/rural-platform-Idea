package com.rural.platform.repository;

import com.rural.platform.entity.VolunteerActivity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface VolunteerActivityRepository extends JpaRepository<VolunteerActivity, Long> {
    Page<VolunteerActivity> findByStatusAndCategoryContaining(String status, String category, Pageable pageable);
    
    @Query("SELECT v FROM VolunteerActivity v WHERE " +
           "(:status IS NULL OR v.status = :status) AND " +
           "(:category IS NULL OR v.category = :category) AND " +
           "(:search IS NULL OR v.title LIKE %:search% OR v.description LIKE %:search%)")
    Page<VolunteerActivity> searchActivities(
        @Param("status") String status,
        @Param("category") String category,
        @Param("search") String search,
        Pageable pageable
    );
} 