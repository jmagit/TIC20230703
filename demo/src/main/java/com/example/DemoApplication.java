package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.example.domains.contracts.repositories.ActorRepository;
import com.example.domains.entities.Actor;

import jakarta.transaction.Transactional;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Autowired
	ActorRepository dao;
	
	@Override
//	@Transactional
	public void run(String... args) throws Exception {
		System.out.println("Aplicacion arrancada");
//		var actor = new Actor(0, "Pepito", "Grillo");
//		dao.save(actor);
//		var leido = dao.findById(211);
//		if(leido.isPresent()) {
//			actor = leido.get();
//			actor.setFirstName(actor.getFirstName().toUpperCase());
//			dao.save(actor);
//		}
//		dao.deleteById(211);
//		dao.findAll().forEach(System.out::println);
//		dao.findTop5ByFirstNameIgnoreCaseStartingWithOrderByLastNameDesc("p").forEach(System.out::println);
//		dao.findByActorIdBetween(5, 10, Sort.unsorted() /* Sort.by("actorId").descending()*/).forEach(System.out::println);
//		dao.findByJPQL(5).forEach(System.out::println);
//		dao.findBySQL(5).forEach(System.out::println);
//		dao.findAll((root, query, builder) -> builder.lessThan(root.get("actorId"), 5)).forEach(System.out::println);
//		dao.findAll((root, query, builder) -> builder.like(root.get("lastName"), "PE%")).forEach(System.out::println);
//		int page = 1, rows = 20;
//		dao.findAll(PageRequest.of(page, rows, Sort.by("actorId"))).forEach(System.out::println);
//		var leido = dao.findById(1);
//		if(leido.isPresent()) {
//			var actor = leido.get();
//			System.out.println(actor);
//			actor.getFilmActors().forEach(item -> System.out.println(item.getFilm()));
//		}
//		int page = 1, rows = 10;
//		dao.findAll(PageRequest.of(page, rows, Sort.by("actorId"))).forEach(
//				item -> System.out.println(item.getActorId() + ": " + item.getFilmActors().size())
//		);
//		try {
//			trans();
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//		}
		dao.findAll((root, query, builder) -> builder.greaterThan(root.get("actorId"), 200)).forEach(System.out::println);
	}

	@Transactional
	private void trans() {
		var actor = new Actor(0, "   ", "Grillo");
		dao.save(actor);
		actor = new Actor(0, "Carmelo", "Coton");
		dao.save(actor);
		dao.deleteById(1);

	}
}
