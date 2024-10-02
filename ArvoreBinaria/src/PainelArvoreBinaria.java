import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class PainelArvoreBinaria extends JPanel {
    private ArvoreBinariaMorse arvore;

    public PainelArvoreBinaria(ArvoreBinariaMorse arvore) {
        this.arvore = arvore;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Define o ponto inicial para desenhar a árvore
        desenharArvore(g, arvore.getRaiz(), getWidth() / 2, 50, getWidth() / 4, 50);
    }

    // Função recursiva para desenhar a árvore binária
    private void desenharArvore(Graphics g, Nodo nodo, int x, int y, int xOffset, int yOffset) {
        if (nodo == null) {
            return;
        }

        // Desenha o círculo para o nó
        g.setColor(Color.BLACK);
        g.fillOval(x - 15, y - 15, 30, 30);
        g.setColor(Color.WHITE);
        g.drawString(String.valueOf(nodo.caractere), x - 5, y + 5); // Desenha o caractere dentro do círculo

        // Desenha a linha para o filho esquerdo
        if (nodo.filhoEsquerdo != null) {
            g.setColor(Color.BLACK);
            g.drawLine(x, y, x - xOffset, y + yOffset); // Linha para o filho esquerdo
            desenharArvore(g, nodo.filhoEsquerdo, x - xOffset, y + yOffset, xOffset / 2, yOffset); // Recursão para o filho esquerdo
        }

        // Desenha a linha para o filho direito
        if (nodo.filhoDireito != null) {
            g.setColor(Color.BLACK);
            g.drawLine(x, y, x + xOffset, y + yOffset); // Linha para o filho direito
            desenharArvore(g, nodo.filhoDireito, x + xOffset, y + yOffset, xOffset / 2, yOffset); // Recursão para o filho direito
        }
    }

    public static void main(String[] args) {
        // Criar a árvore binária Morse e carregar o arquivo morse.txt
        ArvoreBinariaMorse arvoreMorse = new ArvoreBinariaMorse();
        try {
            arvoreMorse.carregarDeArquivo("src\\morse.txt"); // Carrega a árvore do arquivo
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Criar a janela para exibir a árvore
        JFrame frame = new JFrame("Árvore Binária Morse");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        // Adicionar o painel que desenha a árvore à janela
        PainelArvoreBinaria painelArvore = new PainelArvoreBinaria(arvoreMorse);
        frame.add(painelArvore);

        // Exibir a janela
        frame.setVisible(true);
    }
}
