package bzh.zomzog.world.service;

import bzh.zomzog.world.domain.Project;
import bzh.zomzog.world.repository.ProjectRepository;
import bzh.zomzog.world.web.rest.dto.ProjectDTO;
import bzh.zomzog.world.web.rest.mapper.ProjectMapper;
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
 * Service Implementation for managing Project.
 */
@Service
@Transactional
public class ProjectService {

    private final Logger log = LoggerFactory.getLogger(ProjectService.class);
    
    @Inject
    private ProjectRepository projectRepository;
    
    @Inject
    private ProjectMapper projectMapper;
    
    /**
     * Save a project.
     * @return the persisted entity
     */
    public ProjectDTO save(ProjectDTO projectDTO) {
        log.debug("Request to save Project : {}", projectDTO);
        Project project = projectMapper.projectDTOToProject(projectDTO);
        project = projectRepository.save(project);
        ProjectDTO result = projectMapper.projectToProjectDTO(project);
        return result;
    }

    /**
     *  get all the projects.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Project> findAll(Pageable pageable) {
        log.debug("Request to get all Projects");
        Page<Project> result = projectRepository.findAll(pageable); 
        return result;
    }

    /**
     *  get one project by id.
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public ProjectDTO findOne(Long id) {
        log.debug("Request to get Project : {}", id);
        Project project = projectRepository.findOneWithEagerRelationships(id);
        ProjectDTO projectDTO = projectMapper.projectToProjectDTO(project);
        return projectDTO;
    }

    /**
     *  delete the  project by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete Project : {}", id);
        projectRepository.delete(id);
    }
}
