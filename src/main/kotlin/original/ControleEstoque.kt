package original

fun main() {
    val listaItens = mutableListOf<Triple<Int, String, Int>>()
    var idGlobal = 1
    var opcao: Int?
    val menu = """
        
        
    Digite a opção desejada:
    
    1..................ADICIONAR ITEM
    2..................EDITAR ITEM
    3..................EXIBIR ITENS EM ESTOQUE
    4..................EXIBIR TODOS OS ITENS
    0..................FECHAR SISTEMA
""".trimIndent()

    do {
        println(menu)

        opcao = readln().toIntOrNull()
        try {
            when(opcao) {
                1 -> {
                    listaItens.add(addItem(idGlobal))
                    idGlobal++
                }
                2 -> {
                    editItem(listaItens)
                }
                3 -> {
                    mostrarItensEstoque(listaItens, false)
                }
                4 -> {
                    mostrarItensEstoque(listaItens, true)
                }
                0 -> {
                    println("\nPrograma finalizado!")
                }
            }
        } catch (e: LimiteEstoqueMaxException) {
            println(e.message)
        } catch (e: Exception) {
            println("\nOcorreu um erro inesperado. Tente Novamente.")
        }
    } while (opcao != 0)
}

fun addItem(idGlobal: Int): Triple<Int, String, Int> {
    println("\nInforme o nome do novo item: ")
    var nomeItem = readln()
    println("Informe a quantidade do novo item: ")
    var qtdItem = readln().toIntOrNull() ?: 0
    if(qtdItem > 999) {
        throw LimiteEstoqueMaxException("\nA quantidade de produtos não deve ultrapassar 999 itens.")
    }
    return Triple(idGlobal, nomeItem, qtdItem)
}

fun editItem(listaItens: MutableList<Triple<Int, String, Int>>) {
    var itemEdit: Int?
    var item: Triple<Int, String, Int>?
    var novoNome: String
    var novaQtd: Int
    do{
        println("\nDigite o id do Produto a Editar (apenas numérico). Digite 0 para sair:  ")
        itemEdit = readln().toIntOrNull()
        if(itemEdit == null){
            println("\nNúmero inválido. Informe novamente.")
        } else {
            if (itemEdit != 0) {
                item = listaItens[itemEdit.dec()]
                item?.let {
                    println("\nDigite a nova descrição do Item. Caso não queira alterá-la, digite <enter>: ")
                    novoNome = readlnOrNull() ?: it.second
                    if(novoNome.isEmpty()){
                        novoNome = it.second
                    }
                    println("Digite a nova quantidade do Item. Caso não queira alterá-la, digite <enter>: ")
                    novaQtd = readlnOrNull()?.toIntOrNull() ?: it.third
                    if(novaQtd > 999) {
                        throw LimiteEstoqueMaxException("\nA quantidade de produtos não deve ultrapassar 999 itens.")
                    }
                    val itemEditado = Triple(it.first, novoNome, novaQtd)
                    listaItens[it.first.dec()] = itemEditado
                }
            }
        }
    } while (itemEdit != null && itemEdit != 0)
}

fun mostrarItensEstoque(listaItens: MutableList<Triple<Int, String, Int>>, mostraZerados: Boolean) {
    var existeEstoque: Boolean = false
    if(listaItens.isEmpty()){
        println("\nNão há itens cadastrados no sistema.")
    } else {
        listaItens.forEach { item ->
            if (mostraZerados || item.third > 0) {
                print("ID: #")
                println("%04d".format(item.first))
                println("Nome: ${item.second}")
                println("Quantidade: ${item.third}")
                existeEstoque = true
            }
        }
        if (!mostraZerados && !existeEstoque) {
            println("\nEstoque zerado.")
        }
    }
}

class LimiteEstoqueMaxException(message: String): Exception(message)