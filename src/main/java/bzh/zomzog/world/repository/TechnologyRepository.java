package bzh.zomzog.world.repository;

import bzh.zomzog.world.domain.Technology;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Technology entity.
 */
public interface TechnologyRepository extends JpaRepository<Technology,Long> {

}
