package com.example.day1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Height
import androidx.compose.material.icons.filled.MonitorWeight
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.day1.ui.theme.Day1Theme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Day1Theme {
                BMICalculator()
            }
        }
    }
}

@Composable
fun BMICalculator() {
    var height by remember { mutableStateOf(TextFieldValue()) }
    var weight by remember { mutableStateOf(TextFieldValue()) }
    var heightUnit by remember { mutableStateOf("cm") }
    var weightUnit by remember { mutableStateOf("kg") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                OutlinedTextField(
                    value = height,
                    onValueChange = { height = it },
                    modifier = Modifier.weight(1f),
                    label = { Text("Height") },
                    leadingIcon = { Icon(imageVector = Icons.Default.Height, contentDescription = null)},
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                )
                Button(onClick = { heightUnit = if (heightUnit == "cm") "feet" else "cm" }) {
                    Text(text = heightUnit)
                }
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                OutlinedTextField(
                    value = weight,
                    onValueChange = { weight = it },
                    modifier = Modifier.weight(1f),
                    label = { Text("Weight") },
                    leadingIcon = { Icon(imageVector = Icons.Default.MonitorWeight, contentDescription = null)},
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                )
                Button(onClick = { weightUnit = if (weightUnit == "kg") "pound" else "kg" }) {
                    Text(text = weightUnit)
                }
            }

            if (height.text.isNotEmpty() && weight.text.isNotEmpty()) {
                val bmi = calculateBMI(height.text.toFloatOrNull(), weight.text.toFloatOrNull(), heightUnit, weightUnit)
                if (bmi != null) {
                    Text(text = "BMI: $bmi", style = MaterialTheme.typography.bodyMedium, color = Color.Black)
                    val message = getBMIMessage(bmi)
                    Text(text = message, style = MaterialTheme.typography.bodyLarge, color = Color.Black, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

fun calculateBMI(height: Float?, weight: Float?, heightUnit: String, weightUnit: String): Float? {
    if (height == null || weight == null) return null

    val heightInMeters = if (heightUnit == "cm") height / 100 else height / 3.281f // conversion to meters or feet
    val weightInKg = if (weightUnit == "kg") weight else weight * 0.453592f // conversion to kg

    return weightInKg / (heightInMeters * heightInMeters)
}

fun getBMIMessage(bmi: Float): String {
    return when {
        bmi < 18.5 -> "You are underweight"
        bmi in 18.5..24.9 -> "You are normal weight"
        bmi in 25.0..29.9 -> "You are overweight"
        bmi >= 30 -> "You are obsessed"
        else -> "invalid input"
    }
}

