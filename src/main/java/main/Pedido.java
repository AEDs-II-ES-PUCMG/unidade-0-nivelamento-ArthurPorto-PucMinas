package main;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Pedido {

	/** Quantidade máxima de produtos de um pedido */
	private static final int MAX_PRODUTOS = 10;
	
	/** Porcentagem de desconto para pagamentos à vista */
	private static final double DESCONTO_PG_A_VISTA = 0.15;

	private ItemDePedido[] itens;
	
	/** Data de criação do pedido */
	private LocalDate dataPedido;
	
	/** Indica a forma de pagamento do pedido sendo: 1, pagamento à vista; 2, pagamento parcelado */
	private int formaDePagamento;
	
	/** Construtor do pedido.
	 *  Deve criar o vetor de produtos do pedido, 
	 *  armazenar a data e a forma de pagamento informadas para o pedido. 
	 */  
	public Pedido(LocalDate dataPedido, int formaDePagamento) {
		itens = new ItemDePedido[MAX_PRODUTOS];
		this.dataPedido = dataPedido;
		this.formaDePagamento = formaDePagamento;
	}

	public void limpar() {
		itens = new ItemDePedido[MAX_PRODUTOS];
	}
	
	/**
     * Inclui um produto neste pedido e aumenta a quantidade de produtos armazenados no pedido até o momento.
     * @param novo O produto a ser incluído no pedido
     * @return true/false indicando se a inclusão do produto no pedido foi realizada com sucesso.
     */
	public boolean incluirProduto(ItemDePedido novo) {
		int quantProdutos = itens.length;

		if (quantProdutos < MAX_PRODUTOS) {
			// MARK: Valido aqui pra nao inserir duplicado

			for (int i = 0; i < quantProdutos; i++) {
				if (itens[i].equals(novo)) {
					itens[i].adicionaQuantidade(novo.getQuantidade());
					return true;
				}
			}

			itens[quantProdutos++] = novo;
			return true;
		}

		return false;
	}

	public void mesclarPedido(Pedido outroPedido) {
		int quantProdutos = itens.length;
		int quantOutroProdutos = outroPedido.itens.length;
		int todosOsprodutos = quantProdutos + quantOutroProdutos;

		if (todosOsprodutos > MAX_PRODUTOS) {
			throw new IllegalArgumentException("Não é possível adicionar mais produtos");
		}

		for (int i = 0; i < quantOutroProdutos; i++) {
			// MARK: Valida na funcoa de incluir para nao ter duplicados no vetor
			boolean incluiiuOutroProduto = false;

			for(int j = 0; j < quantProdutos; i++) {
				if(itens[j].equals(outroPedido.itens[i])) {

					if(itens[j].getPrecoDeVenda() > outroPedido.itens[i].getPrecoDeVenda() ) {
						itens[j] = outroPedido.itens[i];
					} else {
						itens[j].adicionaQuantidade(outroPedido.itens[i].getQuantidade());
					}

					incluiiuOutroProduto = true;
				}
			}

			if(!incluiiuOutroProduto) {
				itens[quantOutroProdutos++] = outroPedido.itens[i];
			}
		}

		outroPedido.limpar();
	}
	
	/**
     * Calcula e retorna o valor final do pedido (soma do valor de venda de todos os produtos do pedido).
     * Caso a forma de pagamento do pedido seja à vista, aplica o desconto correspondente.
     * @return Valor final do pedido (double)
     */
	public double valorFinal() {
		double valorPedido = 0;
		
		for (int i = 0; i < itens.length; i++) {
			valorPedido += itens[i].valorFinal();
		}
		
		if (formaDePagamento == 1) {
			valorPedido = valorPedido * (1.0 - DESCONTO_PG_A_VISTA);
		}

		return valorPedido;
	}
	
	/**
     * Representação, em String, do pedido.
     * Contém um cabeçalho com sua data e o número de produtos no pedido.
     * Depois, em cada linha, a descrição de cada produto do pedido.
     * Ao final, mostra a forma de pagamento, o percentual de desconto (se for o caso) e o valor a ser pago pelo pedido.
     * Exemplo:
     * Data do pedido: 25/08/2025
     * Pedido com 2 produtos.
     * Produtos no pedido:
     * NOME: Iogurte: R$ 8.00
     * Válido até: 29/08/2025
     * NOME: Guardanapos: R$ 2.75
     * Pedido pago à vista. Percentual de desconto: 15,00%
     * Valor total do pedido: R$ 10.75 
     * @return Uma string contendo dados do pedido conforme especificado (cabeçalho, detalhes, forma de pagamento,
     * percentual de desconto - se for o caso - e valor a pagar)
     */
	@Override
	public String toString() {

		// TODO: Menu é querer demais
		StringBuilder stringPedido = new StringBuilder();
		DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		int quantProdutos =  itens.length;
		
		stringPedido.append("Data do pedido:" + formatoData.format(dataPedido) + "\n");
		stringPedido.append("Pedido com " + quantProdutos + " produtos.\n");
		stringPedido.append("Produtos no pedido:\n");

		for (int i = 0; i < quantProdutos; i++ ) {
			stringPedido.append(itens[i].toString() + "\n");
		}
		
		stringPedido.append("Pedido pago ");
		if (formaDePagamento == 1) {
			stringPedido.append("à vista. Percentual de desconto: " + String.format("%.2f", DESCONTO_PG_A_VISTA * 100) + "%\n");
		} else {
			stringPedido.append("parcelado.\n");
		}
		
		stringPedido.append("Valor total do pedido: R$ " + String.format("%.2f", valorFinal()));
		
		return stringPedido.toString();
	}
	
	/**
     * Igualdade de pedidos: caso possuam a mesma data. 
     * @param obj Outro pedido a ser comparado 
     * @return booleano true/false conforme o parâmetro possua a data igual ou não a este pedido.
     */
    @Override
    public boolean equals(Object obj) {
        Pedido outro = (Pedido)obj;
        return this.dataPedido.equals(outro.dataPedido);
    }
}