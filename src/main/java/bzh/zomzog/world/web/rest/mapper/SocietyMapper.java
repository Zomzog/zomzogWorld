package bzh.zomzog.world.web.rest.mapper;

import bzh.zomzog.world.domain.*;
import bzh.zomzog.world.web.rest.dto.SocietyDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Society and its DTO SocietyDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SocietyMapper {

    SocietyDTO societyToSocietyDTO(Society society);

    @Mapping(target = "projects", ignore = true)
    Society societyDTOToSociety(SocietyDTO societyDTO);
}
