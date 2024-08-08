// package com.tujuhsembilan.app.services.spesification;

// import org.elasticsearch.index.query.BoolQueryBuilder;
// import org.elasticsearch.index.query.QueryBuilders;
// import org.springframework.data.domain.Pageable;

// public class TalentSpecificatios {

//     public static BoolQueryBuilder buildQuery(String talentName, Boolean isActive) {
//         BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        
//         if (talentName != null && !talentName.isEmpty()) {
//             boolQuery.must(QueryBuilders.matchQuery("talent_name", talentName));
//         }

//         if (isActive != null) {
//             boolQuery.must(QueryBuilders.termQuery("is_active", isActive));
//         }

//         return boolQuery;
//     }
// }