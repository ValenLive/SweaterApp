package com.example.sweater.repos;

import com.example.sweater.domain.Message;
import org.springframework.data.repository.CrudRepository;

import java.util.Comparator;
import java.util.List;


/**
 * Репа позволяє получити повний список полей, або найти по ідентифікатору
 */
public interface MessageRepo extends CrudRepository<Message, Long> {
    List<Message> findByTag(String tag);//default Spring JPA method

    /**
     * Sorting by ID
     */

    default void sortById(List<Message> list){
        list.sort(Comparator.comparing(Message::getId).reversed());
    }

    default void sortByIdAsc(List<Message> list){
        list.sort(Comparator.comparing(Message::getId));
    }
}
