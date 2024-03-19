package com.example.demo.batchprocessing.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="file_import")
@Getter
@Setter
public class ImportFile {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    private String fileName;
}
