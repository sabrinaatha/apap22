package id.ac.ui.cs.eaap.lab.repository;

import id.ac.ui.cs.eaap.lab.model.LastContactModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LastContactDb extends JpaRepository<LastContactModel, Long> {
}
