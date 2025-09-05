# 🧩 Jogo do Sudoku — Java (Console + Swing)

Implementação do clássico Sudoku em **Java**, com suporte a:
- **Console** (modo texto interativo).
- **Swing** (interface gráfica amigável).

Projeto estruturado em camadas (`model`, `service`, `ui`, `util`), priorizando **organização**, **manutenibilidade** e **extensibilidade**.

## 📑 Sumário
    - [✨ Funcionalidades](#-funcionalidades)
    - [🚀 Melhorias implementadas](#-melhorias-implementadas)
    - [📂 Estrutura do projeto](#-estrutura-do-projeto)
    - [▶️ Como executar (Console)](#️-como-executar-console)
    - [🖥️ Como executar (Swing)](#️-como-executar-swing)
    - [⌨️ Atalhos e Dicas](#️-atalhos-e-dicas)
    - [🔮 Próximos passos sugeridos](#-próximos-passos-sugeridos)

## ✨ Funcionalidades
    - Tabuleiro **9×9** com casas fixas e editáveis.
    - Status do jogo: **não iniciado**, **incompleto** ou **completo**.
    - Checagem de inconsistências comparando com a **solução esperada** (`expected`).
    - Interface de **console** com menu de operações.
    - Interface **Swing** com feedback visual e botões de controle.


## 🚀 Melhorias implementadas

1. **Validação de jogadas**  
   O `Board` garante que não há repetições na **linha**, **coluna** ou **setor 3×3** (`Board.isValidMove`).

2. **Proteção na inserção**  
   `Board.changeValue` só aceita valores que respeitam as regras e não sejam de células fixas.

3. **API do BoardService**  
   Métodos utilitários expostos:  
   `isValidMove`, `candidates`, `changeValue`, `clearValue`, `countEmpty`, `countWrong`.

4. **Feedback visual (Swing)**  
   - ✅ **Verde claro**: valor correto.  
   - ❌ **Rosa**: valor inválido ou errado.  
   - ⬜ **Branco**: célula vazia.  
   (Implementado em `NumberText`.)

5. **Mensagens de status detalhadas**  
   Botão **“Verificar jogo”** exibe:
   - Total de **vazios**.
   - Total de **incorretos**.
   - Situação atual do jogo.

> Todas as melhorias foram **aditivas**, sem alterar código pré-existente.

## 📂 Estrutura do projeto
    src/
    └─ br/com/dio
    ├─ model
    │   ├─ Board.java          # regras, estado e validações
    │   ├─ GameStatusEnum.java
    │   └─ Space.java
    ├─ service
    │   ├─ BoardService.java   # fachada para a UI
    │   ├─ EventEnum.java
    │   ├─ EventListener.java
    │   └─ NotifierService.java
    ├─ ui/custom
    │   ├─ button/ (Reset, CheckGameStatus, Finish)
    │   ├─ frame/MainFrame.java
    │   ├─ input/NumberText.java (+ NumberTextLimit.java)
    │   ├─ panel/MainPanel.java, SudokuSector.java
    │   └─ screen/MainScreen.java
    ├─ util/BoardTemplate.java
    ├─ Main.java         # versão console
    └─ UIMain.java       # versão Swing



## ▶️ Como executar (Console)

O console lê a configuração do jogo via **args**, no formato:

```

"col,row;expected,fixed"

````

Exemplo (múltiplos argumentos, até 81 posições):

```bash
java br.com.dio.Main \
"0,0;5,true" "1,0;3,true" "2,0;4,false" ...
````

### Menu de opções:

1. Iniciar novo jogo
2. Colocar número
3. Remover número
4. Visualizar jogo atual
5. Verificar status do jogo
6. Limpar jogo
7. Finalizar jogo
8. Sair

⚠️ Ao tentar inserir um valor inválido (ou em célula fixa), o sistema **bloqueia** a jogada e informa o motivo.

## 🖥️ Como executar (Swing)

Recebe os mesmos `args` da versão console:

```bash
java br.com.dio.UIMain \
"0,0;5,true" "1,0;3,true" "2,0;4,false" ...
```

### Recursos visuais:

* Campos editáveis com **limite \[1..9]**.
* Feedback de cores em tempo real (**verde / rosa / branco**).
* Botões:

    * 🔄 **Reiniciar**
    * 🔎 **Verificar jogo**
    * ✅ **Concluir**

## ⌨️ Atalhos e Dicas

* A validação de jogadas segue **apenas as regras do Sudoku**, independente da solução.
* A verificação final compara com o `expected`.
* O botão **Verificar** mostra a quantidade de casas **vazias** e **incorretas**.

## 📜 Licença

Este projeto é distribuído sob a licença **MIT**.
Sinta-se à vontade para usar, modificar e compartilhar.

