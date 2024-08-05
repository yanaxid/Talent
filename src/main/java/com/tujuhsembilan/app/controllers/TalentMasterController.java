package com.tujuhsembilan.app.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.tujuhsembilan.app.dtos.request.TalentRequestDTO;
import com.tujuhsembilan.app.services.TalentMasterService;

// import com.tujuhsembilan.app.services.TalentMasterService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/master-management")
public class TalentMasterController {

   @Autowired
   private TalentMasterService talentMasterService;

   // --> get :: Search Data Master Level Talent
   @GetMapping("/talent-level-option-lists")
   public ResponseEntity<?> getMasterTalentLevel() {
      return talentMasterService.getMasterTalentLevel();
   }

   // --> get :: Search Data Master Status Kepegawaian
   @GetMapping("/employee-status-option-lists")
   public ResponseEntity<?> getMasterEmployeeStatus() {
      return talentMasterService.getMasterEmployeeStatus();
   }

   // --> get :: Search Data Master Skill Set
   @GetMapping("/skill-set-option-lists")
   public ResponseEntity<?> getMasterSkillset() {
      return talentMasterService.getMasterSkillset();
   }

   // --> get :: Search Data Master Posisi Talent
   @GetMapping("/talent-position-option-lists")
   public ResponseEntity<?> getMasterTalentPosition() {
      return talentMasterService.getMasterTalentPosition();
   }

   // --> post :: save data talent
   @PostMapping(path = { "/talent" }, consumes = {
         MediaType.APPLICATION_JSON_VALUE,
         MediaType.MULTIPART_FORM_DATA_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
   public ResponseEntity<?> saveDataTalent(
         @RequestPart("request") TalentRequestDTO request,
         @RequestPart(value = "photoFile", required = false) MultipartFile photoFile,
         @RequestPart(value = "cvFile", required = false) MultipartFile cvFile) {

      return talentMasterService.createTalent(request, photoFile, cvFile);

   }

   // --> [put] :: edit data talent
   
   @PutMapping(path = { "/talent/{talentId}" }, consumes = {
         MediaType.APPLICATION_JSON_VALUE,
         MediaType.MULTIPART_FORM_DATA_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
   public ResponseEntity<?> updateTalent(
         @PathVariable UUID talentId,
         @RequestPart("request") TalentRequestDTO request,
         @RequestPart(value = "photoFile", required = false) MultipartFile photoFile,
         @RequestPart(value = "cvFile", required = false) MultipartFile cvFile) {

      return talentMasterService.updateTalent(talentId, request, photoFile, cvFile);

   }

}
