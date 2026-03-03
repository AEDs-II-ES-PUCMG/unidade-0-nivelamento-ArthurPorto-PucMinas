package main;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public abstract class Produto {
	
	private static final double MARGEM_PADRAO = 0.2;
	private String descricao;
	protected double precoCusto;
	protected double margemLucro;
	
	/**
     * Inicializador privado. Os valores default, em caso de erro, são:
     * "main.Produto sem descrição", R$ 0.00, 0.0
     * @param desc Descrição do produto (mínimo de 3 caracteres)
     * @param precoCusto Preço do produto (mínimo 0.01)
     * @param margemLucro Margem de lucro (mínimo 0.01)
     */
	private void init(String desc, double precoCusto, double margemLucro) {
		if ((desc.length() >= 3) && (precoCusto > 0.0) && (margemLucro > 0.0)) {
			descricao = desc;
			this.precoCusto = precoCusto;
			this.margemLucro = margemLucro;
		} else {
			throw new IllegalArgumentException("Valores inválidos para os dados do produto.");
		}
	}
	
	/**
     * Construtor completo. Os valores default, em caso de erro, são:
     * "main.Produto sem descrição", R$ 0.00, 0.0
     * @param desc Descrição do produto (mínimo de 3 caracteres)
     * @param precoCusto Preço do produto (mínimo 0.01)
     * @param margemLucro Margem de lucro (mínimo 0.01)
     */
	protected Produto(String desc, double precoCusto, double margemLucro) {
		init(desc, precoCusto, margemLucro);
	}
	
	/**
     * Construtor sem margem de lucro - fica considerado o valor padrão de margem de lucro.
     * Os valores default, em caso de erro, são:
     * "main.Produto sem descrição", R$ 0.00
     * @param desc Descrição do produto (mínimo de 3 caracteres)
     * @param precoCusto Preço do produto (mínimo 0.01)
     */
	protected Produto(String desc, double precoCusto) {
		init(desc, precoCusto, MARGEM_PADRAO);
	}

    /**
     * Cria um produto a partir de uma linha de texto no formato CSV simples:
     * {@code tipo;descricao;precoCusto;margemLucro;[dataValidade]}.
     * <p>
     * Tipos aceitos:
     * {@code 1} ou {@code N} para não perecível, {@code 2} ou {@code P} para perecível.
     * Para perecível, a data é obrigatória no formato {@code dd/MM/yyyy}.
     *
     * @param linha linha de dados do produto
     * @return instância de {@link Produto} criada a partir da linha
     * @throws IllegalArgumentException se a linha estiver nula/vazia, com formato inválido,
     *                                  tipo desconhecido ou valores não numéricos válidos
     */
    public static Produto criarDoTexto(String linha) {
        if (linha == null || linha.trim().isEmpty()) {
            throw new IllegalArgumentException("Linha de produto vazia.");
        }

        String[] dados = linha.split(";");
        if (dados.length < 4) {
            throw new IllegalArgumentException("Linha de produto com formato inválido: " + linha);
        }

        for (int i = 0; i < dados.length; i++) {
            dados[i] = dados[i].trim();
        }

        String tipo = dados[0];
        String descricao = dados[1];
        double precoCusto = Double.parseDouble(dados[2].replace(',', '.'));
        double margemLucro = Double.parseDouble(dados[3].replace(',', '.'));

        if (tipo.equals("1") || tipo.equalsIgnoreCase("N")) {
            return new ProdutoNaoPerecivel(descricao, precoCusto, margemLucro);
        }

        if (tipo.equals("2") || tipo.equalsIgnoreCase("P")) {
            if (dados.length < 5) {
                throw new IllegalArgumentException("Produto perecível sem data de validade: " + linha);
            }

            LocalDate validade = LocalDate.parse(dados[4], DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            return new ProdutoPerecivel(descricao, precoCusto, margemLucro, validade);
        }

        throw new IllegalArgumentException("Tipo de produto inválido: " + tipo);
    }
	
	 /**
     * Retorna o valor de venda do produto, considerando seu preço de custo e margem de lucro.
     * @return Valor de venda do produto (double, positivo)
     */
	public double valorVenda() {
		return (precoCusto * (1.0 + margemLucro));
	}

    /**
     * Gera a representação do produto em linha de texto para persistência.
     * Este método deve ser sobrescrito nas subclasses concretas.
     *
     * @return linha com os dados do produto no formato de arquivo
     * @throws UnsupportedOperationException sempre, caso não seja sobrescrito
     */
    public String gerarDadosText() {
        throw new UnsupportedOperationException("FatalError: gerarDadosText() deve ser sobrescrito nas subclasses.");
    }

    /**
     * Alias para manter compatibilidade com código que utiliza o nome em português completo.
     *
     * @return linha de dados do produto no formato de arquivo
     */
    public String gerarDadosTexto() {
        return gerarDadosText();
    }

    protected String getDescricao() {
        return descricao;
    }
	
	/**
     * Descrição, em string, do produto, contendo sua descrição e o valor de venda.
     *  @return String com o formato:
     * [NOME]: R$ [VALOR DE VENDA]
     */
    @Override
	public String toString() {
    	NumberFormat moeda = NumberFormat.getCurrencyInstance();
		return String.format("NOME: " + descricao + ": " + moeda.format(valorVenda()));
	}

    /**
     * Igualdade de produtos: caso possuam o mesmo nome/descrição.
     * @param obj Outro produto a ser comparado
     * @return booleano true/false conforme o parâmetro possua a descrição igual ou não a este produto.
     */
    @Override
    public boolean equals(Object obj){
        Produto outro = (Produto)obj;
        return this.descricao.toLowerCase().equals(outro.descricao.toLowerCase());
    }
}
