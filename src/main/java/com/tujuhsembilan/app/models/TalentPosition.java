package com.tujuhsembilan.app.models;


import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
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

@Table(name = "talent_position")
public class TalentPosition {

   @EmbeddedId
   private TalentPositionId id;

   @ManyToOne(fetch = FetchType.LAZY)
   @MapsId("talentId")
   @JoinColumn(name = "talent_id")
   private Talent talent;

   @ManyToOne(fetch = FetchType.LAZY)
   @MapsId("positionId")
   @JoinColumn(name = "position_id")
   private Position position;

   // --> creation
   private Creation creation;

}
