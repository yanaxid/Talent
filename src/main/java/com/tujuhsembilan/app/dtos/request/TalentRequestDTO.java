package com.tujuhsembilan.app.dtos.request;

import java.util.UUID;

import com.tujuhsembilan.app.dtos.response.PositionResponseDTO2;
import com.tujuhsembilan.app.dtos.response.SkillsetResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TalentRequestDTO {

   private String talentLevel;
   private Integer talentExperience;
   private String talentStatus;
   private String employeeStatus;

}
