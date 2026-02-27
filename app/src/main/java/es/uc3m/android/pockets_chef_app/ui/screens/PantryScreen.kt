package es.uc3m.android.pockets_chef_app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import es.uc3m.android.pockets_chef_app.ui.theme.ErrorRed
import es.uc3m.android.pockets_chef_app.ui.theme.PocketsChefTheme
import es.uc3m.android.pockets_chef_app.ui.theme.WarningAmber

// Temporary data class — replaced by Room entity in Week 5
data class PantryItemPreview(
    val id: Int,
    val name: String,
    val quantity: String,
    val category: String,
    val expiryLabel: String,
    val isExpiringSoon: Boolean = false
)

private val samplePantryItems = listOf(
    PantryItemPreview(1, "Eggs", "12 units", "Dairy", "Expires in 11 days"),
    PantryItemPreview(2, "Milk", "1 liter", "Dairy", "Expires in 6 days", isExpiringSoon = true),
    PantryItemPreview(3, "Chicken Breast", "500 g", "Meat", "Expires in 4 days", isExpiringSoon = true),
    PantryItemPreview(4, "Tomatoes", "5 units", "Vegetables", "Expires in 8 days"),
    PantryItemPreview(5, "Rice", "2 kg", "Grains", "Expires in 330 days"),
    PantryItemPreview(6, "Olive Oil", "750 ml", "Condiments", "Expires in 197 days"),
)

private val categories = listOf("All", "Dairy", "Meat", "Vegetables", "Grains", "Condiments")

@Composable
fun PantryScreen(navController: NavController) {
    var selectedCategory by remember { mutableStateOf("All") }
    val items = if (selectedCategory == "All") samplePantryItems
                else samplePantryItems.filter { it.category == selectedCategory }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* TODO Week 5: open add-item dialog */ },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add item",
                    tint = MaterialTheme.colorScheme.onPrimary)
            }
        }
    ) { innerPadding ->
        Column(modifier = Modifier.fillMaxSize().padding(innerPadding)) {

            // ── Header ────────────────────────────────────────────────────
            Surface(color = MaterialTheme.colorScheme.primary) {
                Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)) {
                    Text(
                        text = "My Pantry",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    Text(
                        text = "${samplePantryItems.size} items in your pantry",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // ── Category filter chips ─────────────────────────────────────
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(categories) { category ->
                    FilterChip(
                        selected = selectedCategory == category,
                        onClick = { selectedCategory = category },
                        label = { Text(category) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // ── Items list ────────────────────────────────────────────────
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(items) { item ->
                    PantryItemCard(item = item, onDelete = { /* TODO Week 5 */ })
                }
            }
        }
    }
}

@Composable
fun PantryItemCard(item: PantryItemPreview, onDelete: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = item.name, style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold)
                Text(text = item.quantity, style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f))
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = item.expiryLabel,
                    style = MaterialTheme.typography.labelSmall,
                    color = if (item.isExpiringSoon) WarningAmber
                            else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                )
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                SuggestionChip(onClick = {}, label = {
                    Text(item.category, style = MaterialTheme.typography.labelSmall)
                })
                Spacer(modifier = Modifier.width(8.dp))
                IconButton(onClick = onDelete) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete item",
                        tint = ErrorRed.copy(alpha = 0.7f))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PantryScreenPreview() {
    PocketsChefTheme {
        PantryScreen(navController = rememberNavController())
    }
}
