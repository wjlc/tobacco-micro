package com.alauda.tobacco.web.rest;

import com.alauda.tobacco.domain.Industry;
import com.alauda.tobacco.repository.IndustryRepository;
import com.alauda.tobacco.service.IndustryService;
import com.alauda.tobacco.web.rest.vm.CommerceVM;
import com.codahale.metrics.annotation.Timed;
import com.alauda.tobacco.domain.Commerce;

import com.alauda.tobacco.repository.CommerceRepository;
import com.alauda.tobacco.web.rest.errors.BadRequestAlertException;
import com.alauda.tobacco.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Commerce.
 */
@RestController
@RequestMapping("/api")
public class CommerceResource {

    private final Logger log = LoggerFactory.getLogger(CommerceResource.class);

    private static final String ENTITY_NAME = "commerce";

    private final IndustryRepository industryRepository;
    private final CommerceRepository commerceRepository;

    @Inject
    private IndustryService industryService;

    public CommerceResource(IndustryRepository industryRepository, CommerceRepository commerceRepository) {
        this.industryRepository = industryRepository;
        this.commerceRepository = commerceRepository;
    }

    /**
     * POST  /commerce : Create a new commerce.
     *
     * @param commerceVM the commerce to create
     * @return the ResponseEntity with status 201 (Created) and with body the new commerce, or with status 400 (Bad Request) if the commerce has already an ID
     */
    @PostMapping("/commerce")
    @Timed
    public ResponseEntity<?> createCommerce(@RequestBody CommerceVM commerceVM) throws IOException {
        log.debug("REST request to save Commerce : {}", commerceVM);
        Commerce commerce = new Commerce();
        if (commerceVM.getCommpanyname() != null) {
            commerce.setCompanyname(commerceVM.getCommpanyname());
        }

        if (commerceVM.getRetailname() != null) {
            commerce.setRetailname(commerceVM.getRetailname());
        }

        if (commerceVM.getIndustryId() != null) {
            Industry industry = industryRepository.findOne(commerceVM.getIndustryId());
            commerce.setIndustry(industry);
            Commerce result = commerceRepository.save(commerce);
            industryService.createImg(industry, commerceRepository.findByIndustry(industry));

            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Industry id is null", HttpStatus.BAD_REQUEST);
        }

//        return ResponseEntity.created(new URI("/api/commerce/" + result.getId()))
//            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
//            .body(result);
    }

    /**
     * PUT  /commerce : Updates an existing commerce.
     *
     * @param commerce the commerce to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated commerce,
     * or with status 400 (Bad Request) if the commerce is not valid,
     * or with status 500 (Internal Server Error) if the commerce couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
//    @PutMapping("/commerce")
//    @Timed
//    public ResponseEntity<Commerce> updateCommerce(@RequestBody CommerceVM commerceVM) throws URISyntaxException {
//        log.debug("REST request to update Commerce : {}", commerceVM);
//        if (commerceVM.getId() == null) {
//            return createCommerce(commerce);
//        }
//        Commerce result = commerceRepository.save(commerce);
//        return ResponseEntity.ok()
//            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, commerce.getId().toString()))
//            .body(result);
//    }

    /**
     * GET  /commerce : get all the commerce.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of commerce in body
     */
    @GetMapping("/commerce")
    @Timed
    public List<Commerce> getAllCommerce() {
        log.debug("REST request to get all Commerce");
        return commerceRepository.findAll();
        }

    /**
     * GET  /commerce/:id : get the "id" commerce.
     *
     * @param id the id of the commerce to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the commerce, or with status 404 (Not Found)
     */
    @GetMapping("/commerce/{id}")
    @Timed
    public ResponseEntity<Commerce> getCommerce(@PathVariable Long id) {
        log.debug("REST request to get Commerce : {}", id);
        Commerce commerce = commerceRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(commerce));
    }

    /**
     * DELETE  /commerce/:id : delete the "id" commerce.
     *
     * @param id the id of the commerce to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/commerce/{id}")
    @Timed
    public ResponseEntity<Void> deleteCommerce(@PathVariable Long id) {
        log.debug("REST request to delete Commerce : {}", id);
        commerceRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
