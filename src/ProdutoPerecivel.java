import java.time.LocalDate;

public class ProdutoPerecivel extends Produto {

    private static final double DESCONTO = 0.25;
    private static final int PRAZO_DESCONTO = 7;
    private LocalDate dataDeValidade;

    ProdutoPerecivel(String desc, double precoCusto, Double margemLucro, LocalDate validade) {
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
}
