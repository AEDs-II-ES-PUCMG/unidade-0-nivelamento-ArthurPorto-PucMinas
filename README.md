# AEDs II: Revisão e Nivelamento

Aplicação Java de linha de comando para gerenciamento de produtos perecíveis e não perecíveis, com persistência em arquivo CSV.

## Objetivo

Praticar conceitos de:

- Programação orientada a objetos (herança, polimorfismo e sobrescrita).
- Leitura e escrita de arquivos texto.
- Organização de projeto Java com Gradle.

## Funcionalidades implementadas

- Carga de produtos a partir de `dadosProdutos.csv` na inicialização.
- Listagem de todos os produtos cadastrados.
- Busca de produtos por nome (não sensível a maiúsculas/minúsculas).
- Cadastro de novos produtos pelo menu.
- Salvamento manual pelo menu e salvamento automático ao sair.
- Conversão texto -> objeto via `Produto.criarDoTexto(...)`.
- Conversão objeto -> texto via `gerarDadosText()` / `gerarDadosTexto()`.

## Regras de domínio atuais

- Descrição do produto deve ter pelo menos 3 caracteres.
- `precoCusto > 0` e `margemLucro > 0`.
- Tipos de produto aceitos no CSV:
  - `1` ou `N`: não perecível
  - `2` ou `P`: perecível
- Produto perecível exige data no formato `dd/MM/yyyy`.
- O vetor de produtos sempre reserva `MAX_NOVOS_PRODUTOS = 10` posições extras para novos cadastros.

## Arquitetura e estrutura

```
src/
  main/
    java/main/
      App.java                   # menu, fluxo da aplicação, leitura/escrita do CSV
      Produto.java               # classe abstrata base
      ProdutoNaoPerecivel.java   # especialização de Produto
      ProdutoPerecivel.java      # especialização de Produto
  test/
    java/
      ProdutoNaoPerecivelTest.java
      ProdutoPerecivelTest.java
```

### Classes principais

- `App`: ponto de entrada (`main`) e operações da CLI.
- `Produto` (abstrata):
  - contém validações comuns e cálculo base de venda;
  - centraliza parsing CSV com `criarDoTexto(...)`;
  - define `gerarDadosText()` com erro explícito caso não seja sobrescrito.
- `ProdutoNaoPerecivel`:
  - sobrescreve `gerarDadosText()` no formato `1;descricao;preco;margem`.
- `ProdutoPerecivel`:
  - sobrescreve `gerarDadosText()` no formato `2;descricao;preco;margem;data`.

## Formato do arquivo CSV

O arquivo de dados fica na raiz do projeto (`dadosProdutos.csv`) e usa:

1. Primeira linha: quantidade de itens.
2. Demais linhas: um produto por linha.

Exemplo:

```csv
6
1;Lapis;1.30;0.50
2;Chips de Banana;3.50;0.25;15/03/2026
1;Guardanapos;2.50;0.35
2;Pao de queijo;1.50;0.40;21/04/2026
1;Frigideira;18.00;0.30
2;Iogurte;7.50;0.15;30/04/2026
```

## Menu da aplicação

- `1` Listar todos os produtos
- `2` Procurar e listar um produto
- `3` Cadastrar novo produto
- `4` Salvar produtos
- `0` Sair (também salva automaticamente antes de encerrar)

## Como executar

### Pré-requisitos

- Java JDK 21 (ou compatível com o projeto).

### Via IntelliJ IDEA

1. Abra a pasta raiz do projeto.
2. Aguarde a sincronização do Gradle.
3. Execute a classe `main.App`.

### Via terminal (sem plugin `application`)

Compilar:

```bash
javac -d out src/main/java/main/*.java
```

Executar:

```bash
java -cp out main.App
```

### Testes

Executar com Gradle:

```bash
./gradlew test
```

## Tecnologias

- Java
- Gradle (wrapper)
- JUnit 5

## Aluno

- Arthur Henrique Porto Silva
