package com.tujuhsembilan.app.services;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.tujuhsembilan.app.repository.TalentLevelRepository;
import com.tujuhsembilan.app.repository.TalentRepository;
import com.tujuhsembilan.app.repository.TalentRequestRepository;
import com.tujuhsembilan.app.repository.TalentStatusRepository;
import com.tujuhsembilan.app.services.spesification.TalentSpecification;

import jakarta.persistence.EntityNotFoundException;
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

   // @Autowired
   // private SkillsetRepository skillsetRepository;

   @Autowired
   private MessageSource messageSource;

   @Autowired
   private TalentRequestRepository talentRequestRepository;

   // @Autowired
   // private CacheManager cacheManager;

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

      Page<Talent> talents = talentRepository.findAll(spec, pageable);

      List<UUID> talentIds = talents.stream().map(Talent::getTalentId).collect(Collectors.toList());

      List<Object[]> allPositions = talentRepository.findPositionsByTalentIds(talentIds);
      List<Object[]> allSkillsets = talentRepository.findSkillsetsByTalentIds(talentIds);

      // Kelompokkan posisi dan skillsets berdasarkan talentId
      Map<UUID, List<PositionResponseDTO>> positionsMap = allPositions.stream()
            .collect(Collectors.groupingBy(
                  row -> (UUID) row[2],
                  Collectors.mapping(
                        row -> new PositionResponseDTO((UUID) row[0], (String) row[1]),
                        Collectors.toList())));

      Map<UUID, List<SkillsetResponseDTO>> skillsetsMap = allSkillsets.stream()
            .collect(Collectors.groupingBy(
                  row -> (UUID) row[2],
                  Collectors.mapping(
                        row -> new SkillsetResponseDTO((UUID) row[0], (String) row[1]),
                        Collectors.toList())));

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

               // // --> most efective
               // // --> mendapatkan PositionResponseDTO
               // List<PositionResponseDTO> positions = t.getTalentPositions().stream()
               // .map(tp -> new PositionResponseDTO(tp.getPosition().getPositionId(),
               // tp.getPosition().getPositionName()))
               // .collect(Collectors.toList());

               // // List<PositionResponseDTO> positions =
               // // talentRepository.findPositionsByTalentId(t.getTalentId());
               // response.setPositions(positions);

               // // --> mendapatkan SkillsetResponseDTO
               // List<SkillsetResponseDTO> skillsets = t.getTalentSkillsets().stream()
               // .map(ts -> new SkillsetResponseDTO(ts.getSkillset().getSkillsetId(),
               // ts.getSkillset().getSkillsetName()))
               // .collect(Collectors.toList());

               // // List<SkillsetResponseDTO> skillsets =
               // // talentRepository.findSkillsetsByTalentId(t.getTalentId());
               // response.setSkillsets(skillsets);

               // Set positions and skillsets
               response.setPositions(positionsMap.getOrDefault(t.getTalentId(), Collections.emptyList()));
               response.setSkillsets(skillsetsMap.getOrDefault(t.getTalentId(), Collections.emptyList()));

               return response;
            }).collect(Collectors.toList());

      if (talentResponseDTOs.isEmpty()) {
         return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No talents found for the given criteria.");
      }
      return ResponseEntity.ok(talentResponseDTOs);
   }

   // --> GET :: talent detail
   // @Cacheable(value = "talent", key = "#talentId")
   public ResponseEntity<?> getTalentById(UUID talentId) {

      log.info("START >>>> ");

      Talent talent = talentRepository.findById(talentId)
            .orElseThrow(() -> new EntityNotFoundException(talentId + " not found"));


      List<Object[]> allPositions = talentRepository.findPositionsByTalentId(talentId);
      List<Object[]> allSkillsets = talentRepository.findSkillsetsByTalentId(talentId);

      // Kelompokkan posisi berdasarkan talentId
      Map<UUID, List<PositionResponseDTO>> positionsMap = allPositions.stream()
            .map(row -> new PositionResponseDTO((UUID) row[0], (String) row[1]))
            .collect(Collectors.groupingBy(row -> talentId)); // Gunakan talentId sebagai kunci

      // Kelompokkan skillsets berdasarkan talentId
      Map<UUID, List<SkillsetResponseDTO>> skillsetsMap = allSkillsets.stream()
            .map(row -> new SkillsetResponseDTO((UUID) row[0], (String) row[1]))
            .collect(Collectors.groupingBy(row -> talentId)); // Gunakan talentId sebagai kunci

      TalentDetailResponseDTO td = new TalentDetailResponseDTO();

      td.setTalentId(talent.getTalentId());
      td.setTalentPhoto(talent.getTalentPhotoFilename());
      td.setTalentName(talent.getTalentName());
      td.setTalentStatus(
            talent.getTalentStatus() != null ? talent.getTalentStatus().getTalentStatusName() : null);
      td.setNip(talent.getEmployeeNumber());
      td.setSex(talent.getGender());
      td.setDob(talent.getBirthDate());
      td.setTalentDescription(talent.getTalentDescription());
      td.setCv(talent.getTalentCvFilename());
      td.setTalentExperience(talent.getTalentExperience());
      td.setTalentLevel(
            talent.getTalentLevel() != null ? talent.getTalentLevel().getTalentLevelName() : null);
      td.setProjectCompleted(talent.getTalentMetadata() != null
            ? talent.getTalentMetadata().getTotalProjectCompleted()
            : null);

      td.setTotalRequested(talentRequestRepository.countRequestsByTalentId(talentId));

      td.setPosition(positionsMap.getOrDefault(talentId, Collections.emptyList()));
      td.setSkillSet(skillsetsMap.getOrDefault(talentId, Collections.emptyList()));

      // --> N+1 problem
      // List<SkillsetResponseDTO> skillsets = talent.getTalentSkillsets().stream()
      //       .map(ts -> new SkillsetResponseDTO(ts.getSkillset().getSkillsetId(),
      //             ts.getSkillset().getSkillsetName()))
      //       .collect(Collectors.toList());
      // td.setSkillSet(skillsets.isEmpty() ? null : skillsets);

      td.setEmail(talent.getEmail());
      td.setCellphone(talent.getCellphone());
      td.setEmployeeStatus(
            talent.getEmployeeStatus() != null ? talent.getEmployeeStatus().getEmployeeStatusName()
                  : null);
      td.setTalentAvailability(talent.getTalentAvailability());
      td.setVideoUrl(talent.getBiographyVideoUrl());

      return ResponseEntity.ok(td);
   }
}
