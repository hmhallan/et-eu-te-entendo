#ET - ET phone home

##Problema
Construir um reconhecedor de linguagem livre de contexto que resolva o problema abaixo:

##Descrição
Desde o início de 2006 o Seti@home (programa de busca de vida alienígena) tem registrado padrões estranhos em transmissões de rádio recebidas do espaço. Inicialmente imaginou-se tratar apenas de estática. Porém, com o tempo e a repetição das transmissões os pesquisadores foram se convencendo que algo mais havia. Convidados a participar do projeto, lingüistas da Universidade de Baylor identificaram uma linguagem na transmissão. Era uma linguagem bastante simples.

A língua tem várias regras de composição de palavras. As regras de composição serão descritas nesse problema pelos seguintes elementos: 
* um conjunto de símbolos não-terminais ``1``; 
* um conjunto de símbolos terminais ``T``; 
* um símbolo não-terminal especial chamado de raiz; 
* um conjunto de regras de composição de palavras. 

Todas as regras de composição que consideramos aqui serão ou da forma ``A -> BC`` ou da forma ``A -> a``, onde ``A,B,C`` são elementos de ``V`` e ``a`` é um elemento de ``T``. 
A notação acima indica que podemos substituir o não-terminal ``A`` à esquerda da seta pelo terminal ``a`` (no primeiro caso) ou pela concatenação dos não-terminais ``A`` e ``B`` (no segundo caso) que aparecem à direita da seta. 

Aplicando repetidamente as regras de composição sobre o símbolo raiz, podemos montar palavras válidas na língua. 
Por exemplo, suponha que o seguinte conjunto de regras de composição é válido: 

```
S -> AB
A -> a
B -> b
```

A palavra ab pode ser obtida a partir desse conjunto de regras de composição da seguinte maneira: 

```
S -> AB
AB -> aB, pois A -> a
aB -> ab, pois B -> b
```

Já a palavra b não pode ser produzida a partir de S a partir desse mesmo conjunto de regras de composição. 
Dado um conjunto de regras de composição e uma lista de palavras, sua tarefa é determinar, para cada uma das palavras, se ela pode ou não ser produzida a partir das regras descritas na instância atual. 

##Entrada
A entrada é composta por vários casos de teste. Cada teste segue as regras descritas acima. 
Na primeira linha de cada teste aparece o símbolo raiz, que sempre será uma letra maiúscula. Na segunda linha, o conjunto V será fornecido como uma palavra composta apenas por letras maiúsculas. Cada letra dessa palavra será identificada como um membro de V . 

O conjunto ``T`` será dado como uma palavra de caracteres imprimíveis (com exceção de ``#`` e caracteres em branco) na terceira linha. Cada caractere dessa palavra será identificada como um membro de ``T`` . 
A seguir, serão fornecidas várias linhas, que descreverão as regras de composição para a instância atual. Uma regra de composição na forma ``# -> #`` indica o fim da lista de regras de composição. 

Por fim, são fornecidas várias linhas, cada uma contendo uma palavra que desejamos saber se pode ou não ser produzida a partir da raiz por meio das regras de composição. Essas palavras não vão conter qualquer caractere em ``V`` e são compostas por no máximo ``50`` caracteres. A lista de palavras termina com uma linha contendo ``#`` na primeira coluna. 

##Saída
No início de cada instância imprima a linha ``Instancia k``, onde ``k`` é o número da instância atual. Em seguida, para cada palavra ``x`` da lista, imprima uma linha na saída dizendo ``x`` e uma palavra valida se ela pode ser obtida a partir da raiz por meio das regras de composição, e ``x`` não e uma palavra valida caso contrário. Imprima uma linha em branco após cada instância. 


##Exemplo
```
Entrada:
S
SAB
ab
S -> AB
A -> a
B -> b
# -> #
ab
a
#
S
SAB
ab
S -> AB
A -> a
B -> b
S -> a
# -> #
ab
a
#

Saída
Instancia 1
ab e uma palavra valida
a nao e uma palavra valida

Instancia 2
ab e uma palavra valida
a e uma palavra valida 
```
