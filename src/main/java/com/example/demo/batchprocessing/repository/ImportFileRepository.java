package com.example.demo.batchprocessing.repository;

import com.example.demo.batchprocessing.entity.ImportFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImportFileRepository extends JpaRepository<ImportFile, Integer> {

}
