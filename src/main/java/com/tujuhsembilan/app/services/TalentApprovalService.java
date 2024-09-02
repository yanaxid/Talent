package com.tujuhsembilan.app.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.tujuhsembilan.app.dtos.request.ApprovedRequestDTO;
import com.tujuhsembilan.app.dtos.request.TalentApprovalFilterDTO;
import com.tujuhsembilan.app.dtos.response.ApprovalDTO;
import com.tujuhsembilan.app.model_elastic.ApprovalDTOElastic;
import com.tujuhsembilan.app.model_elastic.TalentElastic;
import com.tujuhsembilan.app.models.TalentRequest;
import com.tujuhsembilan.app.models.TalentRequestStatus;
import com.tujuhsembilan.app.repository.TalentRequestRepository;
import com.tujuhsembilan.app.repository.TalentRequestStatusRepository;
import com.tujuhsembilan.app.repository_elastic.ApprovalRevositoryElastic;
import com.tujuhsembilan.app.services.spesification.TalentApprovalSpesicication;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
@Service
public class TalentApprovalService {

   @Autowired
   private TalentRequestRepository talentRequestRepository;

   @Autowired
   private TalentRequestStatusRepository talentRequestStatusRepository;

   @Autowired
   private ApprovalRevositoryElastic approvalRevositoryElastic;

   public ResponseEntity<Page<ApprovalDTO>> getListApprovals(TalentApprovalFilterDTO filter, Pageable pageable) {
      try {
         Specification<TalentRequest> spec = TalentApprovalSpesicication.filter(filter);
         Page<TalentRequest> talentRequests = talentRequestRepository.findAll(spec, pageable);

         List<ApprovalDTO> approvalDTOs = talentRequests.stream().map(
               t -> {
                  ApprovalDTO approvalDTO = new ApprovalDTO();
                  approvalDTO.setTalentRequestId(t.getTalentRequestId());
                  approvalDTO.setAgencyName(t.getTalentWishlist().getClient().getAgencyName());
                  approvalDTO.setTalentName(t.getTalentWishlist().getTalent().getTalentName());
                  approvalDTO.setApprovalStatus(t.getTalentRequestStatus().getTalentRequestStatusName());
                  approvalDTO.setRequestDate(t.getRequestDate());
                  return approvalDTO;
               }).collect(Collectors.toList());

         Page<ApprovalDTO> approvalDTOPage = new PageImpl<>(approvalDTOs, pageable, talentRequests.getTotalElements());
         return ResponseEntity.ok(approvalDTOPage);

      } catch (Exception e) {
         // Mengembalikan INTERNAL_SERVER_ERROR jika terjadi error
         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
      }
   }

   public Page<ApprovalDTOElastic> searchListApprovals(TalentApprovalFilterDTO filter, Pageable pageable) {

      String tl = filter.getKeyword();
      if (filter.getKeyword() == null || filter.getKeyword().isEmpty()) {
         tl = "";
      }

      String tanggal = null;

      if (filter.getRequestDate() != null) {
         SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
         tanggal = outputFormat.format(filter.getRequestDate());

         log.info("---------------------------->" + tanggal + "----" +
               filter.getRequestDate());
      }

      String category = "talent_name";

      if (filter.getSearchBy() != null &&
            filter.getSearchBy().equalsIgnoreCase("talent_name")) {
         category = "talent_name";
      } else if (filter.getSearchBy() != null &&
            filter.getSearchBy().equalsIgnoreCase("agency_name")) {
         category = "agency_name";
      } else if (filter.getSearchBy() == null || filter.getSearchBy().isEmpty()) {
         category = "talent_name";
      }

      // --> FILTER QUERY

      // keyword

      // tanggal
      // status
      // tanggal + status

      // log.info("----------------------> " + category);



      Page<ApprovalDTOElastic> resultPage = null;

      if (tanggal != null && filter.getTalentRequestStatus() == null) {
         // cari by tanggal
         resultPage=  approvalRevositoryElastic.findByTanggal(tl.toLowerCase(), tanggal,
               category, pageable);

      } else if (tanggal == null && filter.getTalentRequestStatus() != null) {
         // cari by stataus

         log.info("filter.getRequestDate() " + filter.getRequestDate());
         resultPage = approvalRevositoryElastic.findByStatus(tl.toLowerCase(),
               filter.getTalentRequestStatus(), category,
               pageable);

      } else if (tanggal != null && filter.getTalentRequestStatus() != null) {
         // cari by tanggal + status
         resultPage = approvalRevositoryElastic.findByTanggalAndStatus(tl.toLowerCase(),
               tanggal,
               filter.getTalentRequestStatus(), category, pageable);
      } else {
         resultPage = approvalRevositoryElastic.searchByKeyword(tl.toLowerCase(), category,
               pageable);
      }



      return limitToMaxPages(resultPage, pageable, 10);

   }

   private Page<ApprovalDTOElastic> limitToMaxPages(Page<ApprovalDTOElastic> resultPage, Pageable pageable,
         int maxPages) {

      if (resultPage.getTotalPages() > maxPages) {

         // --> hitung total element
         // misal size 50 * 10 = 500
         // misal size 20 * 10 = 200
         int maxElements = maxPages * pageable.getPageSize();

         List<ApprovalDTOElastic> limitedContent = resultPage.getContent().stream().limit(maxElements)
               .collect(Collectors.toList());

         // content ( hal = 1, size = 0, max 500)
         return new PageImpl<>(limitedContent, PageRequest.of(0, pageable.getPageSize()), maxElements);
      }

      return resultPage;
   }

   // --> get :: edit persetujuan talent
   @Transactional
   public ResponseEntity<?> putApproval(ApprovedRequestDTO request) {

      try {

         // --> fetch
         TalentRequest talentRequest = talentRequestRepository
               .findById(request.getTalentRequestId())
               .orElseThrow(() -> new EntityNotFoundException("Id " + request.getTalentRequestId() + " not found"));

         TalentRequestStatus talentRequestStatus = talentRequestStatusRepository
               .findTalentRequestStatusByName(request.getAction())
               .orElseThrow(() -> new EntityNotFoundException(request.getAction() + " is not exist"));

         // --> set data
         talentRequest.setTalentRequestStatus(talentRequestStatus);
         talentRequest.setRequestRejectReason(request.getRejectReason());
         talentRequest.getCreation().setLastModifiedBy("yana");

         // --> save
         talentRequestRepository.save(talentRequest);

         return ResponseEntity
               .ok(talentRequest.getTalentWishlist().getTalent().getTalentName() + " " + request.getAction());
      } catch (Exception e) {
         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
      }

   }

}
