package com.example.cambioservice.controller;

import com.example.cambioservice.model.Cambio;
import com.example.cambioservice.repository.CambioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.math.RoundingMode;

@RestController
@RequestMapping("/cambio-service")
public class CambioController {

    @Autowired
    private Environment environment;

    @Autowired
    private CambioRepository cambioRepository;

    @GetMapping("/{amount}/{from}/{to}")
    public Cambio getCambio(@PathVariable("amount") BigDecimal amount, @PathVariable("from") String from, @PathVariable("to") String to) {
        String port = this.environment.getProperty("local.server.port");
        Cambio cambio = this.cambioRepository.findAll().stream().filter(cambioInterno -> cambioInterno.getFrom().equals(from) && cambioInterno.getTo().equals(to)).findFirst().orElseThrow(() -> new RuntimeException("Currency unsupported"));
        cambio.setEnvironment(port);
        cambio.setConvertedValue(cambio.getConversionFactor().multiply(amount).setScale(2, RoundingMode.CEILING));
        return cambio;
    }

}
