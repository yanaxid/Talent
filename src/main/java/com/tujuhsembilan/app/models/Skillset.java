package com.tujuhsembilan.app.models;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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

public class Skillset {

   @Id
   @GeneratedValue(strategy = GenerationType.UUID)
   @Column(name = "skillset_id")
   private UUID skillsetId;

   @ManyToOne
   @JoinColumn(name = "skillset_type_id")
   private SkillsetType skillsetType;

   @Column(name = "skillset_name", length = 50)
   @Size(max = 50)
   private String skillsetName;

   @Column(name = "is_active")
   private Boolean isActive;

    // --> creation
   @Embedded
   private Creation creation;

   //--> relation

   @OneToMany(mappedBy = "skillset")
   private List<TalentSkillset> talentSkillsets;

   @OneToMany(mappedBy = "skillset")
   private List<MostFrequentSkillset> mostFrequentSkillsets;



}
