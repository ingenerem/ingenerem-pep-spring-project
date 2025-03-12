package com.example.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Message;

/*This is a Repository class for Message database entity */
@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {

    List<Message> findAllBypostedBy(int user_id);
}
