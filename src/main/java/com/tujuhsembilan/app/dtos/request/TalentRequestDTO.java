package com.tujuhsembilan.app.dtos.request;


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
