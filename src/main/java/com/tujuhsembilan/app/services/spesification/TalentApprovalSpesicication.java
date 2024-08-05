package com.tujuhsembilan.app.services.spesification;


import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.tujuhsembilan.app.dtos.request.TalentApprovalFilterDTO;
import com.tujuhsembilan.app.models.TalentRequest;
import com.tujuhsembilan.app.models.TalentRequestStatus;


import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TalentApprovalSpesicication {

   public static Specification<TalentRequest> filter(TalentApprovalFilterDTO filter) {
      return (root, query, criteriaBuilder) -> {

         List<Predicate> predicates = new ArrayList<Predicate>();

         // --> aproval status
         if (filter.getTalentRequestStatus() != null && !filter.getTalentRequestStatus().isEmpty()) {
            Join<TalentRequest, TalentRequestStatus> TalentRequestJoin = root.join("talentRequestStatus");
            predicates.add(criteriaBuilder.equal(
                  criteriaBuilder.lower(TalentRequestJoin.get("talentRequestStatusName")),
                  filter.getTalentRequestStatus().toLowerCase()));
         }

         if (filter.getRequestDate() != null) {
            String requestDate = "%" + filter.getRequestDate().toLowerCase() + "%";

            predicates.add(criteriaBuilder.like(
                  criteriaBuilder.function("TO_CHAR", String.class,
                        root.get("requestDate"),
                        criteriaBuilder.literal("YYYY-MM-DD")),
                  requestDate

            ));

         }

         

         return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
      };
   }
}
