class Nodo {
    char caractere;
    Nodo filhoEsquerdo; //referece ao filho esquerdo como .
    Nodo filhoDireito; //referece ao filho direito como -

    public Nodo(char caractere) {
        this.caractere = caractere;
        this.filhoEsquerdo = null; // inicializa os filho como vazio
        this.filhoDireito = null; // inicializa os filho como vazio
    }
}
