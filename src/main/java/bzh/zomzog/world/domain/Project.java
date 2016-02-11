package bzh.zomzog.world.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import bzh.zomzog.world.domain.enumeration.Role;

import bzh.zomzog.world.domain.enumeration.Techno;

/**
 * A Project.
 */
@Entity
@Table(name = "project")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Project implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min = 1, max = 254)
    @Column(name = "client", length = 254, nullable = false)
    private String client;
    
    @NotNull
    @Min(value = 1)
    @Column(name = "team_size", nullable = false)
    private Integer teamSize;
    
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "technos")
    private Techno technos;
    
    @Size(max = 2048)
    @Column(name = "description", length = 2048)
    private String description;
    
    @ManyToOne
    @JoinColumn(name = "society_id")
    private Society society;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "project_technologie",
               joinColumns = @JoinColumn(name="projects_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="technologies_id", referencedColumnName="ID"))
    private Set<Technology> technologies = new HashSet<>();

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

    public Techno getTechnos() {
        return technos;
    }
    
    public void setTechnos(Techno technos) {
        this.technos = technos;
    }

    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }

    public Society getSociety() {
        return society;
    }

    public void setSociety(Society society) {
        this.society = society;
    }

    public Set<Technology> getTechnologies() {
        return technologies;
    }

    public void setTechnologies(Set<Technology> technologys) {
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
        Project project = (Project) o;
        if(project.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, project.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Project{" +
            "id=" + id +
            ", client='" + client + "'" +
            ", teamSize='" + teamSize + "'" +
            ", role='" + role + "'" +
            ", technos='" + technos + "'" +
            ", description='" + description + "'" +
            '}';
    }
}
