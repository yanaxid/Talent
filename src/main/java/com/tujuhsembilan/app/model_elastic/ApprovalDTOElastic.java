package com.tujuhsembilan.app.model_elastic;


import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor


@Document(indexName = "approval_idx")
public class ApprovalDTOElastic {

   @Id
   private UUID talentRequestId;

   @Field( type = FieldType.Text, name = "agency_name")
   private String agencyName;

   @Field( name = "request_date")
   private String requestDate;

   @Field( type = FieldType.Text, name = "talent_name")
   private String talentName;

   @Field( type = FieldType.Text, name = "talent_request_status_name")
   private String approvalStatus;

}