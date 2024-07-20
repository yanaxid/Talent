package com.tujuhsembilan.app.services.spesification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.tujuhsembilan.app.dtos.request.TalentRequestDTO;
import com.tujuhsembilan.app.models.Talent;
import com.tujuhsembilan.app.models.TalentLevel;
import com.tujuhsembilan.app.models.TalentStatus;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TalentSpecification {

   public static Specification<Talent> filter(TalentRequestDTO request) {
      return (root, query, criteriaBuilder) -> {

         List<Predicate> predicates = new ArrayList<Predicate>();

         // --> talent level
         if (request.getTalentLevel() != null) {
            Join<Talent, TalentLevel> talentLevelJoin = root.join("talentLevel");
            predicates.add(criteriaBuilder.like(
                  criteriaBuilder.lower(talentLevelJoin.get("talentLevelName")),
                  "%" + request.getTalentLevel().toLowerCase() + "%"));
         }

         // --> talent experience
         if (request.getTalentExperience() != null) {
            int experience = request.getTalentExperience();

            if (experience == 0) {
               predicates.add(criteriaBuilder.between(root.get("experience"), 0, 1));
            } else if (experience == 1) {
               predicates.add(criteriaBuilder.between(root.get("experience"), 2, 4));
            } else if (experience == 2) {
               predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("experience"),
                     5));
            }
         }

         // --> talent status
         if (request.getTalentStatus() != null) {

            Join<Talent, TalentStatus> joinTalentStatus = root.join("talentStatus");

            predicates.add(criteriaBuilder.equal(
                  criteriaBuilder.lower(joinTalentStatus.get("talentStatusName")),
                  request.getTalentStatus().toLowerCase()));
         }

         // // --> employee status
         // if (request.getEmployeeStatus() != null) {

         //    predicates.add(criteriaBuilder.like(
         //          criteriaBuilder.lower(root.get("employeeStatus")),
         //          "%" + request.getEmployeeStatus().toLowerCase() + "%"));
         // }

         return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
      };
   }
}
