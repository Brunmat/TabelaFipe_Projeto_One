package br.com.alura.tabelafipe.principal;

import br.com.alura.tabelafipe.model.DadosMarcas;
import br.com.alura.tabelafipe.model.DadosModelos;
import br.com.alura.tabelafipe.model.DadosValor;
import br.com.alura.tabelafipe.service.ConsumoApi;
import br.com.alura.tabelafipe.service.ConverteDados;
import tools.jackson.databind.exc.MismatchedInputException;


import javax.xml.transform.Source;
import java.io.EOFException;
import java.io.IOException;
import java.sql.SQLOutput;
import java.util.*;
import java.util.stream.Collectors;

public class Principal {

private Scanner leitor = new Scanner(System.in);
private ConsumoApi comsumoApi = new ConsumoApi();
private ConverteDados conversor = new ConverteDados();
private final String ENDERECO = "https://parallelum.com.br/fipe/api/v1/";


public void exibirMenu(){
String menuInicial = """
        **** Digite o nome do tipo de veículo que deseja buscar ***
                                CARROS
                                MOTOS
                               CAMINHOES
                               
                          Digite close para finalizar.
        ************************************************************
        """;
    System.out.println(menuInicial);
        var opcaoEscolhida = leitor.nextLine();

        try{
            while(!(opcaoEscolhida.equalsIgnoreCase("0"))){
                if(opcaoEscolhida.toLowerCase().equals("carros") || opcaoEscolhida.toLowerCase().equals("motos") || opcaoEscolhida.toLowerCase().equals("caminhoes") ){
                    var json = comsumoApi.obterDados(ENDERECO + opcaoEscolhida + "/marcas");



                    List<DadosMarcas> marcas = conversor.obterLista(json, DadosMarcas.class);

//            marcas.forEach(System.out::println); // me trás a lista com as Strings declaradas no record, no for abaixo consigo moldar de forma melhor.

//            for( int i = 0 ; i < marcas.size(); i ++){
//                System.out.println("Código : " + marcas.get(i).codigo() + "  Descrição: " + marcas.get(i).descricao() );
//
//            }
                    marcas.forEach( m -> System.out.println("Código : " + m.codigo() + // lambda
                            "  Descrição : " + m.descricao()));


                    System.out.println("Digite o código da marca para ver os veículos : ");


                    var codigoCarro = Integer.parseInt(leitor.nextLine());

                    json = comsumoApi.obterDados(ENDERECO + opcaoEscolhida + "/marcas/" + codigoCarro + "/modelos");


                    DadosModelos resposta = conversor.obterdados(json, DadosModelos.class);


//            List<DadosMarcas> re2 = resposta.modelos().stream()
//                    .limit(10)
//                            .collect(Collectors.toList());
//
//
//            re2.forEach(System.out::println); // teste para ver como usar o stream na lista  //

//            resposta.modelos().forEach(System.out::println);
//            for( int i = 0 ; i < resposta.modelos().size(); i ++){
//                System.out.println("Código : " + resposta.modelos().get(i).codigo() + "  Descrição: " + resposta.modelos().get(i).descricao() );
//
//            }

                    resposta.modelos().forEach(m -> System.out.println("Código: " + m.codigo() + // versão lambda
                            "  Descrição : " + m.descricao()));


//            CharSequence nomeTrecho = leitor.nextLine().toLowerCase();
//            Optional<DadosMarcas> re3 = resposta.modelos().stream()
//                    .filter( m -> m.marcaDocarro().toLowerCase().contains(nomeTrecho))
//                    .findFirst();
                    // buscar por nome , não testado ainda //


                    System.out.println("Digite um trecho do nome do véiculo para consulta : ");

                    CharSequence nomeTrecho = leitor.nextLine().toLowerCase();

                    List<DadosMarcas> trecho = resposta.modelos().stream()
                            .filter(m -> m.descricao().toLowerCase().contains(nomeTrecho))
                            .collect(Collectors.toList());


                    trecho.forEach(m -> System.out.println("Código : " + m.codigo() +
                            "  Descrição: " + m.descricao()));



                    System.out.println("Digite o código do modelo para consultar valores : ");
                    var modelo = leitor.nextLine();
                    json = comsumoApi.obterDados(ENDERECO + opcaoEscolhida + "/marcas/" + codigoCarro + "/modelos/" + modelo + "/anos"  );

                    List<DadosMarcas> anosVeiculos = conversor.obterLista(json, DadosMarcas.class);

//          anosVeiculos.forEach( m -> System.out.println("Ano : " + m.codigo() +
//                   "  Descrição : " + m.descricao()));


                    for (var i = 0 ; i < anosVeiculos.size(); i++) {
                        json = comsumoApi.obterDados(ENDERECO + opcaoEscolhida + "/marcas/" + codigoCarro + "/modelos/" + modelo + "/anos/" + anosVeiculos.get(i).codigo());

                        DadosValor valor = conversor.obterdados(json,DadosValor.class);

                        System.out.println("Modelo: " + valor.modelo() + "  Ano: " + valor.anoDomodelo() + "  Valor: " + valor.valor());
                    }
                    opcaoEscolhida = leitor.nextLine();


                } else{
                    System.out.println("Digite uma das opções a cima.");
                    opcaoEscolhida = leitor.nextLine();
                }
            }
        }catch ( MismatchedInputException | NullPointerException | NumberFormatException e) {
            System.out.println("Oops! Você digitou algo errado! Por favor digite somente os códigos e trechos válidos!");
        }catch(RuntimeException e){
            System.out.println("Oops! Você digitou algo errado! Por favor digite somente os códigos e trechos válidos!");
        }




    }

    }





