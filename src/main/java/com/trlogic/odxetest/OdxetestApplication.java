package com.trlogic.odxetest;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@SpringBootApplication
@RestController
public class OdxetestApplication {
	
	private static final Logger log = LoggerFactory.getLogger(OdxetestApplication.class);

	@Autowired
	private CustomerRepository customerRepository;

	public static void main(String[] args) {
		SpringApplication.run(OdxetestApplication.class, args);
	}
	@GetMapping("/customers")
	public List<Customer> retrieveAllCustomers() {
		return customerRepository.findAll();
	}

	@GetMapping("/customers/{id}")
	public Customer retrieveCustomer(@PathVariable long id) {
		Optional<Customer> customer = customerRepository.findById(id);

		if (!customer.isPresent())
			throw new CustomerNotFoundException("id-" + id);

		return customer.get();
	}

	@DeleteMapping("/customers/{id}")
	public void deleteCustomer(@PathVariable long id) {
		customerRepository.deleteById(id);
	}

	@PostMapping("/customers")
	public ResponseEntity<Object> createCustomer(@RequestBody Customer customer) {
		Customer savedCustomer = customerRepository.save(customer);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(savedCustomer.getId()).toUri();

		return ResponseEntity.created(location).build();

	}
	
	@PutMapping("/customers/{id}")
	public ResponseEntity<Object> updateCustomer(@RequestBody Customer customer, @PathVariable long id) {

		Optional<Customer> customerOptional = customerRepository.findById(id);

		if (!customerOptional.isPresent())
			return ResponseEntity.notFound().build();

		customer.setId(id);
		
		customerRepository.save(customer);

		return ResponseEntity.noContent().build();
	}
}
