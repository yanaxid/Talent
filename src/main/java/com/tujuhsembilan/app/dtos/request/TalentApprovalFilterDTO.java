package com.tujuhsembilan.app.dtos.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class TalentApprovalFilterDTO {

   
   private String agencyName;
   private String requestDate;
   private String talentRequestStatus;

}
