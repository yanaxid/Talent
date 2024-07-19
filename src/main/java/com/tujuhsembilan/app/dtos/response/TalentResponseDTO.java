package com.tujuhsembilan.app.dtos.response;

import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TalentResponseDTO {

   private UUID talentId;
   private String talentPhotoUrl;
   private String talentName;
   private String talentStatus;
   private String employeeStatus;
   private Boolean talentAvailability;
   private Integer talentExperience;
   private String talentLevel;
   private List<PositionResponseDTO2> position;
   private List<SkillsetResponseDTO> skillSet;

}
