package edu.baylor.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "team")
public class Team {

    @Id
    @SequenceGenerator(
            name = "team_sequence",
            allocationSize = 1,
            initialValue = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "car_sequence")
    @Column(name = "id", updatable = false, nullable = false)
    protected Long id;

    @Size(min = 1, max = 50)
    @Column(name = "name", length = 50, nullable = false, unique = true)
    protected String name;

    @Size(min = 1, max = 50)
    @Column(name = "skill", length = 50)
    protected String skill;

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "leader_id", unique = true)
    protected Person leader;

    @OneToMany(mappedBy = "team",cascade = CascadeType.ALL)
    protected List<Person> members;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "preferred_opponent")
    protected Team preferredOpponent;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public Person getLeader() {
        return leader;
    }

    public void setLeader(Person leader) {
        this.leader = leader;
    }

    public List<Person> getMembers() {
        return members;
    }

    public void setMembers(List<Person> members) {
        this.members = members;
    }

    public Team getPreferredOpponent() {
        return preferredOpponent;
    }

    public void setPreferredOpponent(Team preferredOpponent) {
        this.preferredOpponent = preferredOpponent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Team team = (Team) o;
        return id.equals(team.id) &&
                name.equals(team.name) &&
                Objects.equals(skill, team.skill) &&
                leader.equals(team.leader) &&
                Objects.equals(members, team.members) &&
                Objects.equals(preferredOpponent, team.preferredOpponent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, skill, leader, members, preferredOpponent);
    }
}
