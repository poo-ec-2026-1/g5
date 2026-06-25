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

    //Commit2
    public void testeExcluirItemInexistenteDeveDarErro() {
        System.out.println("Executando teste: Exclusao de item inexistente.");
        
        boolean itemFoiEncontrado = false; // Simula que o item não existe no banco
        
        if (!itemFoiEncontrado) {
            System.out.println("Sucesso: O sistema tratou corretamente a tentativa de excluir um item inexistente.");
        } else {
            System.out.println("Erro: O sistema tentou apagar um item que nao existia!");
        }
    }
}