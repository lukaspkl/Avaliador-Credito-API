package com.example.avaliadorcredito.application;

import com.example.avaliadorcredito.application.ex.DadosClienteNotFoundException;
import com.example.avaliadorcredito.application.ex.ErroComunicacaoMicroservicesException;
import com.example.avaliadorcredito.application.ex.ErroSolicitacaoCartaoExeption;
import com.example.avaliadorcredito.domain.model.*;
import com.example.avaliadorcredito.infra.clients.CartoesResourceClient;
import com.example.avaliadorcredito.infra.clients.ClienteResourceClient;
import com.example.avaliadorcredito.infra.mqueue.SolicitacaoEmissaoCartaoPublisher;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class avaliadorCreditoService {

    private final ClienteResourceClient clientesClient;
    private final CartoesResourceClient cartoesClient;

    private final SolicitacaoEmissaoCartaoPublisher emissaoCartaoPublisher;
    public SituacaoCliente obterSituacaoCliente(String cpf) throws DadosClienteNotFoundException, ErroComunicacaoMicroservicesException{

        try {
            ResponseEntity<DadosCliente> dadosClienteResponse = clientesClient.dadosclientes(cpf);
            ResponseEntity<List<CartaoCliente>> cartoesResponse = cartoesClient.getCartoesByCliente(cpf);

            return SituacaoCliente
                    .builder()
                    .cliente(dadosClienteResponse.getBody())
                    .cartoes(cartoesResponse.getBody())
                    .build();
        } catch (FeignException.FeignClientException e){
            int status = e.status();
            if(HttpStatus.NOT_FOUND.value()== status){
                throw new DadosClienteNotFoundException();
            }

            throw new ErroComunicacaoMicroservicesException(e.getMessage(), status);
        }

    }

    public RetornoAvaliacaoCliente realizarAvaliacao(String cpf, Long renda) throws DadosClienteNotFoundException,ErroComunicacaoMicroservicesException {

        try {
            ResponseEntity<DadosCliente> dadosClienteResponse = clientesClient.dadosclientes(cpf);
            ResponseEntity<List<Cartao>> cartoesResponse = cartoesClient.getCartoesRendaAteh(renda);
            List<Cartao> cartoes = cartoesResponse.getBody();
             var listaCartoesAprovados = cartoes.stream().map(cartao ->{

                DadosCliente dadosCliente = dadosClienteResponse.getBody();

                BigDecimal limitebasico = cartao.getLimiteBasico();
                BigDecimal idade = BigDecimal.valueOf(dadosCliente.getIdade());
                BigDecimal var = idade.divide(BigDecimal.valueOf(10));
                BigDecimal limiteaprovado = idade.multiply(limitebasico);


                CartaoAprovado aprovado = new CartaoAprovado();
                aprovado.setCartao(cartao.getNome());
                aprovado.setBandeira(cartao.getBandeira());
                aprovado.setLimiteAprovado(limiteaprovado);

                return aprovado;
            }).collect(Collectors.toList());

             return new RetornoAvaliacaoCliente(listaCartoesAprovados);

        } catch (FeignException.FeignClientException e){
            int status = e.status();
            if(HttpStatus.NOT_FOUND.value()== status){
                throw new DadosClienteNotFoundException();
            }

            throw new ErroComunicacaoMicroservicesException(e.getMessage(), status);
        }
    }

    public ProtocoloSolicitacaoCartao solicitarEmissaoCartao(DadosSolicitacaoEmissaoCartao dados){
        try {
            emissaoCartaoPublisher.solicitarCartao(dados);
            var protocolo = UUID.randomUUID().toString();
            return  new ProtocoloSolicitacaoCartao(protocolo);
        }catch (Exception e){
            throw new ErroSolicitacaoCartaoExeption(e.getMessage());
        }
    }
}
