package com.example.msclientes.application;

import com.example.msclientes.application.representation.ClienteSaveRequest;
import com.example.msclientes.domain.Cliente;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("clientes")
public class ClientesController {

    private final ClienteService service;

    @GetMapping
    public String status(){
        return "ok";
    }

    @PostMapping
    public ResponseEntity save(@RequestBody ClienteSaveRequest request){
        Cliente cliente = request.tomodel();
        service.save(cliente);
        URI headerlocation = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .query("cpf={cpf}")
                .buildAndExpand(cliente.getCpf())
                .toUri();
        return ResponseEntity.created(headerlocation).build();
    }

    @GetMapping(params = "cpf")
    public ResponseEntity dadosclientes(@RequestParam("cpf") String cpf){
        Optional<Cliente> cliente = service.getbyCPF(cpf);
        if (cliente.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(cliente);

    }
}
