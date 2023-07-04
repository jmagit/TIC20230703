package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.domains.contracts.repositories.ActorRepository;
import com.example.domains.entities.Actor;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Autowired
	ActorRepository dao;
	
	@Override
	public void run(String... args) throws Exception {
		System.out.println("Aplicacion arrancada");
		var actor = new Actor(0, " ", "Grillo");
			dao.save(actor);
//		var leido = dao.findById(211);
//		if(leido.isPresent()) {
//			actor = leido.get();
//			actor.setFirstName(actor.getFirstName().toUpperCase());
//			dao.save(actor);
//		}
//		dao.deleteById(211);
		dao.findAll().forEach(System.out::println);
	}

}
