package com.tujuhsembilan.app.dtos.response;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// @Data
// @NoArgsConstructor
// @AllArgsConstructor
// @Builder

// public class TalentResponseDTO {

//    private UUID talentId;
//    private String talentPhotoUrl;
//    private String talentName;
//    private String talentStatus;
//    private String employeeStatus;
//    private Boolean talentAvailability;
//    private Integer talentExperience;
//    private String talentLevel;
//    private List<PositionResponseDTO> position;
//    private List<SkillsetResponseDTO> skillSet;

// }

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TalentResponseDTO {

   private UUID talentId;
   private String talentPhotoUrl;
   private String talentName;
   private String talentStatus;
   private String employeeStatus;
   private Boolean talentAvailability;
   private Integer talentExperience;
   private String talentLevel;
   private List<PositionResponseDTO> positions;
   private List<SkillsetResponseDTO> skillsets;

   public TalentResponseDTO(UUID talentId, String talentPhotoUrl, String talentName, String talentStatus, String employeeStatus, Boolean talentAvailability, Integer talentExperience, String talentLevel) {
      this.talentId = talentId;
      this.talentPhotoUrl = talentPhotoUrl;
      this.talentName = talentName;
      this.talentStatus = talentStatus;
      this.employeeStatus = employeeStatus;
      this.talentAvailability = talentAvailability;
      this.talentExperience = talentExperience;
      this.talentLevel = talentLevel;
      this.positions = new ArrayList<>();
      this.skillsets = new ArrayList<>();
   }
   
   public void addPosition(PositionResponseDTO position) {
      this.positions.add(position);
   }

   public void addSkillset(SkillsetResponseDTO skillset) {
      this.skillsets.add(skillset);
   }
}
