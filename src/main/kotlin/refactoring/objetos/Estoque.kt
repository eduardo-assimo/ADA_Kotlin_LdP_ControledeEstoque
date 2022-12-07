package refactoring.objetos

import refactoring.exceptions.LimiteEstoqueMaxException


class Estoque {
    var listaEstoque: MutableList<ItemEstoque>

    init {
        listaEstoque = mutableListOf()
    }

    fun addItem(itemEstoque: ItemEstoque) {
        if(itemEstoque.qtdItem > 999) {
            throw LimiteEstoqueMaxException("\nA quantidade de produtos não deve ultrapassar 999 itens.")
        }
        listaEstoque.add(itemEstoque)
    }

    fun editItem(itemEstoque: ItemEstoque) {
        listaEstoque.forEach {
            if (it.idGlobal==itemEstoque.idGlobal) {
                if (!itemEstoque.nomeItem.isEmpty()) {
                    it.nomeItem = itemEstoque.nomeItem
                }
                if(itemEstoque.qtdItem>=0) {
                    it.qtdItem = itemEstoque.qtdItem
                }
            }
        }
    }

    fun mostrarItensEstoque(mostraZerados: Boolean) {
        var existeEstoque: Boolean = false
        if(listaEstoque.isEmpty()){
            println("\nNão há itens cadastrados no sistema.")
        } else {
            listaEstoque.forEach { item ->
                if (mostraZerados || item.qtdItem > 0) {
                    print("ID: #")
                    println("%04d".format(item.idGlobal))
                    println("Nome: ${item.nomeItem}")
                    println("Quantidade: ${item.qtdItem}")
                    existeEstoque = true
                }
            }
            if (!mostraZerados && !existeEstoque) {
                println("\nEstoque zerado.")
            }
        }
    }

}