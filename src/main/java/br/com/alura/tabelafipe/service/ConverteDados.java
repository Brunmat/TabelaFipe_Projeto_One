package br.com.alura.tabelafipe.service;

import tools.jackson.core.JacksonException;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.JavaType;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

public class ConverteDados  implements  IconverteDados {
    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public <T> T obterdados(String json, Class<T> classe) {
        return mapper.readValue(json, classe);
    }


    public <T> List<T> obterLista(String json, Class<T> classe) {
        try {
            JavaType tipoLista = mapper
                    .getTypeFactory()
                    .constructCollectionType(List.class, classe);

            return mapper.readValue(json, tipoLista);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
