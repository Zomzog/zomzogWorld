package bzh.zomzog.world.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import java.time.LocalDate;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Society.
 */
@Entity
@Table(name = "society")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Society implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min = 1, max = 254)
    @Column(name = "name", length = 254, nullable = false)
    private String name;
    
    @NotNull
    @Column(name = "enter", nullable = false)
    private LocalDate enter;
    
    @Column(name = "quit")
    private LocalDate quit;
    
    @OneToMany(mappedBy = "society")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Project> projects = new HashSet<>();

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

    public Set<Project> getProjects() {
        return projects;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Society society = (Society) o;
        if(society.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, society.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Society{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", enter='" + enter + "'" +
            ", quit='" + quit + "'" +
            '}';
    }
}
