package com.algar.demoalar.services;

import com.algar.demoalar.entities.City;
import com.algar.demoalar.repositories.CityRepository;
import com.algar.demoalar.repositories.StateRepository;
import com.algar.demoalar.services.exceptions.DataIntegrityViolation;
import com.algar.demoalar.services.exceptions.ObjectNotfoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CityService {

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private StateRepository stateRepository;

    @Transactional
    public City insert(City obj){
        obj.setId(null);
        obj = cityRepository.save(obj);
        stateRepository.save(obj.getState());
        return obj;
    }

    public City findBy(Integer id){
        Optional<City> obj = cityRepository.findById(id);

        return obj.orElseThrow(() -> new ObjectNotfoundException(
                "Objeto não encontrato! Id: "
                    + id
                    + ", Tipo: "
                    + City.class.getName()
        ));
    }

    public List<City> findAll(){
        return cityRepository.findAll();
    }


    public void delete(Integer id){
        findBy(id);
        try {
            cityRepository.deleteById(id);
        }
        catch (DataIntegrityViolationException e){
            throw new DataIntegrityViolation("Não é possível excluir, pois há entidades relacionadas!");
        }
    }

}
