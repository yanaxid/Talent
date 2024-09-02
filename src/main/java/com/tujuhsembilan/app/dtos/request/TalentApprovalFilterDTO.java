package com.tujuhsembilan.app.dtos.request;


import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class TalentApprovalFilterDTO {

   private String keyword;
   private String searchBy;
   private String agencyName;
   private String talentName;
   private Date requestDate;
   private String talentRequestStatus;

}
