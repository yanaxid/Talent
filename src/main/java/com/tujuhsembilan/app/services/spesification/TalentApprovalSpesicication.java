package com.tujuhsembilan.app.services.spesification;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.tujuhsembilan.app.dtos.request.TalentApprovalFilterDTO;
import com.tujuhsembilan.app.models.Client;
import com.tujuhsembilan.app.models.EmployeeStatus;
import com.tujuhsembilan.app.models.Talent;
import com.tujuhsembilan.app.models.TalentLevel;
import com.tujuhsembilan.app.models.TalentRequest;
import com.tujuhsembilan.app.models.TalentRequestStatus;
import com.tujuhsembilan.app.models.TalentStatus;
import com.tujuhsembilan.app.models.TalentWishlist;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TalentApprovalSpesicication {

   public static Specification<TalentRequest> filter(TalentApprovalFilterDTO filter) {
      return (root, query, criteriaBuilder) -> {

         List<Predicate> predicates = new ArrayList<Predicate>();

         // --> agency name
         // if (filter.getAgencyName() != null && !filter.getAgencyName().isEmpty()) {

         // Join<TalentRequest, TalentWishlist> wishlistJoin =
         // root.join("talentWishlist");
         // Join<TalentWishlist, Client> clientJoin = wishlistJoin.join("client");

         // predicates.add(criteriaBuilder.equal(
         // criteriaBuilder.lower(clientJoin.get("agencyName")),
         // filter.getAgencyName().toLowerCase()));
         // }

         // --> aproval status
         if (filter.getTalentRequestStatus() != null && !filter.getTalentRequestStatus().isEmpty()) {
            Join<TalentRequest, TalentRequestStatus> TalentRequestJoin = root.join("talentRequestStatus");
            predicates.add(criteriaBuilder.equal(
                  criteriaBuilder.lower(TalentRequestJoin.get("talentRequestStatusName")),
                  filter.getTalentRequestStatus().toLowerCase()));
         }

         // --> aproval status
         // if (filter.getRequestDate() != null) {

         log.info("----------------> BEFORE " + filter.getRequestDate());

         // // String a = "%" + filter.getRequestDate() + "%";

         log.info("----------------> AFTER " + root.get("requestDate"));
         // predicates.add(criteriaBuilder.equal(
         // root.get("requestDate"),
         // filter.getRequestDate()));
         // }

         if (filter.getRequestDate() != null) {
            predicates.add(criteriaBuilder.equal(root.get("requestDate"), filter.getRequestDate()));
         }

         return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
      };
   }
}
