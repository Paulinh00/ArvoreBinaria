import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class TradutorMorse extends JFrame {
    private ArvoreBinariaMorse arvoreMorse;
    private JTextField inputMorse;
    private JTextField inputFrase;
    private JTextArea outputResultado;
    private JTextField inputNovoCaractere;
    private JTextField inputNovoCodigoMorse;
    private Map<Character, String> mapaMorse;

    public TradutorMorse() {
        arvoreMorse = new ArvoreBinariaMorse();
        mapaMorse = new HashMap<>();

        // Usa a nova classe LeitorMorse para carregar o arquivo morse.txt
        LeitorMorse leitor = new LeitorMorse();
        try {
            leitor.carregarArvoreEMapa("morse.txt", arvoreMorse, mapaMorse);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Configurações da Janela
        setTitle("Tradutor Morse");
        setSize(700, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(8, 1));

        // Campo de entrada para código Morse
        inputMorse = new JTextField();
        add(new JLabel("Digite o código Morse ou a frase"));
        add(inputMorse);

        // Campo de entrada para frase
        inputFrase = new JTextField();
        add(new JLabel("Traduzir para código Morse"));
        add(inputFrase);

        // Botão de tradução
        JButton botaoTraduzir = new JButton("Traduzir");
        add(botaoTraduzir);

        // Campo de saída de resultado
        outputResultado = new JTextArea();
        outputResultado.setEditable(false);
        add(new JScrollPane(outputResultado));

        // Novo campo para inserção de caracteres
        inputNovoCaractere = new JTextField();
        inputNovoCodigoMorse = new JTextField();
        add(new JLabel("Digite o novo caractere para inserir:"));
        add(inputNovoCaractere);
        add(new JLabel("Digite o código Morse correspondente:"));
        add(inputNovoCodigoMorse);

        // Botão de inserir novo caractere
        JButton botaoInserir = new JButton("Inserir Novo Caractere");
        add(botaoInserir);

        // Botão para exibir a árvore
        JButton botaoMostrarArvore = new JButton("Mostrar Árvore");
        add(botaoMostrarArvore);

        // Ação do botão de tradução
        botaoTraduzir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String morse = inputMorse.getText().trim();
                String frase = inputFrase.getText().trim();
                if (!morse.isEmpty()) {
                    String resultado = traduzirDeMorseParaTexto(morse);
                    outputResultado.setText("Morse para texto: " + resultado);
                } else if (!frase.isEmpty()) {
                    String resultado = traduzirTextoParaMorse(frase);
                    outputResultado.setText("Texto para Morse: " + resultado);
                } else {
                    outputResultado.setText("Erro: Insira uma frase ou código Morse.");
                }
            }
        });

        // Ação do botão de inserir novo caractere
        botaoInserir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String caractere = inputNovoCaractere.getText().trim();
                String codigoMorse = inputNovoCodigoMorse.getText().trim();

                if (!caractere.isEmpty() && !codigoMorse.isEmpty() && caractere.length() == 1) {
                    char novoCaractere = caractere.charAt(0);

                    // Inserir na árvore e no mapa
                    arvoreMorse.inserirNovoCaractere(codigoMorse, novoCaractere);
                    mapaMorse.put(novoCaractere, codigoMorse);

                    // Atualizar o arquivo morse.txt
                    try {
                        BufferedWriter writer = new BufferedWriter(new FileWriter("morse.txt", true));
                        writer.write(novoCaractere + " = " + codigoMorse);
                        writer.newLine();
                        writer.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }

                    outputResultado.setText("Novo caractere '" + novoCaractere + "' inserido com sucesso!");
                } else {
                    outputResultado.setText("Erro: Insira um caractere válido e o código Morse.");
                }
            }
        });

        // Ação do botão de mostrar árvore
        botaoMostrarArvore.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame janelaArvore = new JFrame("Árvore Binária Morse");
                janelaArvore.setSize(800, 600);
                PainelArvoreBinaria painelArvore = new PainelArvoreBinaria(arvoreMorse);
                janelaArvore.add(painelArvore);
                janelaArvore.setVisible(true);
            }
        });
    }

    // Método para traduzir código Morse para texto
    private String traduzirDeMorseParaTexto(String morse) {
        String[] palavrasMorse = morse.split("   "); // Três espaços para separar palavras
        StringBuilder resultado = new StringBuilder();

        for (String palavraMorse : palavrasMorse) {
            String[] letrasMorse = palavraMorse.split(" "); // Um espaço para separar letras
            for (String codigo : letrasMorse) {
                char letra = arvoreMorse.buscar(codigo);
                if (letra != '\0') {
                    resultado.append(letra);
                } else {
                    resultado.append('?'); // Indica que o código não foi encontrado
                }
            }
            resultado.append(" "); // Adiciona espaço entre palavras
        }
        return resultado.toString().trim();
    }

    // Método para traduzir texto para código Morse
    private String traduzirTextoParaMorse(String frase) {
        StringBuilder resultado = new StringBuilder();
        for (char letra : frase.toUpperCase().toCharArray()) {
            String codigoMorse = mapaMorse.get(letra);  // Usa o mapa gerado a partir do arquivo
            if (codigoMorse != null) {
                resultado.append(codigoMorse).append(" ");
            }
        }
        return resultado.toString().trim();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TradutorMorse().setVisible(true);
            }
        });
    }
}
