package com.example.application.resources;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import jakarta.websocket.server.PathParam;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.domains.contracts.services.ActorService;
import com.example.domains.entities.dtos.ActorDTO;
import com.example.domains.entities.dtos.ActorShort;
import com.example.domains.entities.dtos.PeliculasShortDTO;
import com.example.exceptions.*;

import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/actores/v1")
public class ActorResource {
	@Autowired
	private ActorService srv;

	@GetMapping
	public List<ActorDTO> getAll() {
		return srv.getByProjection(ActorDTO.class);
	}

	@GetMapping(params = "page")
	public Page<ActorDTO> getAll(Pageable pageable) {
		return srv.getByProjection(pageable, ActorDTO.class);
	}

	@GetMapping(params = "mode=short")
	public List<ActorShort> getAllShort() {
		return srv.getByProjection(ActorShort.class);
	}

	@GetMapping(path = "/{id}")
	public ActorDTO getOne(@PathVariable int id) throws NotFoundException {
		var item = srv.getOne(id);
		if(item.isEmpty())
			throw new NotFoundException();
		return ActorDTO.from(item.get());
	}
	
	@Transactional
	@GetMapping(path = "/{id}/pelis")
	public List<PeliculasShortDTO> getPelis(@PathVariable int id) throws NotFoundException {
		var item = srv.getOne(id);
		if(item.isEmpty())
			throw new NotFoundException();
		return item.get().getFilmActors().stream()
				.map(a -> new PeliculasShortDTO(a.getFilm().getFilmId(), a.getFilm().getTitle()))
				.toList();
	}
	
	@PostMapping
	public ResponseEntity<Object> create(@Valid @RequestBody ActorDTO item) throws BadRequestException, DuplicateKeyException, InvalidDataException {
		var newItem = srv.add(ActorDTO.from(item));
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
			.buildAndExpand(newItem.getActorId()).toUri();
		return ResponseEntity.created(location).build();

	}

	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void update(@PathVariable int id, @Valid @RequestBody ActorDTO item) throws BadRequestException, NotFoundException, InvalidDataException {
		if(id != item.getActorId())
			throw new BadRequestException("No coinciden los identificadores");
		srv.modify(ActorDTO.from(item));
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable int id) {
		srv.deleteById(id);
	}
}
