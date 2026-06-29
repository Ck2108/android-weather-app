package com.weather.app.ui.components

// =============================================================
// SearchBar.kt — "The Search Box at the Top"
// =============================================================
// This is a text field where users type a city name.
//
// 🧱 LEGO analogy: This is a single LEGO piece — a search bar.
//    We build it once, and can use it anywhere in the app.
//
// NEW CONCEPTS YOU'LL LEARN HERE:
//
//   1. "Modifier" — Think of it as DECORATIONS for a LEGO block.
//      Modifier.padding(16.dp) = "Add 16dp of space around me"
//      Modifier.fillMaxWidth() = "Stretch me to fill the whole width"
//      You can CHAIN them: Modifier.padding(8.dp).fillMaxWidth()
//
//   2. "dp" vs "sp"
//      dp = "density-independent pixels" — for sizes and spacing
//      sp = "scalable pixels" — ONLY for text (respects user font settings)
//
//   3. "@Composable fun" = A function that creates a piece of UI.
//      Regular functions return data. Composable functions DRAW on screen.
//
//   4. "OutlinedTextField" — A pre-built text input box from Material3.
//      Google already built it for us! We just customize it.
// =============================================================

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.weather.app.ui.theme.TextOnSky
import com.weather.app.ui.theme.TextOnSkyMuted
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp

// =============================================================
// SearchBar Composable
// =============================================================
// Parameters explained (like ingredients for this LEGO block):
//
//   query: String
//     → The current text in the search box.
//       Example: If user typed "Chicago", query = "Chicago"
//
//   onQueryChange: (String) -> Unit
//     → A CALLBACK — "what to do when the user types something"
//       This is like giving the LEGO block a walkie-talkie to
//       report back: "Hey! The user just typed 'Chi'!"
//       The -> Unit means "I don't return anything, I just notify"
//
//   modifier: Modifier
//     → Optional decorations from whoever uses this component.
//       "modifier = Modifier" means "if nobody specifies one, use a blank one"
// =============================================================
@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: () -> Unit = {},       // NEW! Called when user presses Enter/Search on keyboard
    modifier: Modifier = Modifier
) {
    // OutlinedTextField = A text input with a border around it
    // Google built this for us in Material3. We just configure it.
    OutlinedTextField(
        // The current text showing in the box
        value = query,

        // When the user types, call onQueryChange with the new text
        // "it" is Kotlin shorthand for "the single parameter"
        // So when user types "Chi", it = "Chi"
        onValueChange = { onQueryChange(it) },

        // Placeholder = grey text shown when the box is empty
        placeholder = {
            Text(
                text = "Search city...",
                color = TextOnSkyMuted
            )
        },

        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search",
                tint = TextOnSkyMuted
            )
        },

        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = TextOnSky,
            unfocusedTextColor = TextOnSky,
            focusedBorderColor = TextOnSkyMuted,
            unfocusedBorderColor = TextOnSkyMuted.copy(alpha = 0.5f),
            cursorColor = TextOnSky
        ),

        // Only one line (not a multi-line text area)
        singleLine = true,

        // Keyboard options — show a "Search" button instead of "Enter" on the keyboard
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),

        // When user presses the Search button on keyboard, call onSearch
        keyboardActions = KeyboardActions(onSearch = { onSearch() }),

        // Rounded corners (16dp radius = nicely rounded)
        shape = RoundedCornerShape(16.dp),

        // Modifier chain: take any external modifiers AND fill full width
        modifier = modifier.fillMaxWidth()
    )
}
