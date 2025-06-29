package com.hrowlxb.passy_backend.site.repository;

import com.hrowlxb.passy_backend.site.domain.SavedSite;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface SavedSiteRepository extends MongoRepository<SavedSite, String> {
    List<SavedSite> findAllByEmail(String email);
}
