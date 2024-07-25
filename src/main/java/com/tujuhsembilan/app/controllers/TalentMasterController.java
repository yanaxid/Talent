package com.tujuhsembilan.app.controllers;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


   //--> post :: Save Data Talent
   // @PostMapping("/talent-management/talents")
   // public ResponseEntity<?> saveDataTalent() {
   //    return talentMasterService.saveDataTalent();
   // }

  

}
