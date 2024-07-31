package com.tujuhsembilan.app.dtos.response;

import java.util.Date;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class ApprovalDTO {

   private UUID talentRequestId;
   private String agencyName;
   private Date requestDate;
   private String talentName;
   private String approvalStatus;
}