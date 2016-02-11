package bzh.zomzog.world.service;

import bzh.zomzog.world.domain.Society;
import bzh.zomzog.world.repository.SocietyRepository;
import bzh.zomzog.world.web.rest.dto.SocietyDTO;
import bzh.zomzog.world.web.rest.mapper.SocietyMapper;
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
 * Service Implementation for managing Society.
 */
@Service
@Transactional
public class SocietyService {

    private final Logger log = LoggerFactory.getLogger(SocietyService.class);
    
    @Inject
    private SocietyRepository societyRepository;
    
    @Inject
    private SocietyMapper societyMapper;
    
    /**
     * Save a society.
     * @return the persisted entity
     */
    public SocietyDTO save(SocietyDTO societyDTO) {
        log.debug("Request to save Society : {}", societyDTO);
        Society society = societyMapper.societyDTOToSociety(societyDTO);
        society = societyRepository.save(society);
        SocietyDTO result = societyMapper.societyToSocietyDTO(society);
        return result;
    }

    /**
     *  get all the societys.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Society> findAll(Pageable pageable) {
        log.debug("Request to get all Societys");
        Page<Society> result = societyRepository.findAll(pageable); 
        return result;
    }

    /**
     *  get one society by id.
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public SocietyDTO findOne(Long id) {
        log.debug("Request to get Society : {}", id);
        Society society = societyRepository.findOne(id);
        SocietyDTO societyDTO = societyMapper.societyToSocietyDTO(society);
        return societyDTO;
    }

    /**
     *  delete the  society by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete Society : {}", id);
        societyRepository.delete(id);
    }
}
