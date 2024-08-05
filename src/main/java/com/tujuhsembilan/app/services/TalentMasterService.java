package com.tujuhsembilan.app.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.tujuhsembilan.app.dtos.request.TalentRequestDTO;
import com.tujuhsembilan.app.dtos.response.EmployeeStatusResponseDTO;
import com.tujuhsembilan.app.dtos.response.PositionResponseDTO;
import com.tujuhsembilan.app.dtos.response.SkillsetResponseDTO;
import com.tujuhsembilan.app.dtos.response.TalentLevelResponsetDTO;
import com.tujuhsembilan.app.models.Creation;
import com.tujuhsembilan.app.models.EmployeeStatus;
import com.tujuhsembilan.app.models.Position;
import com.tujuhsembilan.app.models.Skillset;
import com.tujuhsembilan.app.models.Talent;
import com.tujuhsembilan.app.models.TalentLevel;
import com.tujuhsembilan.app.models.TalentMetadata;
import com.tujuhsembilan.app.models.TalentPosition;
import com.tujuhsembilan.app.models.TalentPositionId;
import com.tujuhsembilan.app.models.TalentSkillset;
import com.tujuhsembilan.app.models.TalentSkillsetId;
import com.tujuhsembilan.app.models.TalentStatus;
import com.tujuhsembilan.app.repository.EmployeeStatusRepository;
import com.tujuhsembilan.app.repository.PositionRepository;
import com.tujuhsembilan.app.repository.SkillsetRepository;
import com.tujuhsembilan.app.repository.TalentLevelRepository;
import com.tujuhsembilan.app.repository.TalentPositionRepository;
import com.tujuhsembilan.app.repository.TalentRepository;
import com.tujuhsembilan.app.repository.TalentSkillsetRepository;
import com.tujuhsembilan.app.repository.TalentStatusRepository;

import jakarta.transaction.Transactional;
import lib.minio.MinioService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
@Service
public class TalentMasterService {

   @Autowired
   private TalentRepository talentRepository;

   @Autowired
   private EmployeeStatusRepository employeeStatusRepository;

   @Autowired
   private TalentLevelRepository talentLevelRepository;

   @Autowired
   private PositionRepository positionRepository;

   @Autowired
   private SkillsetRepository skillsetRepository;

   @Autowired
   private TalentPositionRepository talentPositionRepository;

   @Autowired
   private TalentSkillsetRepository talentSkillsetRepository;

   @Autowired
   private TalentStatusRepository talentStatusRepository;

   @Autowired
   private MinioService minioService;

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
               .add(new SkillsetResponseDTO(s.getSkillsetId(), s.getSkillsetName()));
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

   // --> post :: create talent
   @Transactional
   public ResponseEntity<?> createTalent(TalentRequestDTO request, MultipartFile fotoFile, MultipartFile cvFile) {

      try {
         // --> create new talent
         Talent talent = new Talent();

         log.info("START >>>>");

         // --> validate name
         if (request.getTalentName() == null || request.getTalentName().isEmpty()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Nama tidak boleh kosong");
         }

         // --> fetch and validate talentStatus
         Optional<TalentStatus> talentStatusOPT = talentStatusRepository.findById(request.getTalentStatus());
         if (talentStatusOPT.isEmpty()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Invalid TalentStatus ID");
         }

         // --> fetch and validate employeeStatus
         Optional<EmployeeStatus> employeeStatusOPT = employeeStatusRepository.findById(request.getEmployeeStatus());
         if (employeeStatusOPT.isEmpty()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Invalid EmployeeStatus ID");
         }

         // --> fetch and validate talentLevel
         Optional<TalentLevel> talentLevelOPT = talentLevelRepository.findById(request.getTalentLevel());
         if (talentLevelOPT.isEmpty()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Invalid TalentLevel ID");
         }

         // --> upload image
         if (fotoFile != null && !fotoFile.isEmpty()) {
            // --> validate image extension :: jpg, jpeg, png
            String[] allowedFotoFile = { ".jpg", ".jpeg", ".png" };
            int count = 0;
            for (String x : allowedFotoFile) {
               if (minioService.getFileExtension(fotoFile.getOriginalFilename()).toLowerCase().equals(x)) {
                  count += 1;
               }
            }
            if (count == 0) {
               return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                     .body("File IMAGE extension TIDAK DIDUKUNG");
            }

            // --> generate file name
            String filename = minioService.generatedFilename(request.getTalentName(), request.getTalentExperience(),
                  talentLevelOPT.get().getTalentLevelName(), fotoFile);

            minioService.uploadFile(filename, fotoFile);
            String photoUrl = minioService.getPublicLink(filename);

            talent.setTalentPhotoUrl(photoUrl);
            talent.setTalentPhotoFilename(filename);
         }

         // --> upload doc
         if (cvFile != null && !cvFile.isEmpty()) {
            // --> validate doc extension :: pdf, docx
            String[] allowedDocFile = { ".pdf", ".docx" };
            int count = 0;
            for (String x : allowedDocFile) {
               if (minioService.getFileExtension(cvFile.getOriginalFilename()).toLowerCase().equals(x)) {
                  count += 1;
               }
            }
            if (count == 0) {
               return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("File DOC extension TIDAK DIDUKUNG");
            }

            // --> generate file name
            String filename = minioService.generatedFilename(request.getTalentName(), request.getTalentExperience(),
                  talentLevelOPT.get().getTalentLevelName(), cvFile);

            minioService.uploadFile(filename, cvFile);
            String cvUrl = minioService.getPublicLink(filename);

            talent.setTalentCvUrl(cvUrl);
            talent.setTalentCvFilename(filename);
         }

         Creation creation = Creation.builder().createdBy("yanaxid").build();

         talent.setTalentName(request.getTalentName());
         talent.setTalentStatus(talentStatusOPT.get());
         talent.setEmployeeStatus(employeeStatusOPT.get());
         talent.setTalentLevel(talentLevelOPT.get());
         talent.setEmployeeNumber(request.getNip());
         talent.setGender(request.getSex());
         talent.setBirthDate(request.getDob());
         talent.setTalentDescription(request.getTalentDescription());
         talent.setTalentAvailability(request.getTalentAvailability());
         talent.setTalentExperience(request.getTalentExperience());
         talent.setEmail(request.getEmail());
         talent.setCellphone(request.getCellphone());
         talent.setIsActive(true);
         talent.setTalentMetadata(
               new TalentMetadata(talent, 0, 0, request.getProjectCompleted(), creation));
         talent.setCreation(creation);

         // Save Talent first
         talentRepository.save(talent);

         // --> save talent positions
         if (request.getPositions() != null) {
            List<TalentPosition> talentPositions = new ArrayList<>();

            for (PositionResponseDTO positionDTO : request.getPositions()) {
               Position position = positionRepository.findById(positionDTO.getPositionId())
                     .orElseThrow(() -> new IllegalArgumentException("Invalid Position ID"));

               TalentPosition talentPosition = TalentPosition.builder()
                     .id(new TalentPositionId(talent.getTalentId(), position.getPositionId()))
                     .talent(talent)
                     .position(position)
                     .creation(creation)
                     .build();

               talentPositions.add(talentPosition);
            }

            talentPositionRepository.saveAll(talentPositions);
            talent.setTalentPositions(talentPositions);
         }

         // --> save talent skillsets
         if (request.getSkillsets() != null) {

            List<TalentSkillset> talentSkillsets = new ArrayList<>();

            for (SkillsetResponseDTO skilsetsDTO : request.getSkillsets()) {

               Skillset skillset = skillsetRepository.findById(skilsetsDTO.getSkillsetId())
                     .orElseThrow(() -> new IllegalArgumentException("Invalid Skilset ID"));

               TalentSkillset talentSkillset = TalentSkillset.builder()
                     .id(new TalentSkillsetId(talent.getTalentId(), skillset.getSkillsetId()))
                     .talent(talent)
                     .skillset(skillset)
                     .creation(creation)
                     .build();

               talentSkillsets.add(talentSkillset);
            }

            talentSkillsetRepository.saveAll(talentSkillsets);
            talent.setTalentSkillsets(talentSkillsets);
         }

         return ResponseEntity.ok("Talent with name " + talent.getTalentName() + " created successfully");

      } catch (

      Exception e) {
         log.error("Error creating talent: ", e);
         return ResponseEntity.ok("ERROR");
      }
   }

   // --> post :: upate talent
   @Transactional
   // @CachePut(value = "talent", key = "#talentId")
   public ResponseEntity<?> updateTalent(UUID talentId, TalentRequestDTO request, MultipartFile fotoFile,
         MultipartFile cvFile) {

      try {
         // --> get talent by id
         Optional<Talent> talent = talentRepository.findById(talentId);

         log.info("START >>>>");


         if (talent.isEmpty()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("taletn id not found");
         }

         // --> validate name
         if (request.getTalentName() == null || request.getTalentName().isEmpty()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Nama tidak boleh kosong");
         } else {
            talent.get().setTalentName(request.getTalentName());
         }

         // --> fetch and validate talentStatus
         Optional<TalentStatus> talentStatusOPT = talentStatusRepository.findById(request.getTalentStatus());
         if (talentStatusOPT.isEmpty()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Invalid TalentStatus ID");
         } else {
            talent.get().setTalentStatus(talentStatusOPT.get());
         }

         // --> fetch and validate employeeStatus
         Optional<EmployeeStatus> employeeStatusOPT = employeeStatusRepository.findById(request.getEmployeeStatus());
         if (employeeStatusOPT.isEmpty()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Invalid EmployeeStatus ID");
         } else {
            talent.get().setEmployeeStatus(employeeStatusOPT.get());
         }

         // --> fetch and validate talentLevel
         Optional<TalentLevel> talentLevelOPT = talentLevelRepository.findById(request.getTalentLevel());
         if (talentLevelOPT.isEmpty()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Invalid TalentLevel ID");
         } else {
            talent.get().setTalentLevel(talentLevelOPT.get());
         }

         // --> upload image
         if (fotoFile != null && !fotoFile.isEmpty()) {
            // --> validate image extension :: jpg, jpeg, png
            String[] allowedFotoFile = { ".jpg", ".jpeg", ".png" };
            int count = 0;
            for (String x : allowedFotoFile) {
               if (minioService.getFileExtension(fotoFile.getOriginalFilename()).toLowerCase().equals(x)) {
                  count += 1;
               }
            }
            if (count == 0) {
               return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                     .body("File IMAGE extension TIDAK DIDUKUNG");
            }

            // --> generate file name
            String filename = minioService.generatedFilename(request.getTalentName(), request.getTalentExperience(),
                  talentLevelOPT.get().getTalentLevelName(), fotoFile);

            minioService.updateFile(talent.get().getTalentPhotoFilename(), filename, fotoFile);
            String photoUrl = minioService.getPublicLink(filename);

            talent.get().setTalentPhotoUrl(photoUrl);
            talent.get().setTalentPhotoFilename(filename);
         }

         // --> upload doc
         if (cvFile != null && !cvFile.isEmpty()) {
            // --> validate doc extension :: pdf, docx
            String[] allowedDocFile = { ".pdf", ".docx" };
            int count = 0;
            for (String x : allowedDocFile) {
               if (minioService.getFileExtension(cvFile.getOriginalFilename()).toLowerCase().equals(x)) {
                  count += 1;
               }
            }
            if (count == 0) {
               return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("File DOC extension TIDAK DIDUKUNG");
            }

            // --> generate file name
            String filename = minioService.generatedFilename(request.getTalentName(), request.getTalentExperience(),
                  talentLevelOPT.get().getTalentLevelName(), cvFile);

            minioService.updateFile(talent.get().getTalentCvFilename(), filename, cvFile);

            String cvUrl = minioService.getPublicLink(filename);

            talent.get().setTalentCvUrl(cvUrl);
            talent.get().setTalentCvFilename(filename);
         }

         Creation creation = Creation.builder()
               .createdBy(talent.get().getCreation().getCreatedBy())
               .createdTime(talent.get().getCreation().getCreatedTime())
               .lastModifiedBy("yanaxid").build();

         talent.get().setTalentName(request.getTalentName());
         talent.get().setTalentStatus(talentStatusOPT.get());
         talent.get().setEmployeeStatus(employeeStatusOPT.get());
         talent.get().setTalentLevel(talentLevelOPT.get());
         talent.get().setEmployeeNumber(request.getNip());
         talent.get().setGender(request.getSex());
         talent.get().setBirthDate(request.getDob());
         talent.get().setTalentDescription(request.getTalentDescription());
         talent.get().setTalentAvailability(request.getTalentAvailability());
         talent.get().setTalentExperience(request.getTalentExperience());
         talent.get().setEmail(request.getEmail());
         talent.get().setCellphone(request.getCellphone());
         talent.get().setIsActive(true);
         talent.get().setTalentMetadata(
               new TalentMetadata(talent.get(), 0, 0, request.getProjectCompleted(), creation));
         talent.get().setCreation(creation);

         // Save Talent first
         talentRepository.save(talent.get());

         // --> save talent positions
         if (request.getPositions() != null) {
            List<TalentPosition> talentPositions = new ArrayList<>();

            for (PositionResponseDTO positionDTO : request.getPositions()) {
               Position position = positionRepository.findById(positionDTO.getPositionId())
                     .orElseThrow(() -> new IllegalArgumentException("Invalid Position ID"));

               TalentPosition talentPosition = TalentPosition.builder()
                     .id(new TalentPositionId(talent.get().getTalentId(), position.getPositionId()))
                     .talent(talent.get())
                     .position(position)
                     .creation(creation)
                     .build();

               talentPositions.add(talentPosition);
            }

            talentPositionRepository.saveAll(talentPositions);
            talent.get().setTalentPositions(talentPositions);
         }

         // --> save talent skillsets
         if (request.getSkillsets() != null) {

            List<TalentSkillset> talentSkillsets = new ArrayList<>();

            for (SkillsetResponseDTO skilsetsDTO : request.getSkillsets()) {

               Skillset skillset = skillsetRepository.findById(skilsetsDTO.getSkillsetId())
                     .orElseThrow(() -> new IllegalArgumentException("Invalid Skilset ID"));

               TalentSkillset talentSkillset = TalentSkillset.builder()
                     .id(new TalentSkillsetId(talent.get().getTalentId(), skillset.getSkillsetId()))
                     .talent(talent.get())
                     .skillset(skillset)
                     .creation(creation)
                     .build();

               talentSkillsets.add(talentSkillset);
            }

            talentSkillsetRepository.saveAll(talentSkillsets);
            talent.get().setTalentSkillsets(talentSkillsets);
         }



       

         return ResponseEntity.ok("Talent with name " + talent.get().getTalentName() + " updated successfully");
        

      } catch (

      Exception e) {
         log.error("Error creating talent: ", e);
         return ResponseEntity.ok("ERROR");
      }
   }

}
