package com.example.IncidentSubmissionService.daoImpl;

import com.example.IncidentSubmissionService.dao.IncidentSubmissionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;

public class IncidentSubmissionDaoImpl implements IncidentSubmissionDao {

    @Autowired
    MongoTemplate mongoTemplate;

    @Override // TODO : Pending to Impl into get unique value
    public List<String> getAllUniqueValuesByField(String fieldName) {

        Criteria criteria = new Criteria();
        criteria.and( fieldName).exists(true);

        List<AggregationOperation> aggregationOperations = new ArrayList<>();
        aggregationOperations.add(new MatchOperation(criteria));
        aggregationOperations.add(Aggregation.group().addToSet( fieldName).as("values"));

        Aggregation aggregation = newAggregation(aggregationOperations);
        AggregationResults<Object> groupResults = mongoTemplate.aggregate(aggregation, "incidents",Object.class);

        if (CollectionUtils.isEmpty(groupResults.getMappedResults()))
            return Collections.emptyList();

        LinkedHashMap map = (LinkedHashMap) groupResults.getUniqueMappedResult();
        return (List<String>) map.get("values");
    }
}
