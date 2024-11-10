package com.mu.tote2026

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController
import com.google.firebase.firestore.FirebaseFirestore
import com.mu.tote2026.presentation.navigation.NavGraph
import com.mu.tote2026.presentation.navigation.NavGraphSafety
import com.mu.tote2026.ui.theme.Tote2026Theme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()

        setContent {
            val navController = rememberNavController()

            Tote2026Theme {
                Scaffold(
                    /*containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary,*/
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    //MainScreen()
                    NavGraph(navController = navController)
                    //NavGraphSafety(navController = navController)

                }
            }
        }
    }
}

@Composable
fun MainScreen() {
    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    var groups by remember {
        mutableStateOf(emptyList<Group>())
    }

    val firestore = FirebaseFirestore.getInstance()
    val context = LocalContext.current
    val listener = firestore.collection("groups").addSnapshotListener { snapshot, exception ->
        groups = snapshot?.toObjects(Group::class.java) ?: emptyList()
        Toast.makeText(context, "Количество групп ${groups.size}", Toast.LENGTH_LONG).show()
    }
    //listener.remove()
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(groups) { group ->
            Text(
                text = "${group.number} - группа ${group.group}",
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentWidth()
            )
        }
    }
}

class Group {
    val number: Int = 0
    val group: String = ""
}
