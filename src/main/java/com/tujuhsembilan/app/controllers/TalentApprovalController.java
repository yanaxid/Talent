package com.tujuhsembilan.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tujuhsembilan.app.dtos.request.ApprovedRequestDTO;
import com.tujuhsembilan.app.dtos.request.CustomPageRequest;
import com.tujuhsembilan.app.dtos.request.TalentApprovalFilterDTO;
import com.tujuhsembilan.app.model_elastic.ApprovalDTOElastic;
import com.tujuhsembilan.app.services.TalentApprovalService;


import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/master-management")
@Validated
public class TalentApprovalController {

   @Autowired
   private TalentApprovalService talentApprovalService;

   // --> get :: daftar persetujuan talent
   @GetMapping(path = "/talent-approvals", produces = MediaType.APPLICATION_JSON_VALUE)
   public ResponseEntity<?> getListApprovals(@ModelAttribute TalentApprovalFilterDTO filter,
         CustomPageRequest customPageRequest) {
      return talentApprovalService.getListApprovals(filter, customPageRequest.getPage("requestDate,desc"));
   }


   // --> get :: daftar persetujuan talent
   @GetMapping(path = "/talent-approvals/search", produces = MediaType.APPLICATION_JSON_VALUE)
   public Page<ApprovalDTOElastic> searchApproval(@ModelAttribute TalentApprovalFilterDTO filter,
         CustomPageRequest customPageRequest) {
      return talentApprovalService.searchListApprovals(filter, customPageRequest.getPage("request_date,desc"));
   }


   // --> put :: edit persetujuan talent
   @PutMapping(path = "/talent-approvals", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
   public ResponseEntity<?> putListApprovals(@Valid @RequestBody ApprovedRequestDTO request) {
      return talentApprovalService.putApproval(request);
   }




   // @GetMapping("/talents/search")
   // public Page<TalentElastic> searchTalents(@Valid TalentFilterDTO filter, CustomPageRequest customPageRequest) {
   //    return talentService.searchTalents(filter, customPageRequest.getPage("experience,desc;talent_level_name.keyword,asc;talent_name.keyword,asc"));
   // }


}
