package bzh.zomzog.world.repository;

import bzh.zomzog.world.domain.Society;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Society entity.
 */
public interface SocietyRepository extends JpaRepository<Society,Long> {

}
