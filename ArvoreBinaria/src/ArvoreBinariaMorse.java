import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ArvoreBinariaMorse {
    private Nodo raiz;

    public ArvoreBinariaMorse() {
        raiz = new Nodo('\0'); // Raiz com caractere nulo
    }

    // Adicionando o getter para a raiz
    public Nodo getRaiz() {
        return raiz;
    }

    //inserir um caractere na arvore usando codigo morse
    public void inserir(String codigoMorse, char caractere) {
        Nodo nodoAtual = raiz;
        for (char simbolo : codigoMorse.toCharArray()) { //Percorre a arvore criando novos nos a esquerda (para '.') e à direita (para '-')
            if (simbolo == '.') {
                if (nodoAtual.filhoEsquerdo == null) {
                    nodoAtual.filhoEsquerdo = new Nodo('\0'); //cria um no se ainda não existe
                }
                nodoAtual = nodoAtual.filhoEsquerdo;    // move para o nó esquerdo
            } else if (simbolo == '-') {
                if (nodoAtual.filhoDireito == null) {
                    nodoAtual.filhoDireito = new Nodo('\0'); // cria um no para a direita se não existir
                }
                nodoAtual = nodoAtual.filhoDireito; // move para o nó direito
            }
        }
        nodoAtual.caractere = caractere;
    }

    // Método para buscar um caractere na árvore
    public char buscar(String codigoMorse) {
        Nodo nodoAtual = raiz;
        for (char simbolo : codigoMorse.toCharArray()) {
            if (simbolo == '.') {
                nodoAtual = nodoAtual.filhoEsquerdo; //move para o nó esquerdo se for .
            } else if (simbolo == '-') {
                nodoAtual = nodoAtual.filhoDireito; // move para o nó direito se for -
            }

            if (nodoAtual == null) {
                return '\0'; // Se o caminho não existir retorna nulo
            }
        }
        return nodoAtual.caractere;
    }
    public void inserirNovoCaractere(String codigoMorse, char caractere) {
        inserir(codigoMorse, caractere); // Reutiliza o método inserir já existente
    }

    public void exibirArvore(Nodo nodo, int nivel) {
        if (nodo != null) {
            exibirArvore(nodo.filhoDireito, nivel + 1); // Exibe subárvore direita
            System.out.println(" ".repeat(nivel * 4) + nodo.caractere); // Exibe caractere no nível atual
            exibirArvore(nodo.filhoEsquerdo, nivel + 1); // Exibe subárvore esquerda
        }
    }

    // Método para carregar os caracteres e seus códigos morse a partir de um arquivo
    public void carregarDeArquivo(String caminhoArquivo) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(caminhoArquivo)); // abre para a leitura
        String linha;

        // lê cada linha do arquivo
        while ((linha = reader.readLine()) != null) {
            String[] partes = linha.split(" = "); // divide o caractere e o código Morse
            if (partes.length == 2) {
                char caractere = partes[0].charAt(0); // extrai o caractere
                String codigoMorse = partes[1]; // extrai o código Morse
                inserir(codigoMorse, caractere); // insere na árvore binária
            }
        }
        reader.close(); // fecha o arquivo
    }

    public void exibirArvore() {
        exibirArvore(raiz, 0); // Começa a exibição da raiz no nível 0
    }

    public static void main(String[] args) {
        ArvoreBinariaMorse arvore = new ArvoreBinariaMorse();

        try {
            // Carregar os caracteres do arquivo morse.txt
            arvore.carregarDeArquivo("src\\morse.txt");

            // Teste de exibição da árvore
            System.out.println("Exibindo a árvore binária Morse:");
            arvore.exibirArvore(); // Chama o método para exibir a árvore

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
