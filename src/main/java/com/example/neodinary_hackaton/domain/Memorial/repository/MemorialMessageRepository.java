package com.example.neodinary_hackaton.domain.Memorial.repository;

import com.example.neodinary_hackaton.domain.Memorial.entity.MemorialMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemorialMessageRepository extends JpaRepository<MemorialMessage, Long> {
}
