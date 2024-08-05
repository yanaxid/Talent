package com.tujuhsembilan.app.services.spesification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.tujuhsembilan.app.dtos.request.TalentFilterDTO;
import com.tujuhsembilan.app.models.EmployeeStatus;
import com.tujuhsembilan.app.models.Talent;
import com.tujuhsembilan.app.models.TalentLevel;
import com.tujuhsembilan.app.models.TalentStatus;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TalentSpecification {

   public static Specification<Talent> filter(TalentFilterDTO filter) {
      return (root, query, criteriaBuilder) -> {

         List<Predicate> predicates = new ArrayList<Predicate>();

         // --> talent level
         if (filter.getTalentLevel() != null && !filter.getTalentLevel().isEmpty()) {
            Join<Talent, TalentLevel> talentLevelJoin = root.join("talentLevel");
            predicates.add(criteriaBuilder.equal(
                  criteriaBuilder.lower(talentLevelJoin.get("talentLevelName")),
                  filter.getTalentLevel().toLowerCase()));
         }

         // --> talent experience
         if (filter.getTalentExperience() != null) {
            int experience = filter.getTalentExperience();

            if (experience == 0) {
               predicates.add(criteriaBuilder.between(root.get("talentExperience"), 0, 1));
            } else if (experience == 1) {
               predicates.add(criteriaBuilder.between(root.get("talentExperience"), 2, 4));
            } else if (experience == 2) {
               predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("talentExperience"),
                     5));
            }
         }

         // --> talent status
         if (filter.getTalentStatus() != null && !filter.getTalentStatus().isEmpty()) {
            Join<Talent, TalentStatus> joinTalentStatus = root.join("talentStatus");

            predicates.add(criteriaBuilder.equal(
                  criteriaBuilder.lower(joinTalentStatus.get("talentStatusName")),
                  filter.getTalentStatus().toLowerCase()));
         }

         // --> employee status
         if (filter.getEmployeeStatus() != null && !filter.getEmployeeStatus().isEmpty()) {
            Join<Talent, EmployeeStatus> talentEmployeeJoin = root.join("employeeStatus");
            predicates.add(criteriaBuilder.equal(
                  criteriaBuilder.lower(talentEmployeeJoin.get("employeeStatusName")),
                  filter.getEmployeeStatus().toLowerCase()));
         }

         // --> serch
         if (filter.getKeyword() != null) {

            String keyword = "%" + filter.getKeyword().toLowerCase() + "%";

            predicates.add(criteriaBuilder.like(
                  criteriaBuilder.lower(root.get("talentName")), keyword));
         }

         // // --> search
         // if (filter.getKeyword() != null && !filter.getKeyword().isEmpty()) {
         // String keyword = filter.getKeyword().toLowerCase();

         // // Full-text search using to_tsvector and to_tsquery
         // Expression<String> ftsVector = criteriaBuilder.function("to_tsvector",
         // String.class,
         // criteriaBuilder.literal("english"), root.get("talentName"));
         // Expression<Boolean> ftsMatch = criteriaBuilder.function("to_tsquery",
         // Boolean.class,
         // criteriaBuilder.literal("english"), criteriaBuilder.literal(keyword));
         // predicates.add(criteriaBuilder.isTrue(ftsMatch));
         // }

         // --> search
         // if (filter.getKeyword() != null && !filter.getKeyword().isEmpty()) {
         // String keyword = filter.getKeyword().toLowerCase();

         // // Full-text search using to_tsvector and to_tsquery
         // Expression<Boolean> ftsMatch = criteriaBuilder.function(
         // "to_tsvector", String.class, criteriaBuilder.literal("english"),
         // root.get("talentName"))
         // .in(criteriaBuilder.function("to_tsquery", String.class,
         // criteriaBuilder.literal("english"), criteriaBuilder.literal(keyword)));
         // predicates.add(criteriaBuilder.isTrue(ftsMatch));
         // }

         return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
      };
   }
}
