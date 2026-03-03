package main;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class ProdutoPerecivel extends Produto {

    private static final double DESCONTO = 0.25;
    private static final int PRAZO_DESCONTO = 7;
    private LocalDate dataDeValidade;

    public ProdutoPerecivel(String desc, double precoCusto, Double margemLucro, LocalDate validade) {
        super(desc, precoCusto, margemLucro);
        this.dataDeValidade = validade;
    }

    @Override
    public double valorVenda() {
        return super.valorVenda();
    }

    @Override
    public String toString() {
        return super.toString();
    }

    /**
     * Gera a linha de persistência para produto perecível no formato:
     * {@code 2;descricao;precoCusto;margemLucro;dd/MM/yyyy}.
     *
     * @return dados do produto formatados para arquivo texto
     */
    @Override
    public String gerarDadosText() {
        String dataFormatada = DateTimeFormatter.ofPattern("dd/MM/yyyy").format(dataDeValidade);
        return String.format(Locale.US, "2;%s;%.2f;%.2f;%s", getDescricao(), precoCusto, margemLucro, dataFormatada);
    }
}
