package bzh.zomzog.world.web.rest.mapper;

import bzh.zomzog.world.domain.*;
import bzh.zomzog.world.web.rest.dto.TechnologyDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Technology and its DTO TechnologyDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TechnologyMapper {

    TechnologyDTO technologyToTechnologyDTO(Technology technology);

    Technology technologyDTOToTechnology(TechnologyDTO technologyDTO);
}
