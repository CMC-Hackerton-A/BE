package com.example.neodinary_hackaton.domain.Memorial.repository;

import com.example.neodinary_hackaton.domain.Memorial.entity.MemorialMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemorialMessageRepository extends JpaRepository<MemorialMessage, Long> {
    List<MemorialMessage> findAllByOrderByCreatedAtDesc();
}
