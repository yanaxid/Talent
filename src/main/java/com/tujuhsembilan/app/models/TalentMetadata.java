package com.tujuhsembilan.app.models;

import java.util.UUID;

import org.hibernate.annotations.BatchSize;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)

@Table(name = "talent_metadata")
public class TalentMetadata {

   @Id
   @OneToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "talent_id")
   private Talent talent;

   @Column(name = "cv_counter", columnDefinition = "int")
   private Integer cvCounter;

   @Column(name = "profile_counter", columnDefinition = "int")
   private Integer profileCounter;

   @Column(name = "total_project_completed", columnDefinition = "int")
   private Integer totalProjectCompleted;

   //--> creation
   @Embedded
   private Creation creation;



}
