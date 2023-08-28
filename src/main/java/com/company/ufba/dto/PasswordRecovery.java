package com.company.ufba.dto;

import lombok.*;

import java.util.HashMap;
import java.util.Map;

@Getter @Setter @NoArgsConstructor @RequiredArgsConstructor
public class PasswordRecovery {
    @NonNull
    private String cpf;
    @NonNull
    private String senhaAtual;
    @NonNull
    private String novaSenha;
    @NonNull
    private String confirmacao;
    @NonNull
    private String dica;

    private String status;


    public HashMap<String,String> allData(){
        return new HashMap<>(Map.of("cpf",cpf,
                "senhaAtual",senhaAtual,
                "novaSenha",novaSenha,
                "confirmacao",confirmacao,
                "dica",dica));
    }
}
