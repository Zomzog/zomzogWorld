package bzh.zomzog.world.web.rest.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import bzh.zomzog.world.domain.enumeration.Role;

/**
 * A DTO for the Project entity.
 */
public class ProjectDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 1, max = 254)
    private String client;


    @NotNull
    @Min(value = 1)
    private Integer teamSize;


    @NotNull
    private Role role;


    @Size(max = 2048)
    private String description;


    private Long societyId;
    private Set<TechnologyDTO> technologies = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }
    public Integer getTeamSize() {
        return teamSize;
    }

    public void setTeamSize(Integer teamSize) {
        this.teamSize = teamSize;
    }
    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getSocietyId() {
        return societyId;
    }

    public void setSocietyId(Long societyId) {
        this.societyId = societyId;
    }
    public Set<TechnologyDTO> getTechnologies() {
        return technologies;
    }

    public void setTechnologies(Set<TechnologyDTO> technologys) {
        this.technologies = technologys;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProjectDTO projectDTO = (ProjectDTO) o;

        if ( ! Objects.equals(id, projectDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ProjectDTO{" +
            "id=" + id +
            ", client='" + client + "'" +
            ", teamSize='" + teamSize + "'" +
            ", role='" + role + "'" +
            ", description='" + description + "'" +
            '}';
    }
}
