package com.tujuhsembilan.app.services.spesification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.tujuhsembilan.app.dtos.request.TalentRequestDTO;
import com.tujuhsembilan.app.models.Talent;

import jakarta.persistence.criteria.Predicate;

public class TalentSpecification {

   public static Specification<Talent> filter(TalentRequestDTO request) {
      return (root, query, criteriaBuilder) -> {

         List<Predicate> predicates = new ArrayList<Predicate>();

         // --> talent level
         if (request.getTalentLevel() != null) {

            predicates.add(criteriaBuilder.like(
                  criteriaBuilder.lower(root.get("talentLevel")),
                  "%" + request.getTalentLevel().toLowerCase() + "%"));
         }

         // --> talent experience
         if (request.getTalentExperience() != null) {
            int experience = request.getTalentExperience();

            if (experience == 0) {
               predicates.add(criteriaBuilder.between(root.get("experience"), 0, 2));
            } else if (experience == 1) {
               predicates.add(criteriaBuilder.between(root.get("experience"), 2, 4));
            } else if (experience == 2) {
               predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("experience"), 5));
            }
         }

         // --> talent status
         if (request.getTalentStatus() != null) {

            predicates.add(criteriaBuilder.like(
                  criteriaBuilder.lower(root.get("talentStatus")),
                  "%" + request.getTalentStatus().toLowerCase() + "%"));
         }

         // --> employee status
         if (request.getEmployeeStatus() != null) {

            predicates.add(criteriaBuilder.like(
                  criteriaBuilder.lower(root.get("employeeStatus")),
                  "%" + request.getEmployeeStatus().toLowerCase() + "%"));
         }

         return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
      };
   }
}
