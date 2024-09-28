package com.nrapendra.account.services;

import com.nrapendra.localdb.ApplicationData;
import com.nrapendra.localdb.ApplicationDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AccountLocalDBService {

    private final ApplicationDataRepository applicationDataRepository;

    @Transactional(propagation= Propagation.REQUIRES_NEW)
    public void saveApplicationDataToRepository(ApplicationData applicationData){
        applicationDataRepository.saveAndFlush(applicationData);
    }

}
