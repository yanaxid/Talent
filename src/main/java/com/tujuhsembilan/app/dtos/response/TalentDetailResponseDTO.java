package com.tujuhsembilan.app.dtos.response;

import java.util.Date;
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
public class TalentDetailResponseDTO {

   private UUID talentId;
   private String talentPhoto;
   private String talentName;
   private String talentStatus;
   private String nip; //employeeNumber
   private Character sex;
   private String dob; //tanggal lahir
   private String talentDescription;
   private String cv;
   private Integer talentExperience;
   private String talentLevel;
   private Integer projectCompleted;
   private Integer totalRequested;
   private List<PositionResponseDTO> position;
   private List<SkillsetResponseDTO> skillSet;
   private String email;
   private String cellphone;
   private String employeeStatus;
   private Boolean talentAvailability;
   private String videoUrl;
   private String talentPhotoUrl;
   private String talentCvUrl;

}
