package main;

public class ItemDePedido {

    // Atributos encapsulados
    private Produto produto;
    private int quantidade;
    private double precoVenda;

    /**
     * Construtor da classe ItemDePedido.
     * O precoVenda deve ser capturado do produto no momento da criação do item,
     * garantindo que alterações futuras no preço do produto não afetem este pedido.
     */
    public ItemDePedido(Produto produto, int quantidade, double precoVenda) {
        this.produto = produto;
        this.quantidade = quantidade;

        // TODO: Grantir que o preço seja congelado no momento que iniciou a transacao
        this.precoVenda = precoVenda;
    }

    public double calcularSubtotal() {
        return 0;
    }

    public void adicionaQuantidade(int quantidade) {
        this.quantidade += quantidade;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public double getPrecoDeVenda() {
        return precoVenda;
    }

    // --- Sobrescrita do método equals ---

    public double valorFinal() {
        double valorTotal = 0;

        for (int i = 0; i < quantidade; i++) {
            valorTotal += precoVenda;
        }

        return valorTotal;
    }

    /**
     * Compara a igualdade entre dois itens de pedido.
     * A regra de negócio define que dois itens são iguais se possuírem o mesmo Produto.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ItemDePedido) {
            ItemDePedido item = (ItemDePedido) obj;

            if (item.produto.equals(this.produto)) {
                return true;
            }
        }

        return false;
    }
}
