package com.tujuhsembilan.app.models;

import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.BatchSize;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
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

@Table(name = "talent_status")
public class TalentStatus {

   @Id
   @GeneratedValue(strategy = GenerationType.UUID)
   @Column(name = "talent_status_id")
   private UUID talentStatusId;

   @Column(name = "talent_status_name", length = 50)
   @Size(max = 50)
   private String talentStatusName;

   @Column(name = "is_active")
   private Boolean isActive;

   //--> creation
   @Embedded
   private Creation creation;

   //--> realation
   @OneToMany(mappedBy = "talentStatus", fetch = FetchType.LAZY)
   private List<Talent> talents;



}
