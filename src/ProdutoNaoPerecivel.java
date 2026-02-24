public class ProdutoNaoPerecivel extends Produto {

    ProdutoNaoPerecivel(String desc, double precoCusto, Double margemLucro) {
        super(desc, precoCusto, margemLucro);
    }

    ProdutoNaoPerecivel(String desc, double precoCusto) {
        super(desc, precoCusto);
    }

    @Override
    public double valorVenda() {
        return super.valorVenda();
    }
}
