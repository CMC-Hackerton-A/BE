package com.example.neodinary_hackaton.domain.memorial.repository;

import com.example.neodinary_hackaton.domain.memorial.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
}
