package br.com.alura.tabelafipe.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosValor(@JsonAlias("Modelo") String modelo, @JsonAlias("AnoModelo") String anoDomodelo,@JsonAlias("Valor") String valor ) {
}
