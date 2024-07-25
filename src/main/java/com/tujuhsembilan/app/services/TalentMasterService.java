package com.tujuhsembilan.app.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.tujuhsembilan.app.dtos.response.EmployeeStatusResponseDTO;
import com.tujuhsembilan.app.dtos.response.PositionResponseDTO;
import com.tujuhsembilan.app.dtos.response.SkillsetResponseDTO;
import com.tujuhsembilan.app.dtos.response.TalentLevelResponsetDTO;
import com.tujuhsembilan.app.models.EmployeeStatus;
import com.tujuhsembilan.app.models.Position;
import com.tujuhsembilan.app.models.Skillset;
import com.tujuhsembilan.app.models.TalentLevel;
import com.tujuhsembilan.app.repository.EmployeeStatusRepository;
import com.tujuhsembilan.app.repository.PositionRepository;
import com.tujuhsembilan.app.repository.SkillsetRepository;
import com.tujuhsembilan.app.repository.TalentLevelRepository;


import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TalentMasterService {

   @Autowired
   private EmployeeStatusRepository employeeStatusRepository;

   @Autowired
   private TalentLevelRepository talentLevelRepository;

   @Autowired
   private PositionRepository positionRepository;

   @Autowired
   private SkillsetRepository skillsetRepository;

   // --> get :: Search Data Master Level Talent
   public ResponseEntity<?> getMasterTalentLevel() {
      List<TalentLevel> talentLevels = talentLevelRepository.findAll();
      List<TalentLevelResponsetDTO> talentLevelResponsetDTOs = new ArrayList<>();

      for (TalentLevel tl : talentLevels) {
         talentLevelResponsetDTOs.add(new TalentLevelResponsetDTO(tl.getTalentLevelId(), tl.getTalentLevelName()));
      }
      return ResponseEntity.ok(talentLevelResponsetDTOs);
   }

   // --> get :: Search Data Master Status Kepegawaian
   public ResponseEntity<?> getMasterEmployeeStatus() {
      List<EmployeeStatus> employeeStatus = employeeStatusRepository.findAll();
      List<EmployeeStatusResponseDTO> employeeStatusResponseDTOs = new ArrayList<>();

      for (EmployeeStatus e : employeeStatus) {
         employeeStatusResponseDTOs
               .add(new EmployeeStatusResponseDTO(e.getEmployeeStatusId(), e.getEmployeeStatusName()));
      }
      return ResponseEntity.ok(employeeStatusResponseDTOs);
   }

   // --> get :: Search Data Master Skill Set
   public ResponseEntity<?> getMasterSkillset() {

      List<Skillset> skillsets = skillsetRepository.findAll();
      List<SkillsetResponseDTO> skillsetResponseDTOs = new ArrayList<>();

      for (Skillset s : skillsets) {
         skillsetResponseDTOs
               .add(new SkillsetResponseDTO(s.getSkillsetId(),s.getSkillsetName()));
      }
      return ResponseEntity.ok(skillsetResponseDTOs);
   }

   // --> get :: Search Data Master Posisi Talent
   public ResponseEntity<?> getMasterTalentPosition() {

      List<Position> positions = positionRepository.findAll();
      List<PositionResponseDTO> positionResponseDTOs = new ArrayList<>();

      for (Position p : positions) {
         positionResponseDTOs
               .add(new PositionResponseDTO(p.getPositionId(), p.getPositionName()));
      }
      return ResponseEntity.ok(positionResponseDTOs);
   }

}
