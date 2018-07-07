package com.alauda.tobacco.web.rest;

import com.alauda.tobacco.domain.Commerce;
import com.alauda.tobacco.repository.CommerceRepository;
import com.alauda.tobacco.service.IndustryService;
import com.alauda.tobacco.web.rest.vm.ResultVM;
import com.codahale.metrics.annotation.Timed;
import com.alauda.tobacco.domain.Industry;

import com.alauda.tobacco.repository.IndustryRepository;
import com.alauda.tobacco.web.rest.errors.BadRequestAlertException;
import com.alauda.tobacco.web.rest.util.HeaderUtil;
import com.alauda.tobacco.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.inject.Inject;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;


/**
 * REST controller for managing Industry.
 */
@RestController
@RequestMapping("/api")
public class IndustryResource {

    private final Logger log = LoggerFactory.getLogger(IndustryResource.class);

    private static final String ENTITY_NAME = "industry";

    private final IndustryRepository industryRepository;
    private final CommerceRepository commerceRepository;

    @Inject
    private IndustryService industryService;

    public IndustryResource(IndustryRepository industryRepository, CommerceRepository commerceRepository) {
        this.industryRepository = industryRepository;
        this.commerceRepository = commerceRepository;
    }

    /**
     * POST  /industries : Create a new industry.
     *
     * @param industry the industry to create
     * @return the ResponseEntity with status 201 (Created) and with body the new industry, or with status 400 (Bad Request) if the industry has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/industries")
    @Timed
    public ResponseEntity<?> createIndustry(@RequestBody Industry industry) throws URISyntaxException {
        log.debug("REST request to save Industry : {}", industry);
        if (industry.getId() != null) {
            throw new BadRequestAlertException("A new industry cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Industry industry1 = null;
        try {
            industry1 = industryService.createIndustry(industry, "create");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(industry1, HttpStatus.OK);
//        Industry result = industryRepository.save(industry);
//        return ResponseEntity.created(new URI("/api/industries/" + result.getId()))
//            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
//            .body(result);
    }

    /**
     * PUT  /industries : Updates an existing industry.
     *
     * @param industry the industry to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated industry,
     * or with status 400 (Bad Request) if the industry is not valid,
     * or with status 500 (Internal Server Error) if the industry couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/industries")
    @Timed
    public ResponseEntity<?> updateIndustry(@RequestBody Industry industry) throws URISyntaxException {
        log.debug("REST request to update Industry : {}", industry);
        if (industry.getId() == null) {
            return createIndustry(industry);
        }
        Industry result = industryRepository.save(industry);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, industry.getId().toString()))
            .body(result);
    }

    /**
     * GET  /industries : get all the industries.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of industries in body
     */
    @GetMapping("/industries")
    @Timed
    public ResponseEntity<?> getAllIndustries(Pageable pageable) throws IOException {
        log.debug("REST request to get a page of Industries");
        Page<Industry> page = industryRepository.findAll(pageable);
        for(Industry industry : page) {
            File file = new File("qrCodeDir/code" + industry.getId() + ".png");
            if (file.exists()) {
                BufferedImage image = ImageIO.read(file);
                ByteArrayOutputStream os=new ByteArrayOutputStream();
                ImageIO.write(image, "png", os);
                Base64 base64 = new Base64();
                String base64Img = new String(base64.encode(os.toByteArray()));
                industry.setQrcode(base64Img);
            }
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/industries");
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    /**
     * GET  /industries/:id : get the "id" industry.
     *
     * @param id the id of the industry to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the industry, or with status 404 (Not Found)
     */
//    @GetMapping("/industries/{id}")
//    @Timed
//    public ResponseEntity<Industry> getIndustry(@PathVariable Long id) {
//        log.debug("REST request to get Industry : {}", id);
//        Industry industry = industryRepository.findOne(id);
//        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(industry));
//    }

    @GetMapping("/industries/{id}")
    @Timed
    public ResponseEntity<?> getIndustry(@PathVariable Long id) {
        log.debug("REST request to get Industry : {}", id);
        Industry industry = industryRepository.findOne(id);
        ResultVM resultVM = new ResultVM();
        resultVM.setName(industry.getName());
        resultVM.setManufacturer(industry.getManufacturer());
        resultVM.setDate(industry.getDate());
        resultVM.setDesc(industry.getDesc());
        List<Commerce> commerces = commerceRepository.findByIndustry(industry);
        resultVM.setCommerces(commerces);
//        return ResponseUtil.wrapOrNotFound(Optional.of(resultVM));
        return new ResponseEntity<>(resultVM, HttpStatus.OK);
    }

    /**
     * DELETE  /industries/:id : delete the "id" industry.
     *
     * @param id the id of the industry to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/industries/{id}")
    @Timed
    public ResponseEntity<Void> deleteIndustry(@PathVariable Long id) {
        log.debug("REST request to delete Industry : {}", id);
        industryRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
