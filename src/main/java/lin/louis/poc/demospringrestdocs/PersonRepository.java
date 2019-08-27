package lin.louis.poc.demospringrestdocs;

import org.springframework.data.repository.CrudRepository;


public interface PersonRepository extends CrudRepository<Person, Long> {
}
