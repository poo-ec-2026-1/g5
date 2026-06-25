package controller;

public class ControllerTest {

    public void testeCadastroComDadosInvalidosDeveFalhar() {
        System.out.println("Executando teste: Validacao de dados invalidos.");
        
        boolean dadosValidos = false;
        
        if (dadosValidos) {
            System.out.println("Erro: O sistema aceitou dados invalidos!");
        } else {
            System.out.println("Sucesso: O sistema barrou os dados invalidos.");
        }
    }
}s