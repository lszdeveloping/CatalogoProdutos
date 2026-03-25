package com.example.productcatalog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import java.text.NumberFormat
import java.util.Locale

data class Product(
    val name: String,
    val description: String,
    val price: Double,
    val category: String
)

class MainActivity : AppCompatActivity() {

    private val allProducts = listOf(
        Product("Smartphone Pro X", "Tela 6.7, 256GB, 5G", 2999.99, "Eletronicos"),
        Product("Notebook UltraSlim", "Intel i7, 16GB RAM, SSD 512GB", 4599.90, "Eletronicos"),
        Product("Fone Bluetooth", "Cancelamento de ruido, 30h bateria", 349.90, "Eletronicos"),
        Product("Smartwatch Fit", "GPS, monitor cardiaco", 799.00, "Eletronicos"),
        Product("Camiseta Premium", "Algodao, slim fit", 79.90, "Roupas"),
        Product("Tenis Running", "Solado amortecedor, respiravel", 299.90, "Roupas"),
        Product("Jaqueta Bomber", "100% algodao, lavagem stone", 189.90, "Roupas"),
        Product("Calca Jeans Slim", "Stretch, lavagem escura", 149.90, "Roupas"),
        Product("Clean Code", "Robert C. Martin", 89.90, "Livros"),
        Product("O Poder do Habito", "Charles Duhigg", 49.90, "Livros"),
        Product("Kotlin em Acao", "Dmitry Jemerov", 99.90, "Livros"),
        Product("Sapiens", "Yuval Noah Harari", 44.90, "Livros"),
        Product("Bicicleta MTB 29", "21 marchas, freio a disco", 2499.00, "Esportes"),
        Product("Kit Halteres 20kg", "Borracha antiderrapante", 299.90, "Esportes"),
        Product("Bola de Futebol", "Couro sintetico, tamanho 5", 119.90, "Esportes"),
        Product("Corda de Pular Speed", "Cabo de aco, rolamento duplo", 59.90, "Esportes")
    )

    private val categories = listOf("Todos", "Eletronicos", "Roupas", "Livros", "Esportes")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val tabLayout = findViewById<TabLayout>(R.id.tabLayout)

        val adapter = ProductAdapter(allProducts)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        categories.forEach { tabLayout.addTab(tabLayout.newTab().setText(it)) }

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                val filtered = if (tab.text == "Todos") allProducts
                else allProducts.filter { it.category == tab.text }
                adapter.updateList(filtered)
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }
}

class ProductAdapter(private var products: List<Product>) :
    RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvName: TextView = view.findViewById(R.id.tvName)
        val tvDescription: TextView = view.findViewById(R.id.tvDescription)
        val tvPrice: TextView = view.findViewById(R.id.tvPrice)
        val tvCategory: TextView = view.findViewById(R.id.tvCategory)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = products[position]
        holder.tvName.text = product.name
        holder.tvDescription.text = product.description
        holder.tvCategory.text = product.category
        holder.tvPrice.text = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))
            .format(product.price)
    }

    override fun getItemCount() = products.size

    fun updateList(newList: List<Product>) {
        products = newList
        notifyDataSetChanged()
    }
}