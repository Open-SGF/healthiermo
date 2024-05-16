package org.healthiermo.homepage;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TextBoxRepository extends CrudRepository<TextBox, CompositeKey> {

}
