package es.uc3m.android.pockets_chef_app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import es.uc3m.android.pocketschef.ui.theme.PocketsChefTheme

// Temporary data class — replaced by Room entity in Week 6
data class RecipePreview(
    val id: Int,
    val title: String,
    val duration: String,
    val servings: Int,
    val isFavorite: Boolean = false
)

private val sampleRecipes = listOf(
    RecipePreview(1, "Classic Scrambled Eggs", "10 min", 2),
    RecipePreview(2, "Grilled Chicken Breast", "25 min", 4),
    RecipePreview(3, "Garlic Rice", "20 min", 3),
    RecipePreview(4, "Tomato Pasta", "30 min", 4),
)

@Composable
fun RecipesScreen(navController: NavController) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedTab by remember { mutableIntStateOf(0) }

    Column(modifier = Modifier.fillMaxSize()) {

        // ── Header ────────────────────────────────────────────────────────
        Surface(color = MaterialTheme.colorScheme.primary) {
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)) {
                Text(
                    text = "Recipe Collection",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text("Search recipes…") },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                    singleLine = true,
                    shape = RoundedCornerShape(24.dp),
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface
                    )
                )
            }
        }

        // ── Tabs ──────────────────────────────────────────────────────────
        TabRow(selectedTabIndex = selectedTab) {
            Tab(selected = selectedTab == 0, onClick = { selectedTab = 0 }, text = { Text("All Recipes") })
            Tab(selected = selectedTab == 1, onClick = { selectedTab = 1 }, text = { Text("Favorites") })
        }

        // ── List ──────────────────────────────────────────────────────────
        val displayed = sampleRecipes.filter {
            it.title.contains(searchQuery, ignoreCase = true)
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(displayed) { recipe ->
                RecipeCard(recipe = recipe)
            }
        }
    }
}

@Composable
fun RecipeCard(recipe: RecipePreview) {
    var favorite by remember { mutableStateOf(recipe.isFavorite) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = recipe.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "${recipe.duration} · ${recipe.servings} servings",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            }
            IconButton(onClick = { favorite = !favorite }) {
                Icon(
                    imageVector = if (favorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = "Toggle favorite",
                    tint = if (favorite) MaterialTheme.colorScheme.primary
                           else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RecipesScreenPreview() {
    PocketsChefTheme {
        RecipesScreen(navController = rememberNavController())
    }
}
