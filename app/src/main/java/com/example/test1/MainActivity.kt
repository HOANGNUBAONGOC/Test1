package com.example.test1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.test1.ui.theme.Test1Theme
import androidx.compose.ui.draw.shadow
import androidx.compose.foundation.shape.RoundedCornerShape

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Test1Theme {
                var selectedProduct by remember { mutableStateOf<Product?>(null) }
                Surface(color = Color.White) {
                    if (selectedProduct == null) {
                        ProductListScreen(onProductClick = { selectedProduct = it })
                    } else {
                        ProductDetailsScreen(product = selectedProduct, onBack = { selectedProduct = null })
                    }
                }
            }
        }
    }
}

data class Product(
    val id: Int,
    val name: String,
    val price: String,
    val image: Painter,
    val rating: String,
    val description: String)

@Composable
fun getSampleProducts(): List<Product> {
    return listOf(
        Product(1, "Product 1", "$10.00", painterResource(id = R.drawable.ao), "★★★★☆", "A high-quality product with great value."),
        Product(2, "Product 2", "$20.00", painterResource(id = R.drawable.ao), "★★★☆☆", "This product offers decent performance."),
        Product(3, "Product 3", "$30.00", painterResource(id = R.drawable.ao), "★★★★★", "A premium product with excellent quality."),
        Product(4, "Product 4", "$40.00", painterResource(id = R.drawable.ao), "★★☆☆☆", "Basic features at an affordable price.")
    )
}

@Composable
fun ProductListScreen(onProductClick: (Product) -> Unit) {
    val products = getSampleProducts()
    LazyColumn(modifier = Modifier.padding(16.dp)) {
        items(products) { product ->
            ProductItem(product = product, onClick = { onProductClick(product) })
        }
    }
}

@Composable
fun ProductItem(product: Product, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick() }
            .shadow(4.dp, shape = RoundedCornerShape(12.dp)),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = product.image,
                contentDescription = null,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(12.dp))
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(text = product.name, style = MaterialTheme.typography.titleMedium)
                Text(text = product.rating, style = MaterialTheme.typography.bodySmall.copy(fontSize = 14.sp))
                Text(text = product.price, style = MaterialTheme.typography.bodyLarge.copy(color = Color.Red))
            }
        }
    }
}

@Composable
fun ProductDetailsScreen(product: Product?, onBack: () -> Unit) {
    product?.let {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = product.image,
                contentDescription = null,
                modifier = Modifier
                    .size(200.dp)
                    .clip(RoundedCornerShape(16.dp))
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = product.name, style = MaterialTheme.typography.headlineSmall)
            Text(text = product.rating, style = MaterialTheme.typography.bodyMedium.copy(fontSize = 16.sp))
            Text(text = product.price, style = MaterialTheme.typography.headlineMedium.copy(color = Color.Red))
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = product.description, style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(8.dp))
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { }, modifier = Modifier.fillMaxWidth()) {
                Text("Thêm vào giỏ hàng")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = onBack, modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)) {
                Text("Quay lại", color = Color.White)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewProductList() {
    ProductListScreen(onProductClick = {})
}
