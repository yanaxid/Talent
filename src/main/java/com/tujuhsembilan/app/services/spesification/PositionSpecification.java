package com.tujuhsembilan.app.services.spesification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.tujuhsembilan.app.dtos.request.PositionRequestDTO;
import com.tujuhsembilan.app.models.Position;

import jakarta.persistence.criteria.Predicate;

public class PositionSpecification {

   public static Specification<Position> filter(PositionRequestDTO filter) {
      return (root, query, criteriaBuilder) -> {

         List<Predicate> predicates = new ArrayList<Predicate>();

         if (filter.getPositionName() != null) {

            predicates.add(criteriaBuilder.like(
                  criteriaBuilder.lower(root.get("positionName")),
                  "%" + filter.getPositionName().toLowerCase() + "%"));
         }

         if (filter.getStatus() != null) {

            predicates.add(criteriaBuilder.equal(
                  root.get("isActive"),
                  filter.getStatus()));
         }

         return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
      };
   }
}
