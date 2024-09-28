package com.nrapendra.localdb;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationDataRepository extends JpaRepository<ApplicationData,Long> {
}
