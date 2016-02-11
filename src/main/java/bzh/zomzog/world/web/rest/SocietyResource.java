package bzh.zomzog.world.web.rest;

import com.codahale.metrics.annotation.Timed;
import bzh.zomzog.world.domain.Society;
import bzh.zomzog.world.service.SocietyService;
import bzh.zomzog.world.web.rest.util.HeaderUtil;
import bzh.zomzog.world.web.rest.util.PaginationUtil;
import bzh.zomzog.world.web.rest.dto.SocietyDTO;
import bzh.zomzog.world.web.rest.mapper.SocietyMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing Society.
 */
@RestController
@RequestMapping("/api")
public class SocietyResource {

    private final Logger log = LoggerFactory.getLogger(SocietyResource.class);
        
    @Inject
    private SocietyService societyService;
    
    @Inject
    private SocietyMapper societyMapper;
    
    /**
     * POST  /societys -> Create a new society.
     */
    @RequestMapping(value = "/societys",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SocietyDTO> createSociety(@Valid @RequestBody SocietyDTO societyDTO) throws URISyntaxException {
        log.debug("REST request to save Society : {}", societyDTO);
        if (societyDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("society", "idexists", "A new society cannot already have an ID")).body(null);
        }
        SocietyDTO result = societyService.save(societyDTO);
        return ResponseEntity.created(new URI("/api/societys/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("society", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /societys -> Updates an existing society.
     */
    @RequestMapping(value = "/societys",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SocietyDTO> updateSociety(@Valid @RequestBody SocietyDTO societyDTO) throws URISyntaxException {
        log.debug("REST request to update Society : {}", societyDTO);
        if (societyDTO.getId() == null) {
            return createSociety(societyDTO);
        }
        SocietyDTO result = societyService.save(societyDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("society", societyDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /societys -> get all the societys.
     */
    @RequestMapping(value = "/societys",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<List<SocietyDTO>> getAllSocietys(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Societys");
        Page<Society> page = societyService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/societys");
        return new ResponseEntity<>(page.getContent().stream()
            .map(societyMapper::societyToSocietyDTO)
            .collect(Collectors.toCollection(LinkedList::new)), headers, HttpStatus.OK);
    }

    /**
     * GET  /societys/:id -> get the "id" society.
     */
    @RequestMapping(value = "/societys/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SocietyDTO> getSociety(@PathVariable Long id) {
        log.debug("REST request to get Society : {}", id);
        SocietyDTO societyDTO = societyService.findOne(id);
        return Optional.ofNullable(societyDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /societys/:id -> delete the "id" society.
     */
    @RequestMapping(value = "/societys/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteSociety(@PathVariable Long id) {
        log.debug("REST request to delete Society : {}", id);
        societyService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("society", id.toString())).build();
    }
}
