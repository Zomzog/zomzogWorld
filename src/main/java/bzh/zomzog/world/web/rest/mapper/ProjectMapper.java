package bzh.zomzog.world.web.rest.mapper;

import bzh.zomzog.world.domain.*;
import bzh.zomzog.world.web.rest.dto.ProjectDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Project and its DTO ProjectDTO.
 */
@Mapper(componentModel = "spring", uses = {TechnologyMapper.class, })
public interface ProjectMapper {

    @Mapping(source = "society.id", target = "societyId")
    ProjectDTO projectToProjectDTO(Project project);

    @Mapping(source = "societyId", target = "society")
    Project projectDTOToProject(ProjectDTO projectDTO);

    default Society societyFromId(Long id) {
        if (id == null) {
            return null;
        }
        Society society = new Society();
        society.setId(id);
        return society;
    }

    default Technology technologyFromId(Long id) {
        if (id == null) {
            return null;
        }
        Technology technology = new Technology();
        technology.setId(id);
        return technology;
    }
}
