package com.tujuhsembilan.app.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tujuhsembilan.app.models.SampleModel;

public interface SampleRepository extends JpaRepository<SampleModel, UUID> {
}
