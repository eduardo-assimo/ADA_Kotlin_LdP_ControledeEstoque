package refactoring.programa

import refactoring.exceptions.LimiteEstoqueMaxException
import refactoring.objetos.Estoque
import refactoring.objetos.ItemEstoque


fun main() {

    var estoque = Estoque()
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
                    addItem(idGlobal, estoque)
                    idGlobal++
                }
                2 -> {
                    editItem(estoque)
                }
                3 -> {
                    estoque.mostrarItensEstoque(false)
                }
                4 -> {
                    estoque.mostrarItensEstoque(true)
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

fun addItem(idGlobal: Int, estoque: Estoque) {
    println("\nInforme o nome do novo item: ")
    val nomeItem = readln()
    println("Informe a quantidade do novo item: ")
    val qtdItem = readln().toIntOrNull() ?: 0
    estoque.addItem(ItemEstoque(idGlobal, nomeItem, qtdItem))
}

fun editItem(estoque: Estoque) {
    var itemEdit: Int?
    var item: ItemEstoque
    var novoNome: String
    var novaQtd: Int
    do{
        println("\nDigite o id do Produto a Editar (apenas numérico). Digite 0 para sair:  ")
        itemEdit = readln().toIntOrNull()
        if(itemEdit == null){
            println("\nNúmero inválido. Informe novamente.")
        } else {
            if (itemEdit != 0) {
                println("\nDigite a nova descrição do Item. Caso não queira alterá-la, digite <enter>: ")
                novoNome = readlnOrNull() ?: ""
                println("Digite a nova quantidade do Item. Caso não queira alterá-la, digite <enter>: ")
                novaQtd = readlnOrNull()?.toIntOrNull() ?: -1
                val itemEditado = ItemEstoque(itemEdit, novoNome, novaQtd)
                estoque.editItem(itemEditado)
            }
        }
    } while (itemEdit != null && itemEdit != 0)
}