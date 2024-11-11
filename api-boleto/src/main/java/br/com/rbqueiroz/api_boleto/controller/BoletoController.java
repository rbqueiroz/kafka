package br.com.rbqueiroz.api_boleto.controller;

import br.com.rbqueiroz.api_boleto.dto.BoletoDTO;
import br.com.rbqueiroz.api_boleto.dto.BoletoRequestDTO;
import br.com.rbqueiroz.api_boleto.service.BoletoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/boleto")
public class BoletoController {

    public final BoletoService boletoService;

    public BoletoController(BoletoService boletoService) {
        this.boletoService = boletoService;
    }

    @PostMapping
    public ResponseEntity<BoletoDTO> salvarBoleto(@Valid @RequestBody BoletoRequestDTO boletoRequestDTO) {
        BoletoDTO boletoDTO = boletoService.salvar(boletoRequestDTO.getCodigoBarras());
        return new ResponseEntity<>(boletoDTO, HttpStatus.CREATED);
    }

    @GetMapping("/{codigobarras}")
    public ResponseEntity<BoletoDTO> getBoleto(@PathVariable("codigobarras") String codigoBarras) {
        BoletoDTO boletoDTO = boletoService.buscarBoletoPorCodigoBarras(codigoBarras);
        return new ResponseEntity<>(boletoDTO, HttpStatus.OK);
    }
}
