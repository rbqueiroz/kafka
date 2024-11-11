package br.com.rbqueiroz.api_boleto.service;

import br.com.rbqueiroz.api_boleto.controller.exception.ApplicationException;
import br.com.rbqueiroz.api_boleto.controller.exception.NotFoundException;
import br.com.rbqueiroz.api_boleto.dto.BoletoDTO;
import br.com.rbqueiroz.api_boleto.entity.BoletoEntity;
import br.com.rbqueiroz.api_boleto.entity.enums.SituacaoBoleto;
import br.com.rbqueiroz.api_boleto.mapper.BoletoMapper;
import br.com.rbqueiroz.api_boleto.repository.BoletoRepository;
import br.com.rbqueiroz.api_boleto.service.kafka.BoletoProducer;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class BoletoService {

    private final BoletoRepository boletoRepository;

    private final BoletoProducer boletoProducer;

    public BoletoService(BoletoRepository boletoRepository, BoletoProducer boletoProducer) {
        this.boletoRepository = boletoRepository;
        this.boletoProducer = boletoProducer;
    }

    public BoletoDTO salvar(String codigoBarras){
        var boletoOptional = boletoRepository.findByCodigoBarras(codigoBarras);
        if(boletoOptional.isPresent()){
            throw new ApplicationException("Boleto já cadastrado");
        }

        var boletoEntity = BoletoEntity.builder()
                .codigoBarras(codigoBarras)
                .situacaoBoleto(SituacaoBoleto.INICIALIZADO)
                .dataCriacao(LocalDateTime.now())
                .dataAtualizacao(LocalDateTime.now())
                .build();

        BoletoEntity boleto =  boletoRepository.save(boletoEntity);
        boletoProducer.send(BoletoMapper.toAvro(boleto));
        return BoletoMapper.toDTO(boleto);
    }

    private BoletoEntity recuperarBoleto(String codigoBarras) {
        return boletoRepository.findByCodigoBarras(codigoBarras).
                orElseThrow(() -> new NotFoundException("Boleto não encontrado"));
    }

    public BoletoDTO buscarBoletoPorCodigoBarras(String codigoBarras){
        return BoletoMapper.toDTO(recuperarBoleto(codigoBarras));
    }

    public void atualizar(BoletoEntity boleto){
        var boletoAtual = recuperarBoleto(boleto.getCodigoBarras());
        boletoAtual.setSituacaoBoleto(boleto.getSituacaoBoleto());
        boletoAtual.setDataAtualizacao(LocalDateTime.now());
        boletoRepository.save(boletoAtual);
    }
}
