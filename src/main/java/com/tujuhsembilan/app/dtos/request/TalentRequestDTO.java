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

public class TalentRequestDTO {

   private String talentPhoto;
   private String talentName;
   private UUID talentStatus;
   private String nip; // employeeNumber
   private Character sex;
   private Date dob; // tanggal lahir
   private String talentDescription;
   private String cv;
   private Integer talentExperience;
   private UUID talentLevel;
   private Integer projectCompleted;
   private Integer totalRequested;
   private List<PositionResponseDTO> positions;
   private List<SkillsetResponseDTO> skillsets;
   private String email;
   private String cellphone;
   private UUID employeeStatus;
   private Boolean talentAvailability;
   private String videoUrl;

}
