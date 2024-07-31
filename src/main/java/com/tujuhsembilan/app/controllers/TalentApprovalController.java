package com.tujuhsembilan.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tujuhsembilan.app.dtos.request.CustomPageRequest;
import com.tujuhsembilan.app.dtos.request.TalentApprovalFilterDTO;
import com.tujuhsembilan.app.services.TalentApprovalService;
import com.tujuhsembilan.app.services.spesification.TalentApprovalSpesicication;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/master-management")
public class TalentApprovalController {

   @Autowired
   private TalentApprovalService talentApprovalService;


   // --> get :: daftar persetujuan talent
   @GetMapping("/talent-approvals")
   public ResponseEntity<?> getListApprovals(@ModelAttribute TalentApprovalFilterDTO filter, CustomPageRequest customPageRequest) {
      return talentApprovalService.getListApprovals(filter,customPageRequest.getPage("requestDate") );
   }





























   // --> post :: Save Data Talent
   // @PostMapping(path = { "/talent-management/talents" }, consumes = {
   //       MediaType.APPLICATION_JSON_VALUE,
   //       MediaType.MULTIPART_FORM_DATA_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
   // public ResponseEntity<?> saveDataTalent(
   //       @RequestPart("request") TalentRequestDTO request,
   //       @RequestPart(value = "file", required = false) MultipartFile file) {
   //    return talentMasterService.saveDataTalent(request, file);
   // }

   // @PostMapping(path = { "/book-recipes" }, consumes = {
   // MediaType.APPLICATION_JSON_VALUE,
   // MediaType.MULTIPART_FORM_DATA_VALUE }, produces = {
   // MediaType.APPLICATION_JSON_VALUE })
   // public ResponseEntity<MessageResponse> createRecipe(
   // @RequestPart("request") CreateRecipeRequest request,
   // @RequestPart(value = "file", required = false) MultipartFile file) {
   // int userId = request.getUserId();
   // MessageResponse response = recipeService.create(request, file, userId);
   // return ResponseEntity.status(response.getStatusCode()).body(response);
   // }

}
