package lin.louis.poc.demospringrestdocs;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.resourceDetails;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.subsectionWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


@SpringBootTest
@ExtendWith({ RestDocumentationExtension.class, SpringExtension.class })
class PersonAPITest {

	private MockMvc mockMvc;

	@Autowired
	private PersonRepository personRepository;

	@BeforeEach
	void setUp(WebApplicationContext webApplicationContext,
			RestDocumentationContextProvider restDocumentationContextProvider) {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
				.apply(documentationConfiguration(restDocumentationContextProvider))
				.build();
	}

	@Test
	void personGetAll() throws Exception {
		personRepository.deleteAll();
		personRepository.save(new Person("Foo", "Bar"));
		personRepository.save(new Person("Louis", "Lin"));

		// Use org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders in order to have the
		// `urlTemplate` available in the snippets.
		// See https://github.com/ePages-de/restdocs-api-spec#usage-with-spring-rest-docs
		mockMvc.perform(get("/persons").contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(status().isOk())
				// Use com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper instead in order to be generate the
				// OpenAPI spec. See: https://github.com/ePages-de/restdocs-api-spec#mockmvc-based-tests
				.andDo(document("{methodName}", resourceDetails().description("Get all persons"),
						links(
								linkWithRel("self").description("Canonical link for this resource"),
								linkWithRel("profile").description("The ALPS profile for this resource")
						), responseFields(
								subsectionWithPath("_embedded.persons").description(
										"An array of <<resources-persons, Person resources>>"),
								subsectionWithPath("_links").description(
										"Links to navigating the API"))
						)
				);
	}
}
