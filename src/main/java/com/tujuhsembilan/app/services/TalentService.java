package com.tujuhsembilan.app.services;

import java.text.MessageFormat;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.tujuhsembilan.app.dtos.request.TalentFilterDTO;
import com.tujuhsembilan.app.dtos.response.NotFoundResponse;
import com.tujuhsembilan.app.dtos.response.PositionResponseDTO;
import com.tujuhsembilan.app.dtos.response.SkillsetResponseDTO;
import com.tujuhsembilan.app.dtos.response.TalentDetailResponseDTO;
import com.tujuhsembilan.app.dtos.response.TalentResponseDTO;
import com.tujuhsembilan.app.models.Talent;
import com.tujuhsembilan.app.repository.EmployeeStatusRepository;
import com.tujuhsembilan.app.repository.SkillsetRepository;
import com.tujuhsembilan.app.repository.TalentLevelRepository;
import com.tujuhsembilan.app.repository.TalentRepository;
import com.tujuhsembilan.app.repository.TalentRequestRepository;
import com.tujuhsembilan.app.repository.TalentStatusRepository;
import com.tujuhsembilan.app.services.spesification.TalentSpecification;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
@Service
public class TalentService {

   @Autowired
   private TalentRepository talentRepository;

   @Autowired
   private TalentLevelRepository talentLevelRepository;

   @Autowired
   private EmployeeStatusRepository employeeStatusRepository;

   @Autowired
   private TalentStatusRepository statusRepository;

   @Autowired
   private SkillsetRepository skillsetRepository;

   @Autowired
   private MessageSource messageSource;

   @Autowired
   private TalentRequestRepository talentRequestRepository;

   // --> GET :: all talents
   // @Cacheable("talents")
   public ResponseEntity<?> getTalents(TalentFilterDTO request, Pageable pageable) {

      log.info("Pageable received in service: {}", pageable);

      // --> cek talent level
      if (request.getTalentLevel() != null && !request.getTalentLevel().isEmpty()) {
         boolean talentLevelExists = talentLevelRepository.existsByTalentLevelNameIgnoreCase(request.getTalentLevel());
         if (!talentLevelExists) {
            String message = messageSource.getMessage("talent.not.found", null, Locale.getDefault());
            String formatMessage = MessageFormat.format(message, request.getTalentLevel());
            return ResponseEntity
                  .status(HttpStatus.NOT_FOUND)
                  .body(new NotFoundResponse(formatMessage, HttpStatus.NOT_FOUND.value(),
                        HttpStatus.NOT_FOUND.getReasonPhrase()));
         }
      }

      // --> cek Employee status
      if (request.getEmployeeStatus() != null && !request.getEmployeeStatus().isEmpty()) {
         boolean employeeStatus = employeeStatusRepository
               .existsByEmployeeStatusNameIgnoreCase(request.getEmployeeStatus());
         if (!employeeStatus) {
            String message = messageSource.getMessage("talent.not.found", null, Locale.getDefault());
            String formatMessage = MessageFormat.format(message, request.getEmployeeStatus());
            return ResponseEntity
                  .status(HttpStatus.NOT_FOUND)
                  .body(new NotFoundResponse(formatMessage, HttpStatus.NOT_FOUND.value(),
                        HttpStatus.NOT_FOUND.getReasonPhrase()));
         }
      }

      // --> cek Talent status
      if (request.getTalentStatus() != null && !request.getTalentStatus().isEmpty()) {
         boolean talentStatus = statusRepository.existsByTalentStatusNameIgnoreCase(request.getTalentStatus());
         if (!talentStatus) {
            String message = messageSource.getMessage("talent.not.found", null, Locale.getDefault());
            String formatMessage = MessageFormat.format(message, request.getTalentStatus());
            return ResponseEntity
                  .status(HttpStatus.NOT_FOUND)
                  .body(new NotFoundResponse(formatMessage, HttpStatus.NOT_FOUND.value(),
                        HttpStatus.NOT_FOUND.getReasonPhrase()));
         }
      }

      Specification<Talent> spec = TalentSpecification.filter(request);
      log.info("try +++++++++++++++++++++++++++++++++++++++++++++");

      Page<Talent> talents = talentRepository.findAll(spec, pageable);

      List<TalentResponseDTO> talentResponseDTOs = talents.stream().map(
            t -> {
               TalentResponseDTO response = new TalentResponseDTO();
               response.setTalentId(t.getTalentId());
               response.setTalentPhotoUrl(t.getTalentPhotoFilename());
               response.setTalentName(t.getTalentName());
               response.setTalentStatus(t.getTalentStatus() != null ? t.getTalentStatus().getTalentStatusName() : null);
               response.setEmployeeStatus(
                     t.getEmployeeStatus() != null ? t.getEmployeeStatus().getEmployeeStatusName()
                           : null);
               response.setTalentAvailability(t.getTalentAvailability());
               response.setTalentExperience(t.getTalentExperience());
               response.setTalentLevel(t.getTalentLevel() != null ? t.getTalentLevel().getTalentLevelName() : null);

               // --> most efective
               // --> mendapatkan PositionResponseDTO
               List<PositionResponseDTO> positions = t.getTalentPositions().stream()
                     .map(tp -> new PositionResponseDTO(tp.getPosition().getPositionId(),
                           tp.getPosition().getPositionName()))
                     .collect(Collectors.toList());

               // List<PositionResponseDTO> positions = talentRepository.findPositionsByTalentId(t.getTalentId());
               response.setPositions(positions);

               // --> mendapatkan SkillsetResponseDTO
               List<SkillsetResponseDTO> skillsets = t.getTalentSkillsets().stream()
                     .map(ts -> new SkillsetResponseDTO(ts.getSkillset().getSkillsetId(),
                           ts.getSkillset().getSkillsetName()))
                     .collect(Collectors.toList());

               // List<SkillsetResponseDTO> skillsets = talentRepository.findSkillsetsByTalentId(t.getTalentId());
               response.setSkillsets(skillsets);

               return response;
            }).collect(Collectors.toList());

      if (talentResponseDTOs.isEmpty()) {
         return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No talents found for the given criteria.");
      }
      return ResponseEntity.ok(talentResponseDTOs);
   }

   // --> GET :: talent detail
   // @Cacheable("talents")
   // public ResponseEntity<?> getTalentById(UUID talentId) {

   // log.info("start ----------------------> ");

   // Optional<Talent> talentOPT = talentRepository.findById(talentId);

   // if (talentOPT.isEmpty()) {
   // String message = messageSource.getMessage("talent.not.found", null,
   // Locale.getDefault());
   // String formatMessage = MessageFormat.format(message, talentId);
   // return ResponseEntity
   // .status(HttpStatus.NOT_FOUND)
   // .body(new NotFoundResponse(formatMessage, HttpStatus.NOT_FOUND.value(),
   // HttpStatus.NOT_FOUND.getReasonPhrase()));
   // }

   // TalentDetailResponseDTO td = new TalentDetailResponseDTO();

   // td.setTalentId(talentOPT.get().getTalentId());
   // td.setTalentPhoto(talentOPT.get().getTalentPhotoFilename());
   // td.setTalentName(talentOPT.get().getTalentName());
   // td.setTalentStatus(
   // talentOPT.get().getTalentStatus() != null ?
   // talentOPT.get().getTalentStatus().getTalentStatusName() : null);
   // td.setNip(talentOPT.get().getEmployeeNumber());
   // td.setSex(talentOPT.get().getGender());
   // td.setDob(talentOPT.get().getBirthDate());
   // td.setTalentDescription(talentOPT.get().getTalentDescription());
   // td.setCv(talentOPT.get().getTalentCvFilename());
   // td.setTalentExperience(talentOPT.get().getTalentExperience());
   // td.setTalentLevel(
   // talentOPT.get().getTalentLevel() != null ?
   // talentOPT.get().getTalentLevel().getTalentLevelName() : null);
   // td.setProjectCompleted(talentOPT.get().getTalentMetadata() != null
   // ? talentOPT.get().getTalentMetadata().getTotalProjectCompleted()
   // : null);

   // td.setTotalRequested(talentRequestRepository.countRequestsByTalentId(talentId));

   // // --> mendapatkan PositionResponseDTO
   // List<PositionResponseDTO> positions =
   // talentOPT.get().getTalentPositions().stream()
   // .map(tp -> new PositionResponseDTO(tp.getPosition().getPositionId(),
   // tp.getPosition().getPositionName()))
   // .collect(Collectors.toList());
   // // td.setPositions(positions);
   // td.setPosition(positions.isEmpty() ? null : positions);

   // td.setPosition(positions.isEmpty() ? null : positions);

   // // --> mendapatkan SkillsetResponseDTO
   // List<SkillsetResponseDTO> skillsets =
   // talentOPT.get().getTalentSkillsets().stream()
   // .map(ts -> new SkillsetResponseDTO(ts.getSkillset().getSkillsetId(),
   // ts.getSkillset().getSkillsetName()))
   // .collect(Collectors.toList());
   // td.setSkillSet(skillsets.isEmpty() ? null : skillsets);

   // td.setEmail(talentOPT.get().getEmail());
   // td.setCellphone(talentOPT.get().getCellphone());
   // td.setEmployeeStatus(
   // talentOPT.get().getEmployeeStatus() != null ?
   // talentOPT.get().getEmployeeStatus().getEmployeeStatusName()
   // : null);
   // td.setTalentAvailability(talentOPT.get().getTalentAvailability());
   // td.setVideoUrl(talentOPT.get().getBiographyVideoUrl());

   // return ResponseEntity.ok(td);
   // }
}
