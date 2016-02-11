package bzh.zomzog.world.web.rest.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;


/**
 * A DTO for the Technology entity.
 */
public class TechnologyDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 1)
    private String name;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TechnologyDTO technologyDTO = (TechnologyDTO) o;

        if ( ! Objects.equals(id, technologyDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TechnologyDTO{" +
            "id=" + id +
            ", name='" + name + "'" +
            '}';
    }
}
