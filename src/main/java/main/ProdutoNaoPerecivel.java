package main;

import java.util.Locale;

public class ProdutoNaoPerecivel extends Produto {

    public ProdutoNaoPerecivel(String desc, double precoCusto, Double margemLucro) {
        super(desc, precoCusto, margemLucro);
    }

    ProdutoNaoPerecivel(String desc, double precoCusto) {
        super(desc, precoCusto);
    }

    @Override
    public double valorVenda() {
        return super.valorVenda();
    }

    /**
     * Gera a linha de persistência para produto não perecível no formato:
     * {@code 1;descricao;precoCusto;margemLucro}.
     *
     * @return dados do produto formatados para arquivo texto
     */
    @Override
    public String gerarDadosText() {
        return String.format(Locale.US, "1;%s;%.2f;%.2f", getDescricao(), precoCusto, margemLucro);
    }
}
