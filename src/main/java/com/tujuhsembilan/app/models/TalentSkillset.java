package com.tujuhsembilan.app.models;


import org.hibernate.annotations.BatchSize;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

@Table(name = "talent_skillset")
public class TalentSkillset {

   @Id
   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "talent_id")
   private Talent talent;

   @Id
   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "skillset_id")
   private Skillset skillset;


   // --> creation
   @Embedded
   private Creation creation;


}
