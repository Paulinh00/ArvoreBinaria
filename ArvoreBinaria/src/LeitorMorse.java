import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

public class LeitorMorse {

    // Método responsável por carregar os dados do arquivo morse.txt
    public void carregarArvoreEMapa(String nomeArquivo, ArvoreBinariaMorse arvoreMorse, Map<Character, String> mapaMorse) throws IOException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(nomeArquivo);
        if (inputStream == null) {
            throw new IOException("Arquivo " + nomeArquivo + " não encontrado no classpath.");
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String linha;

        while ((linha = reader.readLine()) != null) {
            linha = linha.trim(); // Remover espaços em branco no início e no fim
            if (!linha.isEmpty() && linha.contains(" = ")) {
                String[] partes = linha.split(" = ");
                if (partes.length == 2) {
                    char caractere = partes[0].charAt(0);  // Pega a letra
                    String codigoMorse = partes[1];  // Pega o código Morse
                    arvoreMorse.inserir(codigoMorse, caractere); // Insere na árvore
                    mapaMorse.put(caractere, codigoMorse); // Armazena a letra e seu código Morse no mapa
                }
            }
        }
        reader.close();
    }
}
