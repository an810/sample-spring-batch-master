package com.example.demo.batchprocessing.repository;

import com.example.demo.batchprocessing.entity.ImportFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImportFileRepository extends JpaRepository<ImportFile, Integer> {

}
