package com.tujuhsembilan.app.models;

import java.util.List;
import java.util.UUID;

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


@Table(name = "talent_level")
public class TalentLevel {

   @Id
   @GeneratedValue(strategy = GenerationType.UUID)
   @Column(name = "talent_level_id")
   private UUID talentLevelId;

   @Column(name = "talent_level_name", length = 50)
   @Size(max = 50)
   private String talentLevelName;

   @Column(name = "is_active")
   private Boolean isActive;

   //--> creation
   @Embedded
   private Creation creation;

   //--> relation
   @OneToMany(mappedBy = "talentLevel", fetch = FetchType.LAZY)
   private List<Talent> talents;



}
