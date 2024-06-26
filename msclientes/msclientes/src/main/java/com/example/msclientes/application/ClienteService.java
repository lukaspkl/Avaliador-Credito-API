package com.example.msclientes.application;

import com.example.msclientes.domain.Cliente;
import com.example.msclientes.infra.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository repository;

    @Transactional
    public Cliente save (Cliente cliente){
        return repository.save(cliente);
    }

    public Optional<Cliente> getbyCPF(String cpf){
        return repository.findByCpf(cpf);
    }
}
