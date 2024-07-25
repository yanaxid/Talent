package com.tujuhsembilan.app.dtos.request;


import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.tujuhsembilan.app.dtos.response.PositionResponseDTO;
import com.tujuhsembilan.app.dtos.response.SkillsetResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class TalentMasterRequestDTO {


   private String talentPhoto;
   private String talentName;
   private String talentStatusId;
   private String nip; //employeeNumber
   private Character sex;
   private Date dob; //tanggal lahir
   private String talentDescription;
   private String cv;
   private Integer talentExperience;
   private UUID talentLevelId;
   private Integer projectCompleted;
   private Integer totalRequested;
   private List<PositionResponseDTO> position;
   private List<SkillsetResponseDTO> skillSet;
   private String email;
   private String cellphone;
   private String employeeStatus;
   private Boolean talentAvailability;
   private String videoUrl;

}
