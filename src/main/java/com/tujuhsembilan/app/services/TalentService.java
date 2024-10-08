package com.tujuhsembilan.app.services;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

// import org.hibernate.engine.jdbc.env.internal.LobCreationLogging_.logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
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
import com.tujuhsembilan.app.model_elastic.TalentElastic;
import com.tujuhsembilan.app.models.Talent;
import com.tujuhsembilan.app.repository.EmployeeStatusRepository;
import com.tujuhsembilan.app.repository.TalentLevelRepository;
import com.tujuhsembilan.app.repository.TalentRepository;
import com.tujuhsembilan.app.repository.TalentRequestRepository;
import com.tujuhsembilan.app.repository.TalentStatusRepository;
import com.tujuhsembilan.app.repository_elastic.TalentRepositoryElastic;
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
   private TalentRepositoryElastic talentRepositoryElastic;

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

   public Page<TalentElastic> searchTalents(TalentFilterDTO filter, Pageable pageable) {
      String tl = filter.getKeyword();
      if (tl == null || tl.isEmpty()) {
         tl = "";
      }

      // Ambil halaman hasil pencarian sesuai filter
      Page<TalentElastic> resultPage;

      /*
       * FILTER
       * 
       * level
       * status
       * exp
       * emp ++
       * 
       * level-status
       * level-exp
       * level-emp ++
       * status-exp
       * status-emp ++
       * exp-emp ++
       * 
       * 
       * 
       * level-status-exp
       * 
       * level-status-emp ++
       * level-emp-exp ++
       * status-emp-exp ++
       * 
       * only kyeowrd
       */

      // level
      if (filter.getTalentLevel() != null && filter.getTalentExperience() == null && filter.getTalentStatus() == null) {
         resultPage = talentRepositoryElastic.findLevel(tl.toLowerCase(), filter.getTalentLevel(), pageable);
      }

      // status
      else if (filter.getTalentLevel() == null && filter.getTalentExperience() == null
            && filter.getTalentStatus() != null) {
         resultPage = talentRepositoryElastic.findStatus(tl.toLowerCase(), filter.getTalentStatus(), pageable);
      }

      // exp
      else if (filter.getTalentLevel() == null && filter.getTalentExperience() != null
            && filter.getTalentStatus() == null) {
         resultPage = findNameExp(filter, pageable);
      }

      // level-status
      else if (filter.getTalentLevel() != null && filter.getTalentExperience() == null
            && filter.getTalentStatus() != null) {
         resultPage = talentRepositoryElastic.findLevelStatus(tl.toLowerCase(), filter.getTalentLevel(),
               filter.getTalentStatus(), pageable);
      }

      // level-exp
      else if (filter.getTalentLevel() != null && filter.getTalentExperience() != null
            && filter.getTalentStatus() == null) {
         resultPage = findNameLevelExp(filter, pageable);
      }

      // status-exp
      else if (filter.getTalentLevel() == null && filter.getTalentExperience() != null
            && filter.getTalentStatus() != null) {
         resultPage = findNameStatusExp(filter, pageable);
      }

      // level-status-exp
      else if (filter.getTalentLevel() != null && filter.getTalentStatus() != null
            && filter.getTalentExperience() != null) {
         resultPage = findNameLevelStatusExp(filter, pageable);
      }

      // only kyeowrd
      else {
         resultPage = talentRepositoryElastic.searchByKeyword(tl.toLowerCase(), pageable);
      }

      // Batasi hasil ke 10 halaman
      return limitToMaxPages(resultPage, pageable, 10);
   }

   private Page<TalentElastic> limitToMaxPages(Page<TalentElastic> resultPage, Pageable pageable, int maxPages) {

      if (resultPage.getTotalPages() > maxPages) {

         // --> hitung total element
         // misal size 50 * 10 = 500
         // misal size 20 * 10 = 200
         int maxElements = maxPages * pageable.getPageSize();

         List<TalentElastic> limitedContent = resultPage.getContent().stream().limit(maxElements)
               .collect(Collectors.toList());

         // content ( hal = 1, size = 0, max 500)
         return new PageImpl<>(limitedContent, PageRequest.of(0, pageable.getPageSize()), maxElements);
      }

      return resultPage;
   }

   public Page<TalentElastic> findNameExp(TalentFilterDTO filter, Pageable pageable) {
      // --> create category
      String keyword = filter.getKeyword() != null ? filter.getKeyword().toLowerCase() : "";
      int minExperience = 0;
      int maxExperience = Integer.MAX_VALUE;

      switch (filter.getTalentExperience()) {
         case 0:
            maxExperience = 1; // 0-1 tahun
            break;
         case 1:
            minExperience = 2; // 2-4 tahun
            maxExperience = 4;
            break;
         case 2:
            minExperience = 5; // 5 tahun ke atas
            break;
         default:
            minExperience = 0;
            maxExperience = Integer.MAX_VALUE; // Semua kategori
            break;
      }

      return talentRepositoryElastic.findExp(keyword, minExperience, maxExperience,
            pageable);
   }

   public Page<TalentElastic> findNameLevelStatusExp(TalentFilterDTO filter, Pageable pageable) {
      String keyword = filter.getKeyword() != null ? filter.getKeyword().toLowerCase() : "";
      int minExperience = 0;
      int maxExperience = Integer.MAX_VALUE;

      switch (filter.getTalentExperience()) {
         case 0:
            maxExperience = 1; // 0-1 tahun
            break;
         case 1:
            minExperience = 2; // 2-4 tahun
            maxExperience = 4;
            break;
         case 2:
            minExperience = 5; // 5 tahun ke atas
            break;
         default:
            minExperience = 0;
            maxExperience = Integer.MAX_VALUE; // Semua kategori
            break;
      }

      return talentRepositoryElastic.findLevelStatusExp(
            keyword,
            minExperience,
            maxExperience,
            filter.getTalentLevel(),
            filter.getTalentStatus(),
            pageable);
   }

   public Page<TalentElastic> findNameLevelExp(TalentFilterDTO filter, Pageable pageable) {
      String keyword = filter.getKeyword() != null ? filter.getKeyword().toLowerCase() : "";
      int minExperience = 0;
      int maxExperience = Integer.MAX_VALUE;

      switch (filter.getTalentExperience()) {
         case 0:
            maxExperience = 1; // 0-1 tahun
            break;
         case 1:
            minExperience = 2; // 2-4 tahun
            maxExperience = 4;
            break;
         case 2:
            minExperience = 5; // 5 tahun ke atas
            break;
         default:
            minExperience = 0;
            maxExperience = Integer.MAX_VALUE; // Semua kategori
            break;
      }

      return talentRepositoryElastic.findLevelExp(
            keyword,
            minExperience,
            maxExperience,
            filter.getTalentLevel(),
            pageable);
   }

   public Page<TalentElastic> findNameStatusExp(TalentFilterDTO filter, Pageable pageable) {
      String keyword = filter.getKeyword() != null ? filter.getKeyword().toLowerCase() : "";
      int minExperience = 0;
      int maxExperience = Integer.MAX_VALUE;

      switch (filter.getTalentExperience()) {
         case 0:
            maxExperience = 1; // 0-1 tahun
            break;
         case 1:
            minExperience = 2; // 2-4 tahun
            maxExperience = 4;
            break;
         case 2:
            minExperience = 5; // 5 tahun ke atas
            break;
         default:
            minExperience = 0;
            maxExperience = Integer.MAX_VALUE; // Semua kategori
            break;
      }

      return talentRepositoryElastic.findStatusExp(
            keyword,
            minExperience,
            maxExperience,
            filter.getTalentStatus(),
            pageable);
   }

   // --> GET :: all talents
   // @Cacheable("talents")
   public ResponseEntity<?> getTalents(TalentFilterDTO request, Pageable pageable) {

      log.info("Pageable received in service: {}", pageable);

      // --> cek talent level
      if (request.getTalentLevel() != null && !request.getTalentLevel().isEmpty()) {
         boolean talentLevelExists = talentLevelRepository.existsByTalentLevelNameIgnoreCase(request.getTalentLevel());
         if (!talentLevelExists) {
            String message = messageSource.getMessage("talent.not.found", null,
                  Locale.getDefault());
            String formatMessage = MessageFormat.format(message,
                  request.getTalentLevel());
            return ResponseEntity
                  .status(HttpStatus.NOT_FOUND)
                  .body(new NotFoundResponse(formatMessage, HttpStatus.NOT_FOUND.value(),
                        HttpStatus.NOT_FOUND.getReasonPhrase()));
         }
      }

      // --> cek Employee status
      if (request.getEmployeeStatus() != null &&
            !request.getEmployeeStatus().isEmpty()) {
         boolean employeeStatus = employeeStatusRepository
               .existsByEmployeeStatusNameIgnoreCase(request.getEmployeeStatus());
         if (!employeeStatus) {
            String message = messageSource.getMessage("talent.not.found", null,
                  Locale.getDefault());
            String formatMessage = MessageFormat.format(message,
                  request.getEmployeeStatus());
            return ResponseEntity
                  .status(HttpStatus.NOT_FOUND)
                  .body(new NotFoundResponse(formatMessage, HttpStatus.NOT_FOUND.value(),
                        HttpStatus.NOT_FOUND.getReasonPhrase()));
         }
      }

      // --> cek Talent status
      if (request.getTalentStatus() != null &&
            !request.getTalentStatus().isEmpty()) {
         boolean talentStatus = statusRepository.existsByTalentStatusNameIgnoreCase(request.getTalentStatus());
         if (!talentStatus) {
            String message = messageSource.getMessage("talent.not.found", null,
                  Locale.getDefault());
            String formatMessage = MessageFormat.format(message,
                  request.getTalentStatus());
            return ResponseEntity
                  .status(HttpStatus.NOT_FOUND)
                  .body(new NotFoundResponse(formatMessage, HttpStatus.NOT_FOUND.value(),
                        HttpStatus.NOT_FOUND.getReasonPhrase()));
         }
      }

      Specification<Talent> spec = TalentSpecification.filter(request);

      Page<Talent> talents = talentRepository.findAll(spec, pageable);

      log.info(talents.get().toString());

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

               // --> most efective
               // --> mendapatkan PositionResponseDTO
               List<PositionResponseDTO> positions = t.getTalentPositions().stream()
                     .map(tp -> new PositionResponseDTO(tp.getPosition().getPositionId(),
                           tp.getPosition().getPositionName()))
                     .collect(Collectors.toList());

               // List<PositionResponseDTO> positions =
               // talentRepository.findPositionsByTalentId(t.getTalentId());
               response.setPositions(positions);

               // --> mendapatkan SkillsetResponseDTO
               List<SkillsetResponseDTO> skillsets = t.getTalentSkillsets().stream()
                     .map(ts -> new SkillsetResponseDTO(ts.getSkillset().getSkillsetId(),
                           ts.getSkillset().getSkillsetName()))
                     .collect(Collectors.toList());

               // List<SkillsetResponseDTO> skillsets =
               // talentRepository.findSkillsetsByTalentId(t.getTalentId());
               response.setSkillsets(skillsets);

               // Set positions and skillsets
               response.setPositions(positionsMap.getOrDefault(t.getTalentId(),
                     Collections.emptyList()));
               response.setSkillsets(skillsetsMap.getOrDefault(t.getTalentId(),
                     Collections.emptyList()));

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
      td.setDob(String.valueOf(talent.getBirthDate()));
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

      td.setEmail(talent.getEmail());
      td.setCellphone(talent.getCellphone());
      td.setEmployeeStatus(
            talent.getEmployeeStatus() != null ? talent.getEmployeeStatus().getEmployeeStatusName()
                  : null);
      td.setTalentAvailability(talent.getTalentAvailability());
      td.setVideoUrl(talent.getBiographyVideoUrl());
      td.setTalentPhotoUrl(talent.getTalentPhotoUrl());
      td.setTalentCvUrl(talent.getTalentCvUrl());

      return ResponseEntity.ok(td);
   }
}
