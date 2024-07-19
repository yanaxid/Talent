package com.tujuhsembilan.app.models;

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


@Table(name ="most_frequent_skillset")
public class MostFrequentSkillset {

   @Id
   @GeneratedValue(strategy = GenerationType.UUID)
   @Column(name = "most_frequent_skillset_id")
   private UUID mostFrequentSkillsetId;

   @ManyToOne
   @JoinColumn(name = "skillset_id")
   private Skillset skillset;

   private Integer counter;

   // --> creation
   @Embedded
   private Creation creation;

}
