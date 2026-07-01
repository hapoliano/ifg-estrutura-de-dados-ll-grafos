# Sistema de Rotas Aéreas — Trabalho de Grafos

**Disciplina:** Estrutura de Dados II  
**Instituição:** Instituto Federal de Goiás — Campus Luziânia  
**Entrega:** 01/07/2026

---

## 1. Visão Geral

Este trabalho implementa um sistema de rotas aéreas utilizando a estrutura de dados **Grafo**. O sistema permite cadastrar cidades, criar rotas diretas entre elas e verificar se existe caminho entre duas cidades quaisquer, mesmo que seja necessário passar por cidades intermediárias.

O programa é executado via terminal e oferece um menu interativo com as seguintes opções:

- Cadastrar cidade
- Cadastrar rota entre duas cidades
- Listar todas as cidades
- Listar todas as rotas
- Verificar rota usando Busca em Largura
- Verificar rota usando Busca em Profundidade

---

## 2. Como Executar

### Pré-requisitos

- **JDK 17** ou superior instalado (`javac -version` para conferir).

### Compilar

Na raiz do projeto, onde estão os arquivos `.java`:

```bash
javac *.java -d out
```

### Executar

```bash
java -cp out Main
```

O programa abrirá um menu interativo no terminal:

```
=== Sistema de Rotas Aéreas ===
1. Cadastrar cidade
2. Cadastrar rota
3. Listar cidades
4. Listar rotas
5. Verificar rota (Busca em Largura)
6. Verificar rota (Busca em Profundidade)
0. Sair
```

## 3. Modelagem do Grafo

O grafo foi modelado como um **grafo não direcionado**, pois uma rota aérea entre duas cidades permite viagens nos dois sentidos.

A representação interna utilizada foi a **lista de adjacência**, onde cada cidade (vértice) mantém uma lista de rotas (arestas) que partem dela para seus vizinhos diretos. Essa abordagem é eficiente em memória para grafos esparsos, como uma rede de rotas aéreas típica.

### Estrutura lógica:

```
Grafo
 ├── cidades: Map<String, Cidade>     → mapeia nome da cidade ao objeto Cidade
 └── arestas: Map<String, List<Rota>> → mapeia nome da cidade à lista de rotas saindo dela
```

Quando uma rota A ↔ B é adicionada, ela é registrada nos dois sentidos:
- A lista de A recebe uma aresta A → B
- A lista de B recebe uma aresta B → A

---

## 4. Classes Implementadas

### Cidade
Representa um vértice do grafo — uma cidade atendida pela companhia aérea.

| Atributo | Tipo   | Descrição              |
|----------|--------|------------------------|
| nome     | String | Nome da cidade         |
| estado   | String | Estado onde se localiza|

### Rota
Representa uma aresta do grafo — uma ligação direta entre duas cidades.

| Atributo | Tipo   | Descrição       |
|----------|--------|-----------------|
| origem   | Cidade | Cidade de origem|
| destino  | Cidade | Cidade de destino|

### Grafo
Classe principal que armazena os vértices e arestas e implementa os algoritmos de busca.

| Método                                         | Descrição                                              |
|------------------------------------------------|--------------------------------------------------------|
| `adicionarCidade(Cidade cidade)`               | Adiciona um vértice ao grafo                           |
| `adicionarRota(String origem, String destino)` | Adiciona uma aresta bidirecional entre dois vértices   |
| `listarCidades()`                              | Exibe todos os vértices cadastrados                    |
| `listarRotas()`                                | Exibe todas as arestas, sem repetição                  |
| `existeRotaBuscaEmLargura(String origem, String destino)` | Verifica conectividade usando Busca em Largura         |
| `existeRotaBuscaEmProfundidade(String origem, String destino)` | Verifica conectividade usando Busca em Profundidade    |

---

## 5. Algoritmos de Busca

### Busca em Largura

A Busca em Largura percorre o grafo **nível a nível** a partir da cidade de origem. Todos os vizinhos diretos são visitados antes de avançar para os vizinhos dos vizinhos.

**Estrutura utilizada:** Fila (FIFO — `Queue`)

**Funcionamento:**
1. A cidade de origem é inserida na fila e marcada como visitada.
2. Enquanto a fila não estiver vazia, retira-se a cidade do início.
3. Se for a cidade destino, retorna `true`.
4. Caso contrário, todos os vizinhos ainda não visitados são adicionados à fila.
5. Se a fila esvaziar sem encontrar o destino, retorna `false`.

### Busca em Profundidade

A Busca em Profundidade mergulha o mais fundo possível em um caminho antes de voltar e tentar outro.

**Estrutura utilizada:** Pilha (LIFO — `Deque`)

**Funcionamento:**
1. A cidade de origem é empilhada e marcada como visitada.
2. Enquanto a pilha não estiver vazia, retira-se a cidade do topo.
3. Se for a cidade destino, retorna `true`.
4. Caso contrário, todos os vizinhos ainda não visitados são empilhados.
5. Se a pilha esvaziar sem encontrar o destino, retorna `false`.

### Comparação entre os algoritmos

| Característica       | Busca em Largura (BFS) | Busca em Profundidade (DFS) |
|----------------------|------------------------|-----------------------------|
| Estrutura            | Fila                   | Pilha                       |
| Ordem de visita      | Nível por nível        | Caminho por caminho         |
| Caminho mais curto   | Sim (em nº de saltos)  | Não garantido               |
| Resultado (existe?)  | Igual                  | Igual                       |

Ambos os algoritmos utilizam um conjunto `visitados` para evitar visitar a mesma cidade duas vezes, prevenindo loops infinitos em grafos com ciclos.

---

## 6. Exemplo de Execução

### Cidades e rotas cadastradas

```
Cidades:
  Goiânia      - GO
  Luziânia     - GO
  Valparaíso   - GO
  Cristalina   - GO
  Brasília     - DF
  Ouro Preto   - MG
  Caldas Novas - GO

Rotas (geograficamente coerentes):
  Brasília     <-> Valparaíso   (cidades vizinhas)
  Brasília     <-> Luziânia     (~60 km)
  Brasília     <-> Goiânia      (~200 km, rota principal)
  Luziânia     <-> Cristalina   (~100 km ao sul)
  Goiânia      <-> Caldas Novas (~170 km ao sul)
  Cristalina   <-> Ouro Preto   (divisa GO/MG)
```

### Consulta 1 — Caldas Novas → Ouro Preto (Busca em Largura)

```
[Largura] Buscando rota: Caldas Novas -> Ouro Preto
[Largura] Ordem de visita: Caldas Novas Goiânia Brasília Valparaíso Luziânia Cristalina Ouro Preto
[Largura] Destino encontrado!

Existe rota de "Caldas Novas" para "Ouro Preto"? SIM
```

### Consulta 2 — Valparaíso → Cristalina (Busca em Profundidade)

```
[Profundidade] Buscando rota: Valparaíso -> Cristalina
[Profundidade] Ordem de visita: Valparaíso Brasília Goiânia Caldas Novas Luziânia Cristalina
[Profundidade] Destino encontrado!

Existe rota de "Valparaíso" para "Cristalina"? SIM
```

