package com.example.mscartoes.application;

import com.example.mscartoes.application.representation.CartaoSaveRequest;
import com.example.mscartoes.application.representation.CartoesPorClienteResponse;
import com.example.mscartoes.domain.Cartao;
import com.example.mscartoes.domain.ClienteCartao;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("cartoes")
@RequiredArgsConstructor
public class CartoesResources {

    private final CartaoService cartaoService;
    private final ClienteCartaoService clienteCartaoService;

    @GetMapping
    public String status(){
        return "ok";
    }

    @PostMapping
    public ResponseEntity cadastra(@RequestBody CartaoSaveRequest request){
        Cartao cartao = request.toModel();
        cartaoService.save(cartao);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping(params = "renda")
    public ResponseEntity<List<Cartao>> getCartoesRendaAteh(@RequestParam("renda")Long renda){
        List<Cartao> list = cartaoService.getCartoesRendaMenorigual(renda);
        return ResponseEntity.ok(list);
    }

    @GetMapping(params = "cpf")
    public ResponseEntity<List<CartoesPorClienteResponse>> getCartoesByCliente(@RequestParam("cpf") String cpf){

        List<ClienteCartao> lista = clienteCartaoService.listaCartoesBycpf(cpf);
        List<CartoesPorClienteResponse> resultlist = lista.stream().map(CartoesPorClienteResponse::frommodel)
                .collect(Collectors.toList());
        return ResponseEntity.ok(resultlist);
    }
}
