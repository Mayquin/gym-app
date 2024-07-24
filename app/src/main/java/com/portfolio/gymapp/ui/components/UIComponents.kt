@file:OptIn(ExperimentalComposeUiApi::class)

package pt.sibs.biometrics.modules.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.LocalTextInputService
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.portfolio.gymapp.R
import com.portfolio.gymapp.ui.theme.Blue
import com.portfolio.gymapp.ui.theme.ButtonTextStyle
import com.portfolio.gymapp.ui.theme.LightBlue
import com.portfolio.gymapp.ui.theme.boldTextStyle
import com.portfolio.gymapp.ui.theme.commonBlueTextStyle
import com.portfolio.gymapp.ui.theme.commonTextStyle
import com.portfolio.gymapp.ui.theme.customButtonColor
import com.portfolio.gymapp.ui.theme.customTextFieldColor

@Composable
fun ImageBase(imageId: Int, modifier: Modifier = Modifier, contentDescription: String = ""){
    Image(modifier= modifier, painter = painterResource(id = imageId), contentDescription = contentDescription)
}

@Composable
fun TextBase(textId: Int, modifier: Modifier = Modifier, textAlign: TextAlign = TextAlign.Center, textStyle: TextStyle = commonTextStyle()){
    Text(text = stringResource(id = textId),
        modifier = modifier,
        textAlign = textAlign,
        style = textStyle)
}

@Composable
fun RoundedButton(textId: Int, modifier: Modifier = Modifier, onClick: () -> Unit){
    OutlinedButton(onClick, modifier, colors = ButtonDefaults.buttonColors(
        containerColor = Blue,
        contentColor = Color.White
    ), border = BorderStroke(1.dp, Blue)
    ) {
        TextBase(textId = textId, textStyle = ButtonTextStyle)
    }
}

@Composable
fun RoundedStrokedButton(textId: Int, modifier: Modifier = Modifier, textColor : Color = Color.Black, onClick: () -> Unit){
    OutlinedButton(onClick, modifier, colors = ButtonDefaults.buttonColors(
        containerColor = LightBlue
    ), border = BorderStroke(1.dp, Blue)
    ) {
        TextBase( textId, textStyle = boldTextStyle(color = textColor))
    }
}


@Immutable @Stable
data class DropDownData(var selectedText: String, val content: List<String>, val action : (String)->Unit)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownMenu(dropdownData: DropDownData){
    var expanded by remember { mutableStateOf(false) }
    var text by remember { mutableStateOf(dropdownData.selectedText) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        }
    ) {
        CompositionLocalProvider(LocalTextInputService provides null) {
            CustomizedTextField(
                value = text,
                onValueChange = {
                    // For search
                },
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                colors = customTextFieldColor(),
                modifier = Modifier
                    .height(40.dp)
                    .fillMaxWidth()
                    .menuAnchor(),
                textStyle = TextStyle(fontSize = 16.sp)
            )
        }

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                // We shouldn't hide the menu when the user enters/removes any character
            }
        ) {
            dropdownData.content.forEach { item ->
                DropdownMenuItem(
                    modifier = Modifier
                        .background(Color.White)
                        .border(1.dp, Color.Gray, RectangleShape),
                    text = { Text(text = item) },
                    onClick = {
                        dropdownData.action(item)
                        text = item
                        expanded = false
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CustomDropdownSearching(dropdownData: DropDownData){
    var expanded by remember { mutableStateOf(false) }
    var text by remember { mutableStateOf(dropdownData.selectedText) }
    var searchingText by remember { mutableStateOf("") }
    var indexItemSelected by remember { mutableStateOf(0) }
    val listState = rememberLazyListState()
    val focusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()
    expanded = isFocused
    var content = remember { dropdownData.content }

    if(text.isNotEmpty()){
        indexItemSelected = content.indexOf(text)
    }

    ConstraintLayout {
        val (textField, menu) = createRefs()
        CustomizedTextField(value = searchingText, onValueChange = {
            searchingText = it
            content = dropdownData.content.let { content ->
                val newContent = mutableListOf<String>()
                if(searchingText.isNotEmpty()){
                    content.forEach {contentItem ->
                        if (contentItem.contains(searchingText, ignoreCase = true)){
                            newContent.add(contentItem)
                        }
                    }
                }

                if(newContent.isEmpty()){
                    return@let content
                }

                newContent
            }
        }, singleLine = true,
            placeholder = {
                Text(text, color = Color.Black)
            }, interactionSource = interactionSource,
            modifier = Modifier
                .constrainAs(textField) {
                    width = Dimension.matchParent
                }
                .height(40.dp)
                .onPreviewKeyEvent {
                    if (it.key == Key.Back) {
                        searchingText = ""
                        focusManager.clearFocus()
                    }
                    false
                },
            keyboardActions = KeyboardActions( onDone = {
                searchingText = ""
                focusManager.clearFocus()
            }))

        if (expanded){
            LazyColumn (state = listState,
                modifier = Modifier
                    .background(Color.LightGray)
                    .height(300.dp)
                    .constrainAs(menu) {
                        bottom.linkTo(textField.top, margin = 8.dp)
                    },
                verticalArrangement = Arrangement.spacedBy(1.dp)) {
                items(content){
                    Button(modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                        colors = customButtonColor(),
                        shape = RectangleShape, onClick = {
                            indexItemSelected = content.indexOf(it)
                            searchingText = ""
                            text = it
                            focusManager.clearFocus(force = true)
                            dropdownData.action(it)
                        }) {
                        Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween){
                            Text(it, modifier = Modifier, textAlign = TextAlign.Start)
                            if(it == text){
                                Image(modifier= Modifier
                                    .height(20.dp)
                                    // @Mike
                                    .width(20.dp), painter = painterResource(id = R.drawable.ic_launcher_background), contentDescription = "")
                            }
                        }
                    }
                }
            }

            LaunchedEffect("animateScrollItem"){
                listState.scrollToItem(index = indexItemSelected)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownSearchMenu(dropdownData: DropDownData){
    var expanded by remember { mutableStateOf(false) }
    var text by remember { mutableStateOf(dropdownData.selectedText) }
    var searchingText by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        }
    ) {

        CustomizedTextField(
            value = searchingText,
            onValueChange = {
                searchingText = it
            },
            placeholder = { Text(text = text, style = TextStyle(color = Color.Gray, fontSize = 16.sp)) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            colors = customTextFieldColor(),
            modifier = Modifier
                .height(40.dp)
                .menuAnchor(),
            singleLine = true,
            textStyle = TextStyle(fontSize = 16.sp),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = {
                    searchingText = ""
                    expanded = false
                    focusManager.clearFocus()
                    keyboardController?.hide()
                })
        )

        val filteredOptions =
            dropdownData.content.filter { it.contains(searchingText, ignoreCase = true) }.let {
                it.ifEmpty { dropdownData.content }
            }

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = {  }
        ) {
            filteredOptions.forEach { item ->
                DropdownMenuItem(
                    text = { Text(text = item) },
                    onClick = {
                        dropdownData.action(item)
                        text = item
                        searchingText = ""
                        expanded = false
                        focusManager.clearFocus()
                        keyboardController?.hide()
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                )
            }
        }
    }
}
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TextFieldBase(modifier: Modifier = Modifier, initialValue:String, hintTextId: Int, onValueChange: (String) -> Unit) {
    var text by remember { mutableStateOf(initialValue) }
    val focusManager = LocalFocusManager.current

    CustomizedTextField(value = text, onValueChange = {
        text = it
        onValueChange(it)
    }, placeholder = {
        TextBase(textId = hintTextId, textStyle = TextStyle(color = Color.Gray))
    }, colors = customTextFieldColor(),
        modifier = modifier
            .height(40.dp)
            .fillMaxWidth()
            .onPreviewKeyEvent {
                if (it.key == Key.Back) {
                    focusManager.clearFocus()
                }
                false
            },
        keyboardActions = KeyboardActions( onDone = {
            focusManager.clearFocus()
        })
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomizedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = TextStyle(fontSize = 16.sp),
    label: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    supportingText: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape = TextFieldDefaults.outlinedShape,
    colors: TextFieldColors = customTextFieldColor()
) {
    BasicTextField(
        value = value,
        modifier = modifier
            .defaultMinSize(
                minWidth = TextFieldDefaults.MinWidth,
                minHeight = TextFieldDefaults.MinHeight
            ),
        onValueChange = onValueChange,
        enabled = enabled,
        readOnly = readOnly,
        textStyle = textStyle,
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        interactionSource = interactionSource,
        singleLine = singleLine,
        maxLines = maxLines,
        decorationBox = @Composable { innerTextField ->
            // places leading icon, text field with label and placeholder, trailing icon
            TextFieldDefaults.TextFieldDecorationBox(
                value = value,
                visualTransformation = visualTransformation,
                innerTextField = innerTextField,
                placeholder = placeholder,
                label = label,
                leadingIcon = leadingIcon,
                trailingIcon = trailingIcon,
                supportingText = supportingText,
                shape = shape,
                singleLine = singleLine,
                enabled = enabled,
                isError = isError,
                interactionSource = interactionSource,
                colors = colors,
                contentPadding = PaddingValues(start = 5.dp),
                container = {
                    TextFieldDefaults.OutlinedBorderContainerBox(
                        enabled,
                        isError,
                        interactionSource,
                        colors,
                        shape
                    )
                }
            )
        }
    )
}

@Composable
fun CustomAlert(titleId: Int, descriptionId: Int, buttonTextId: Int, buttonClick: () -> Unit, onDismissRequest: () -> Unit){
    AlertDialog(
        containerColor = LightBlue,
        onDismissRequest = onDismissRequest,
        title = {
            TextBase(titleId, textStyle = commonBlueTextStyle(fontSize = 20), modifier = Modifier.fillMaxWidth())
        },
        text = {
            TextBase(descriptionId, textStyle = commonTextStyle())
        },
        confirmButton = {
            Row {
                RoundedButton(buttonTextId, onClick = buttonClick)
            }
        }
    )
}
