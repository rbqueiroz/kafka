package br.com.rbqueiroz.valida_boleto.service;

import br.com.rbqueiroz.valida_boleto.entity.BoletoEntity;
import br.com.rbqueiroz.valida_boleto.entity.enums.SituacaoBoleto;
import br.com.rbqueiroz.valida_boleto.mapper.BoletoMapper;
import br.com.rbqueiroz.valida_boleto.repository.BoletoRepository;
import br.com.rbqueiroz.valida_boleto.service.kafka.NotificacaoProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PagarBoletoService {

    private static Logger LOGGER = LoggerFactory.getLogger(PagarBoletoService.class);

    private final BoletoRepository boletoRepository;
    private final NotificacaoProducer notificacaoProducer;

    public PagarBoletoService(BoletoRepository boletoRepository, NotificacaoProducer notificacaoProducer) {
        this.boletoRepository = boletoRepository;
        this.notificacaoProducer = notificacaoProducer;
    }

    public void pagarBoleto(BoletoEntity boleto) {
        String soNumeros = boleto.getCodigoBarras().replaceAll("[^0-9]", "");
        if(soNumeros.length() > 47){
            boletoErro(boleto);
            LOGGER.info("Falha ao pagar boleto!");
        } else {
            boletoPago(boleto);
            LOGGER.info("Boleto pago com sucesso!");
        }
        boletoRepository.save(boleto);
        notificacaoProducer.send(BoletoMapper.toAvro(boleto));
    }

    private void boletoErro(BoletoEntity boleto) {
        boleto.setDataAtualizacao(LocalDateTime.now());
        boleto.setSituacaoBoleto(SituacaoBoleto.ERRO_PAGAMENTO);
    }

    private void boletoPago(BoletoEntity boleto) {
        boleto.setDataAtualizacao(LocalDateTime.now());
        boleto.setSituacaoBoleto(SituacaoBoleto.PAGO);
    }
}
