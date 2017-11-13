package net.portrix.meld.importer;

import net.portrix.meld.social.Education;
import net.portrix.meld.social.School;
import net.portrix.meld.usercontrol.User;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.time.LocalDate;

@ApplicationScoped
public class ProfileImport {

    @Inject
    EntityManager entityManager;

    @Transactional
    public void execute() {

        User patrick = entityManager.createNamedQuery("findUser", User.class)
                .setParameter("name", "patrick")
                .getSingleResult();

        Education education = new Education();
        education.setUser(patrick);
        entityManager.persist(education);

        School patrickSchool = new School();
        patrickSchool.setCourse("Angwandte Informatik");
        patrickSchool.setName("HAW Hamburg");
        patrickSchool.setStart(LocalDate.of(2007, 8, 1));
        patrickSchool.setEnd(LocalDate.of(2013, 8,1));
        education.getSchools().add(patrickSchool);
        entityManager.persist(patrickSchool);
    }

}
