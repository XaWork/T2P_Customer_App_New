package me.taste2plate.app.customer.presentation.screens.address

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.google.android.gms.maps.model.LatLng
import me.taste2plate.app.customer.data.Status
import me.taste2plate.app.customer.presentation.navigation.Screens
import me.taste2plate.app.customer.presentation.screens.countries
import me.taste2plate.app.customer.presentation.theme.ExtraHighPadding
import me.taste2plate.app.customer.presentation.theme.LowSpacing
import me.taste2plate.app.customer.presentation.theme.ScreenPadding
import me.taste2plate.app.customer.presentation.theme.T2PCustomerAppTheme
import me.taste2plate.app.customer.presentation.widgets.AppButton
import me.taste2plate.app.customer.presentation.widgets.AppDropDown
import me.taste2plate.app.customer.presentation.widgets.AppRadioButton
import me.taste2plate.app.customer.presentation.widgets.AppScaffold
import me.taste2plate.app.customer.presentation.widgets.AppTextField
import me.taste2plate.app.customer.presentation.widgets.AppTopBar
import me.taste2plate.app.customer.presentation.widgets.ShowLoading
import me.taste2plate.app.customer.presentation.widgets.SpaceBetweenRow
import me.taste2plate.app.customer.presentation.widgets.showToast

@Composable
fun AddEditAddressScreen(
    viewModel: AddressViewModel,
    latLng: LatLng? = null,
    onNavigateToLocationScreen: (screen: String) -> Unit,
    onNavigateToHomeScreen: () -> Unit
) {
    val state = viewModel.state

    LaunchedEffect(latLng != null) {
        viewModel.latLng = latLng
    }

    Log.e("AddEditScreen", "Lat long is $latLng")

    LaunchedEffect(state.stateList.isEmpty()) {
        viewModel.onEvent(AddressEvents.SetData)
    }

    LaunchedEffect(state) {
        when {
            state.isError -> {
                showToast(state.message ?: "")
                viewModel.onEvent(AddressEvents.UpdateState)
            }

            state.addAddressResponse != null && state.addAddressResponse.status == Status.success.name-> {
                onNavigateToHomeScreen()
            }

            state.editAddressResponse != null  && state.editAddressResponse.status == Status.success.name-> {
                onNavigateToHomeScreen()
            }
        }
    }

    AppScaffold(
        topBar = {
            AppTopBar(
                title = if (viewModel.addressIndex != -1) "Edit Address" else "Add Address"
            ) {}
        }
    ) {
        ContentAddEditAddressScreen(viewModel) { onNavigateToLocationScreen(Screens.AddEditAddressScreen.route) }
    }
}

@Composable
fun ContentAddEditAddressScreen(
    viewModel: AddressViewModel,
    onNavigateToLocationScreen: () -> Unit
) {
    val state = viewModel.state
    var countryExpanded by remember { mutableStateOf(false) }
    var stateExpanded by remember { mutableStateOf(false) }
    var cityExpanded by remember { mutableStateOf(false) }
    var pinCodeExpanded by remember { mutableStateOf(false) }

    var selectedText by remember { mutableStateOf(countries[0]) }
    var pinCode by remember { mutableStateOf(countries[0]) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = ScreenPadding,
                start = ScreenPadding,
                end = ScreenPadding
            )
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(bottom = ExtraHighPadding + ExtraHighPadding)
        ) {
            AppTextField(
                value = viewModel.fullName.value, onValueChanged = {
                    viewModel.fullName.value = it
                },
                hint = "Full name"
            )


            AppTextField(
                value = viewModel.phone.value, onValueChanged = {
                    if (it.length <= 10) viewModel.phone.value = it
                },
                hint = "Phone",
                keyboardType = KeyboardType.Phone
            )

            AppTextField(
                value = viewModel.address1.value, onValueChanged = {
                    viewModel.address1.value = it
                },
                hint = "House No./Building Name"
            )

            AppTextField(
                value = viewModel.address2A.value, onValueChanged = {
                    viewModel.address2A.value = it
                },
                hint = "Road no/Area/Colony"
            )

            AppDropDown(
                countryExpanded,
                hint = "Country",
                items = countries,
                onExpandedChange = {
                    countryExpanded = !countryExpanded
                },
                selectedText = selectedText,
                onTextChanged = {
                    selectedText = it
                }
            )

            AppDropDown(
                stateExpanded,
                hint = "State",
                items = state.stateList.map { it.name },
                onExpandedChange = {
                    stateExpanded = !stateExpanded
                },
                selectedText = viewModel.stateA.value,
                onTextChanged = {
                    viewModel.stateA.value = it
                    viewModel.onEvent(AddressEvents.GetCityList)
                }
            )

            AppDropDown(
                cityExpanded,
                hint = "City",
                items = state.cityList.map { it.name },
                onExpandedChange = {
                    cityExpanded = !cityExpanded
                },
                selectedText = viewModel.cityA.value,
                onTextChanged = {
                    viewModel.cityA.value = it
                    viewModel.onEvent(AddressEvents.GetZipList)
                }
            )

            SpaceBetweenRow(item1 = {
                AppDropDown(
                    pinCodeExpanded,
                    items = state.zipList.map { it.name },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = LowSpacing)
                        .weight(1f),
                    onExpandedChange = {
                        pinCodeExpanded = !pinCodeExpanded
                    },
                    hint = "PinCode",
                    selectedText = viewModel.pincodeA.value,
                    onTextChanged = {
                        viewModel.pincodeA.value = it
                    }
                )
            }) {
                AppTextField(
                    modifier = Modifier
                        .padding(start = LowSpacing)
                        .weight(1f),
                    value = viewModel.landmarkA.value, onValueChanged = {
                        viewModel.landmarkA.value = it
                    },
                    hint = "Landmark(Optional)"
                )
            }

            AppRadioButton(
                viewModel.addressTypes,
                viewModel.addressType.value.text,
                onOptionSelected = {
                    viewModel.addressType.value = it
                }
            )
        }

        val buttonText = if (viewModel.latLng == null) "Select Location" else {
            if (viewModel.addressIndex != -1) "Edit" else "Add"
        }

        if (state.isLoading)
            ShowLoading()
        else
            AppButton(
                text = buttonText,
                modifier = Modifier.align(Alignment.BottomCenter)
            ) {
                when (buttonText) {
                    "Select Location" -> {
                        onNavigateToLocationScreen()
                    }

                    "Edit" -> {
                        viewModel.onEvent(AddressEvents.EditAddress)
                    }

                    "Add" -> {
                        viewModel.onEvent(AddressEvents.AddAddress)
                    }
                }
            }
    }
}


@Preview
@Preview(name = "Dark Preview", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun AddEditAddressScreenPreview() {
    T2PCustomerAppTheme {
        //  AddEditAddressScreen()
    }
}