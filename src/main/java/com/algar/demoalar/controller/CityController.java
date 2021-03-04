package com.algar.demoalar.controller;

import com.algar.demoalar.entities.City;
import com.algar.demoalar.services.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/cities")
public class CityController {

    @Autowired
    private CityService cityService;

    @PostMapping
    public ResponseEntity<Void> insert(@Valid @RequestBody City obj){
        obj = cityService.insert(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<City> findById(@PathVariable Integer id){
        City obj = cityService.findBy(id);

        return ResponseEntity.ok().body(obj);
    }

    @GetMapping
    public ResponseEntity<List<City>> findAll(){
        List<City> list = cityService.findAll();

        return ResponseEntity.ok().body(list);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id){
        cityService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
