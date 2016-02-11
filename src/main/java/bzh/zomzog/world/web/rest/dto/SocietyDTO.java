package bzh.zomzog.world.web.rest.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the Society entity.
 */
public class SocietyDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 1, max = 254)
    private String name;


    @NotNull
    private LocalDate enter;


    private LocalDate quit;


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
    public LocalDate getEnter() {
        return enter;
    }

    public void setEnter(LocalDate enter) {
        this.enter = enter;
    }
    public LocalDate getQuit() {
        return quit;
    }

    public void setQuit(LocalDate quit) {
        this.quit = quit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SocietyDTO societyDTO = (SocietyDTO) o;

        if ( ! Objects.equals(id, societyDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "SocietyDTO{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", enter='" + enter + "'" +
            ", quit='" + quit + "'" +
            '}';
    }
}
