package com.repositories;

import com.controllers.SurveyController;
import com.models.Survey;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public interface SurveyRepository extends CrudRepository<Survey,Integer> {
    Set<Survey> findByIdGreaterThanEqual(int id);


}
