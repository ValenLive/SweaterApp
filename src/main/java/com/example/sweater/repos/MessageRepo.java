package com.example.sweater.repos;

import com.example.sweater.domain.Message;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


/**
 * Репа позволяє получити повний список полей, або найти по ідентифікатору
 */
public interface MessageRepo extends CrudRepository<Message, Long> {
    List<Message> findByTag(String tag);//default Spring JPA method
}
