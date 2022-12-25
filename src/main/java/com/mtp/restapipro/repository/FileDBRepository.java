package com.mtp.restapipro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mtp.restapipro.models.FileDB;
@Repository
public interface FileDBRepository extends JpaRepository<FileDB, String> {

}
