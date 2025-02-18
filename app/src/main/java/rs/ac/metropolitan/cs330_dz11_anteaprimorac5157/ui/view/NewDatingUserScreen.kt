package rs.ac.metropolitan.cs330_dz11_anteaprimorac5157.ui.view

import SegmentedControl
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import rs.ac.metropolitan.common.Common
import rs.ac.metropolitan.common.DatingUser
import java.util.UUID

@Composable
fun NewDatingUserScreen(
    paddingValues: PaddingValues = PaddingValues(16.dp),
    submitDatingUser: (DatingUser) -> Unit,
    goBack: () -> Unit,
) {
    var username by remember { mutableStateOf(TextFieldValue("")) }
    var city by remember { mutableStateOf(TextFieldValue("")) }
    var country by remember { mutableStateOf(TextFieldValue("")) }
    var description by remember { mutableStateOf(TextFieldValue("")) }
    var selectedSex by rememberSaveable { mutableStateOf("") }
    var selectedInterestedIn by rememberSaveable { mutableStateOf("") }

    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
        shape = MaterialTheme.shapes.large,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(paddingValues)
        ) {
            item { Header(goBack) }
            item { InputField(username, { username = it }, "Username", "Enter your username") }
            item { InputField(city, { city = it }, "City", "Enter your city") }
            item { InputField(country, { country = it }, "Country", "Enter your country") }
            item {
                GenderSelection("Sex", selectedSex) { index ->
                    selectedSex = if (index == 0) "Male" else "Female"
                }
            }
            item {
                GenderSelection("Interested In", selectedInterestedIn) { index ->
                    selectedInterestedIn = if (index == 0) "Male" else "Female"
                }
            }
            item { DescriptionField(description, { description = it }) }
            item {
                SubmitButton {
                    submitDatingUser(
                        DatingUser(
                            id = UUID.randomUUID().toString(),
                            username = username.text,
                            avatar = Common.generateAvatarImage(username.text).toString(),
                            city = city.text,
                            country = country.text,
                            sex = selectedSex,
                            interestedIn = selectedInterestedIn,
                            description = description.text
                        )
                    )
                    goBack()
                }
            }
        }
    }
}

@Composable
private fun Header(goBack: () -> Unit) {
    Box(modifier = Modifier.fillMaxWidth()) {
        IconButton(
            modifier = Modifier
                .background(Color.Transparent)
                .scale(1.5f),
            onClick = goBack
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = "Back",
                tint = MaterialTheme.colorScheme.primary
            )
        }
        Text(
            text = "New Dating User",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
private fun InputField(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    label: String,
    placeholder: String
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        placeholder = { Text(placeholder) },
    )
}

@Composable
private fun DescriptionField(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp),
        maxLines = 5,
        textStyle = MaterialTheme.typography.bodyLarge,
    )
}

@Composable
private fun GenderSelection(
    title: String,
    selectedGender: String,
    onGenderSelected: (Int) -> Unit
) {
    val genders = listOf("Male", "Female")
    Text(title, style = MaterialTheme.typography.titleMedium)
    SegmentedControl(
        items = genders,
        defaultSelectedItemIndex = 0,
        onItemSelection = onGenderSelected
    )
}

@Composable
private fun SubmitButton(onClick: () -> Unit) {
    Button(onClick = onClick) {
        Text(text = "Submit")
    }
}

@Preview
@Composable
fun NewDatingUserScreenPreview() {
    NewDatingUserScreen(
        paddingValues = PaddingValues(0.dp),
        submitDatingUser = {},
        goBack = {}
    )
}