package br.com.rbqueiroz.valida_boleto.service;

import br.com.rbqueiroz.valida_boleto.entity.BoletoEntity;
import br.com.rbqueiroz.valida_boleto.entity.enums.SituacaoBoleto;
import br.com.rbqueiroz.valida_boleto.mapper.BoletoMapper;
import br.com.rbqueiroz.valida_boleto.repository.BoletoRepository;
import br.com.rbqueiroz.valida_boleto.service.kafka.NotificacaoProducer;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ValidarBoletoService {

    private static Logger LOGGER = org.slf4j.LoggerFactory.getLogger(ValidarBoletoService.class);

    private final BoletoRepository boletoRepository;
    private final NotificacaoProducer notificacaoProducer;
    private final PagarBoletoService pagarBoletoService;

    public ValidarBoletoService(BoletoRepository boletoRepository, NotificacaoProducer notificacaoProducer, PagarBoletoService pagarBoletoService) {
        this.boletoRepository = boletoRepository;
        this.notificacaoProducer = notificacaoProducer;
        this.pagarBoletoService = pagarBoletoService;
    }

    public void validar(BoletoEntity boleto) {
        var codigo = Integer.parseInt(boleto.getCodigoBarras().substring(0, 1));
        if (codigo % 2 == 0) {
            boletoErro(boleto);
            LOGGER.info("Erro ao validar boleto!");
        } else {
            boletoValido(boleto);
            pagarBoletoService.pagarBoleto(boleto);
        }
        boletoRepository.save(boleto);
        notificacaoProducer.send(BoletoMapper.toAvro(boleto));
    }

    private void boletoErro(BoletoEntity boleto) {
        boleto.setDataCriacao(LocalDateTime.now());
        boleto.setDataAtualizacao(LocalDateTime.now());
        boleto.setSituacaoBoleto(SituacaoBoleto.ERRO_VALIDACAO);
    }

    private void boletoValido(BoletoEntity boleto){
        boleto.setDataCriacao(LocalDateTime.now());
        boleto.setDataAtualizacao(LocalDateTime.now());
        boleto.setSituacaoBoleto(SituacaoBoleto.VALIDADO);
    }
}
