package com.tujuhsembilan.app.services.spesification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.tujuhsembilan.app.dtos.request.PositionRequestDTO;
import com.tujuhsembilan.app.models.Position;

import jakarta.persistence.criteria.Predicate;

public class PositionSpecification {

   public static Specification<Position> filter(PositionRequestDTO positionRequestDTO) {
      return (root, query, criteriaBuilder) -> {

         List<Predicate> predicates = new ArrayList<Predicate>();

         if (positionRequestDTO.getPositionName() != null) {

            predicates.add(criteriaBuilder.like(
                  criteriaBuilder.lower(root.get("positionName")),
                  "%" + positionRequestDTO.getPositionName().toLowerCase() + "%"));
         }

         if (positionRequestDTO.getStatus() != null) {

            predicates.add(criteriaBuilder.equal(
                  root.get("isActive"),
                  positionRequestDTO.getStatus()));
         }

         return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
      };
   }
}
