package com.algar.demoalar.services;

import com.algar.demoalar.dto.ClienteNewDTO;
import com.algar.demoalar.entities.Address;
import com.algar.demoalar.entities.City;
import com.algar.demoalar.entities.Client;
import com.algar.demoalar.repositories.AddresRepositry;
import com.algar.demoalar.repositories.ClientRepository;
import com.algar.demoalar.services.exceptions.DataIntegrityViolation;
import com.algar.demoalar.services.exceptions.ObjectNotfoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private AddresRepositry addresRepositry;

    @Transactional
    public Client insert(Client obj){
        obj.setId(null);
        obj = clientRepository.save(obj);
        addresRepositry.saveAll(obj.getAddresses());
        return obj;
    }

    public Client findBy(Integer id){
        Optional<Client> obj = clientRepository.findById(id);

        return obj.orElseThrow(() -> new ObjectNotfoundException(
                "Objeto não encontrado! Id: "
                        + id
                        + ", Tipo: "
                        + Client.class.getName()
        )) ;
    }

    public List<Client> findAll(){
        return clientRepository.findAll();
    }

    public void delete(Integer id){
        findBy(id);
        try {
            clientRepository.deleteById(id);
        }
        catch (DataIntegrityViolationException e){
            throw new DataIntegrityViolation("Não é possível excluir, pois há entidades relacionadas!");
        }
    }

    public Client fromDto(ClienteNewDTO objDto){
        Client client = new Client(null, objDto.getName(), objDto.getEmail(), objDto.getClientType(), objDto.getCpfOrCnpj(), objDto.getPassWord());
        City city = new City(objDto.getCityId(), null, null);
        Address address = new Address(null, objDto.getLogrdouro(),objDto.getNumber(),objDto.getComplement(),objDto.getDistrict(),objDto.getCep(), client,city);

        client.getAddresses().add(address);
        client.getPhones().add(objDto.getPhone1());

        if (objDto.getPhone2() != null){
            client.getPhones().add(objDto.getPhone2());
        }
        if (objDto.getPhone3() != null){
            client.getPhones().add(objDto.getPhone3());
        }

        return client;
    }

}
