/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Melnikov
 */
@Entity
public class History  implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private Project project;
    @OneToOne
    private Student student;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateBegin;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateEnd;

    public History() {
    }

    public History(Project project, Student student, Date dateBegin) {
        this.project = project;
        this.student = student;
        this.dateBegin = dateBegin;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 61 * hash + Objects.hashCode(this.id);
        hash = 61 * hash + Objects.hashCode(this.project);
        hash = 61 * hash + Objects.hashCode(this.student);
        hash = 61 * hash + Objects.hashCode(this.dateBegin);
        hash = 61 * hash + Objects.hashCode(this.dateEnd);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final History other = (History) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.project, other.project)) {
            return false;
        }
        if (!Objects.equals(this.student, other.student)) {
            return false;
        }
        if (!Objects.equals(this.dateBegin, other.dateBegin)) {
            return false;
        }
        if (!Objects.equals(this.dateEnd, other.dateEnd)) {
            return false;
        }
        return true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Date getDateBegin() {
        return dateBegin;
    }

    public void setDateBegin(Date dateBegin) {
        this.dateBegin = dateBegin;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

   

    @Override
    public String toString() {
        if(dateEnd == null){
          return "History{" + "project=" + project.getName() + ", student=" + student.getName()+" "+student.getSurname() + ", dateBegin=" + dateBegin.toString()+ '}';  
        }else{
          return "History{" + "project=" + project.getName() + ", student=" + student.getName()+" "+student.getSurname() + ", dateBegin=" + dateBegin.toString()+ ", dateEnd=" + dateEnd.toString()+ '}';
        }
            
    }
    
    
}
