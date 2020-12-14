package com.project.repo;

import com.project.model.Woogle;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WoogleRepository extends JpaRepository<Woogle, Integer>{
    List<Woogle> findByUserId(Long userId);
}