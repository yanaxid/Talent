package com.tujuhsembilan.app.dtos.request;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomPageRequest {

   private String sort;
   private Integer size;
   private Integer page;

   public Pageable getPage( String defaultSort) {
      int pageNumberValue = (page !=  null) ? (page < 1) ? 1 : page : 1;
      int pageSizeValue = size != null ? size < 1 ? 1 : size : 10;
      Sort sortBy = Sort.by(Direction.ASC, defaultSort);

      if (sort != null) {
         String[] parts = sort.split(",");
         String sortField = parts[0];
         String sortOrder = parts.length > 1 ? parts[1] : "ASC";
         sortBy = Sort.by(Sort.Direction.fromString(sortOrder), sortField);
      }

      return PageRequest.of(pageNumberValue - 1, pageSizeValue, sortBy);
   }
}

