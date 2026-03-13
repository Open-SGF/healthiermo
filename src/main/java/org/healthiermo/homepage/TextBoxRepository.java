package org.healthiermo.homepage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TextBoxRepository extends JpaRepository<TextBox, CompositeKey> {
}
