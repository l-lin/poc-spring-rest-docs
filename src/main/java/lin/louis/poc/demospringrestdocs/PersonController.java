package lin.louis.poc.demospringrestdocs;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/person")
public class PersonController {

	private static final List<PersonDTO> PERSONS = Arrays.asList(
			new PersonDTO("Foo", "Bar"),
			new PersonDTO("Louis", "Lin")
	);

	@GetMapping
	public List<PersonDTO> getAll() {
		return PERSONS;
	}
}
