package com.practise.assignment.dao.entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false, updatable = false)
    private String createdBy;

    @Column(nullable = false, updatable = false)
    private LocalDateTime creationTime;

    @Column(nullable = false, updatable = false)
    private String updatedBy;

    @Column(nullable = false)
    private LocalDateTime updationTime;

    public long getId() {
        return id;
    }

    public BaseEntity setId(long id) {
        this.id = id;
        return this;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public BaseEntity setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public BaseEntity setCreationTime(LocalDateTime creationTime) {
        this.creationTime = creationTime;
        return this;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public BaseEntity setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public LocalDateTime getUpdationTime() {
        return updationTime;
    }

    public BaseEntity setUpdationTime(LocalDateTime updationTime) {
        this.updationTime = updationTime;
        return this;
    }
}
