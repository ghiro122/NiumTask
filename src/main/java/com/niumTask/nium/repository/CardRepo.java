package com.niumTask.nium.repository;

import com.niumTask.nium.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepo extends JpaRepository<Card, Long> {
}
