package com.alauda.tobacco.repository;

import com.alauda.tobacco.domain.Commerce;
import com.alauda.tobacco.domain.Industry;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the Commerce entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CommerceRepository extends JpaRepository<Commerce, Long> {

    List<Commerce> findByIndustry(Industry industry);
}
