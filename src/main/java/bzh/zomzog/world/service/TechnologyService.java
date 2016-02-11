package bzh.zomzog.world.service;

import bzh.zomzog.world.domain.Technology;
import bzh.zomzog.world.repository.TechnologyRepository;
import bzh.zomzog.world.web.rest.dto.TechnologyDTO;
import bzh.zomzog.world.web.rest.mapper.TechnologyMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Technology.
 */
@Service
@Transactional
public class TechnologyService {

    private final Logger log = LoggerFactory.getLogger(TechnologyService.class);
    
    @Inject
    private TechnologyRepository technologyRepository;
    
    @Inject
    private TechnologyMapper technologyMapper;
    
    /**
     * Save a technology.
     * @return the persisted entity
     */
    public TechnologyDTO save(TechnologyDTO technologyDTO) {
        log.debug("Request to save Technology : {}", technologyDTO);
        Technology technology = technologyMapper.technologyDTOToTechnology(technologyDTO);
        technology = technologyRepository.save(technology);
        TechnologyDTO result = technologyMapper.technologyToTechnologyDTO(technology);
        return result;
    }

    /**
     *  get all the technologys.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Technology> findAll(Pageable pageable) {
        log.debug("Request to get all Technologys");
        Page<Technology> result = technologyRepository.findAll(pageable); 
        return result;
    }

    /**
     *  get one technology by id.
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public TechnologyDTO findOne(Long id) {
        log.debug("Request to get Technology : {}", id);
        Technology technology = technologyRepository.findOne(id);
        TechnologyDTO technologyDTO = technologyMapper.technologyToTechnologyDTO(technology);
        return technologyDTO;
    }

    /**
     *  delete the  technology by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete Technology : {}", id);
        technologyRepository.delete(id);
    }
}
