import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.navigation.NavController
import androidx.compose.ui.res.stringResource
import com.ombati.guidecane.R

@Composable
fun UpdateScreen(navController: NavController) {
    var userName by remember { mutableStateOf(TextFieldValue("")) }
    var geofenceLat by remember { mutableStateOf(TextFieldValue("")) }
    var geofenceLong by remember { mutableStateOf(TextFieldValue("")) }
    var geofencingPoints by remember { mutableStateOf(listOf<Pair<String, String>>()) }
    var wifiName by remember { mutableStateOf(TextFieldValue("")) }
    var wifiPassword by remember { mutableStateOf(TextFieldValue("")) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // User name field
        TextField(
            value = userName,
            onValueChange = { userName = it },
            label = { Text(stringResource(id = R.string.user_name)) }, // Using string resource
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Latitude and Longitude inputs
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            TextField(
                value = geofenceLat,
                onValueChange = { geofenceLat = it },
                label = { Text(stringResource(id = R.string.latitude)) }, // Using string resource
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(16.dp))

            TextField(
                value = geofenceLong,
                onValueChange = { geofenceLong = it },
                label = { Text(stringResource(id = R.string.longitude)) }, // Using string resource
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Button to add geofencing point
        Button(onClick = {
            if (geofenceLat.text.isNotEmpty() && geofenceLong.text.isNotEmpty()) {
                geofencingPoints = geofencingPoints + Pair(geofenceLat.text, geofenceLong.text)
                geofenceLat = TextFieldValue("")
                geofenceLong = TextFieldValue("")
            }
        }) {
            Text(stringResource(id = R.string.add_geofencing_point)) // Using string resource
        }

        Spacer(modifier = Modifier.height(8.dp))

        // List added geofencing points
        Column {
            Text(stringResource(id = R.string.geofencing_points)) // Using string resource
            geofencingPoints.forEach { point ->
                Text("Lat: ${point.first}, Long: ${point.second}")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // WiFi name field
        TextField(
            value = wifiName,
            onValueChange = { wifiName = it },
            label = { Text(stringResource(id = R.string.wifi_name)) }, // Using string resource
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // WiFi password field
        TextField(
            value = wifiPassword,
            onValueChange = { wifiPassword = it },
            label = { Text(stringResource(id = R.string.wifi_password)) }, // Using string resource
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Buttons Row
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = {
                    // Handle update logic here, then navigate back to profile
                    navController.navigate("profile") { popUpTo("profile") { inclusive = true } }
                },
                modifier = Modifier.weight(1f)
            ) {
                Text(stringResource(id = R.string.save_changes)) // Using string resource
            }

            Spacer(modifier = Modifier.width(16.dp))

            Button(
                onClick = {
                    // Navigate back to profile without saving changes
                    navController.navigate("profile") { popUpTo("profile") { inclusive = true } }
                },
                modifier = Modifier.weight(1f)
            ) {
                Text(stringResource(id = R.string.cancel)) // Using string resource
            }
        }
    }
}
