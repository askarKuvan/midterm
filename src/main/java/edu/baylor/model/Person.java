/***************************************
 *
 * Author       : Askar Kuvanychbekov
 * Assignment   : II - mini app
 * Class        : CSI 5354
 *
 ***************************************/

package edu.baylor.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "person")
@NamedQueries({
        @NamedQuery(name = "Person.findAll", query = "SELECT p from Person p")
})
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Person {

    @Id
    @SequenceGenerator(
            name = "person_sequence",
            allocationSize = 1,
            initialValue = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "person_sequence")
    @Column(name = "id", updatable = false, nullable = false)
    protected Long id;

    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "name", length = 50, nullable = false)
    protected String name;


    @Column(name = "dob")
    protected LocalDateTime dateOfBirth;

    @JsonIgnore
    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "team")
    protected Team team;

    @JsonIgnore
    @OneToOne(mappedBy = "leader")
    protected Team ledTeam;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDateTime dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Team getLedTeam() {
        return ledTeam;
    }

    public void setLedTeam(Team ledTeam) {
        this.ledTeam = ledTeam;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return id.equals(person.id) &&
                name.equals(person.name) &&
                Objects.equals(dateOfBirth, person.dateOfBirth) &&
                Objects.equals(team, person.team);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, dateOfBirth, team);
    }

}
