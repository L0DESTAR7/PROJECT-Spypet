package com.example.myapplication

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Build
import android.util.Log
import android.widget.DatePicker
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.myapplication.ui.theme.AuroMetalSaurus
import com.example.myapplication.ui.theme.CadetBlue
import com.example.myapplication.ui.theme.ColumbiaBlue
import com.example.myapplication.ui.theme.Fulvous
import com.example.myapplication.ui.theme.QRformColor
import com.example.myapplication.ui.theme.gradientBottom
import com.example.myapplication.ui.theme.gradientTop
import com.example.myapplication.ui.theme.montserrat
import com.example.myapplication.ui.theme.notosans_light
import com.example.myapplication.ui.theme.notosans_regular
import com.example.myapplication.ui.theme.notosans_semibold
import com.example.myapplication.ui.theme.orbitron_medium
import com.example.myapplication.ui.theme.strokeQR
import com.example.myapplication.ui.theme.textColorInput
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Calendar
import java.util.Date
import kotlin.coroutines.CoroutineContext


@RequiresApi(Build.VERSION_CODES.N)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun mainSettings(navHostController: NavHostController, token: String) {


    var counter by remember { mutableStateOf(0) }
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf("MONTHLY") }
    var visible by remember { mutableStateOf(false) }
    var elementTopDistance by remember { mutableStateOf(0) }
    var elementLeftDistance by remember { mutableStateOf(0) }
    var setTopDistance by remember { mutableStateOf(0) }
    var setLeftDistance by remember { mutableStateOf(0) }
    var time by remember { mutableStateOf("10 : 45") }
    var setTimed by remember { mutableStateOf(false) }
    var setDate by remember { mutableStateOf(false) }

    //local context
    val context = LocalContext.current

    //Calendar Parameters
    // Fetching the Local Context
    val mContext = LocalContext.current

    // Declaring integer values
    // for year, month and day
    val mYear: Int
    val mMonth: Int
    val mDay: Int
    var mDayofweek: Int


    // Initializing a Calendar
    val mCalendar = Calendar.getInstance()

    // Fetching current year, month and day
    mYear = mCalendar.get(Calendar.YEAR)
    mMonth = mCalendar.get(Calendar.MONTH)
    mDay = mCalendar.get(Calendar.DAY_OF_MONTH)


    mCalendar.time = Date()

    mDayofweek = 3

    // Declaring a string value to
    // store date in string format
    val mDate = remember { mutableStateOf("12/5/2023") }

    // Declaring DatePickerDialog and setting
    // initial values as current values (present year, month and day)
    val mDatePickerDialog = DatePickerDialog(
        mContext,
        { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
            mDate.value = "$mDayOfMonth/${mMonth+1}/$mYear"

        }, mYear, mMonth, mDay
    )


    // Time picker
    val mHour = mCalendar[Calendar.HOUR_OF_DAY]
    val mMinute = mCalendar[Calendar.MINUTE]

    // Value for storing time as a string
    val mTime = remember { mutableStateOf("12:45") }

    // Creating a TimePicker dialog
    val mTimePickerDialog = TimePickerDialog(
        mContext,
        {_, mHour : Int, mMinute: Int ->
            mTime.value = "$mHour:$mMinute"
        }, mHour, mMinute, true
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        gradientTop,
                        gradientBottom
                    )
                )
            )
    ) {
        // Outer container with margin
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(start = 16.dp, top = 16.dp)
        ) {
            // Row containing image and text
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                // Ellipse component with margin
                //Ellipse(modifier = Modifier.padding(end = 16.dp))
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "My Image",
                    modifier = Modifier
                        .size(40.dp)
                )
                // Text component with margin
                Text(
                    text = "Spypet",
                    color = ColumbiaBlue,
                    fontSize = 24.sp,
                    //fontWeight = FontWeight.W300,
                    fontFamily = montserrat,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
            }
            Row(
                modifier = Modifier
                    .height(120.dp)
                    .fillMaxWidth(0.88f)
                    .padding(start = 16.dp, top = 30.dp)
            ) {
                Divider(
                    color = ColumbiaBlue.copy(alpha = 0.3f), modifier = Modifier
                        .fillMaxHeight(0.6f)
                        .width(1.5.dp)
                )
                Column(
                    modifier = Modifier
                        .padding(start = 10.dp)
                        .fillMaxWidth(0.8f)
                        .fillMaxHeight(0.6f),
                    verticalArrangement = Arrangement.spacedBy((-11).dp)
                ) {
                    Text(
                        text = "Main Settings",
                        color = ColumbiaBlue,
                        fontFamily = notosans_semibold,
                        fontSize = 22.sp
                    )
                    Text(
                        text = "Spypet # 7042",
                        color = CadetBlue,
                        fontFamily = notosans_regular,
                        fontSize = 16.sp
                    )
                }
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = "Arrow",
                    tint = ColumbiaBlue,
                    modifier = Modifier.padding(top = 20.dp)
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxHeight(0.35f)
                    .fillMaxWidth()
                    .padding(start = 16.dp, top = 20.dp)
            ) {
                Column() {
                    Divider(
                        color = CadetBlue, modifier = Modifier
                            .fillMaxHeight(0.1f)
                            .width(1.5.dp)
                    )
                    Divider(
                        color = CadetBlue.copy(0.4f), modifier = Modifier
                            .fillMaxHeight(0.9f)
                            .width(1.5.dp)
                    )
                }
                Column(
                    modifier = Modifier.fillMaxHeight()
                ) {
                    Row() {
                        Image(
                            painter = painterResource(id = R.drawable.carre),
                            contentDescription = "carré",
                            modifier = Modifier
                                .fillMaxSize(0.1f)
                                .padding(start = 10.dp)
                        )
                        Text(
                            text = "Capacity",
                            fontSize = 16.sp,
                            color = ColumbiaBlue,
                            letterSpacing = 0.5.sp,
                            modifier = Modifier.padding(start = 13.dp)
                        )
                    }
                    Text(
                        text = "75%",
                        fontSize = 96.sp,
                        color = Fulvous,
                        modifier = Modifier
                            .fillMaxHeight(0.9f)
                            .padding(start = 10.dp, top = 49.dp),
                    )
                }
                Column(
                    modifier = Modifier.padding(start = 25.dp)
                ) {
                    Divider(
                        color = CadetBlue, modifier = Modifier
                            .fillMaxHeight(0.1f)
                            .width(1.5.dp)
                    )
                    Divider(
                        color = CadetBlue.copy(0.4f), modifier = Modifier
                            .fillMaxHeight(0.9f)
                            .width(1.5.dp)
                    )
                }
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Row(
                        Modifier.fillMaxWidth()
                    ) {

                        Image(
                            painter = painterResource(id = R.drawable.pet),
                            contentDescription = "pet",
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .padding(start = 10.dp)
                                .fillMaxSize(0.12f)
                        )
                        Text(
                            text = "Bowl",
                            fontSize = 16.sp,
                            color = ColumbiaBlue,
                            letterSpacing = 0.5.sp,
                            modifier = Modifier.padding(start = 13.dp)
                        )
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "More options",
                            tint = ColumbiaBlue,
                            modifier = Modifier.padding(start = 25.dp)
                        )
                    }
                    Text(
                        text = "239",
                        fontSize = 48.sp,
                        color = CadetBlue,
                        modifier = Modifier
                            .fillMaxHeight(0.9f)
                            .padding(start = 10.dp, top = 95.dp),
                    )
                }
            }
            Row(
                modifier = Modifier
                    .padding(start = 16.dp, top = 30.dp)
                    .fillMaxWidth()
                    .fillMaxHeight(0.25f)
            ) {
                Column() {
                    Divider(
                        color = CadetBlue, modifier = Modifier
                            .fillMaxHeight(0.23f)
                            .width(1.5.dp)
                    )
                    Divider(
                        color = CadetBlue.copy(0.4f), modifier = Modifier
                            .fillMaxHeight(0.77f)
                            .width(1.5.dp)
                    )
                }
                Column(
                    modifier = Modifier.fillMaxHeight()
                ) {
                    Row {
                        Image(
                            painter = painterResource(id = R.drawable.carre),
                            contentDescription = "carré",
                            alignment = Alignment.CenterStart,
                            modifier = Modifier
                                .fillMaxSize(0.23f)
                                .padding(start = 15.dp)
                        )
                        Box(
                            modifier = Modifier
                                .padding(start = 80.dp)
                                .clickable { visible = !visible; setTimed = false; setDate = false }
                                .onGloballyPositioned { coordinates ->
                                    elementTopDistance = coordinates.positionInWindow().y.toInt()
                                    elementLeftDistance = coordinates.positionInWindow().x.toInt()
                                }
                        ) {
                            Icon(
                                imageVector = Icons.Default.MoreVert,
                                contentDescription = "More options",
                                tint = ColumbiaBlue,
                            )
                        }
                    }
                    Text(
                        text = mTime.value.split(":")[0] + " : " + mTime.value.split(":")[1],
                        fontWeight = FontWeight.Black,
                        fontSize = 20.sp,
                        textAlign = TextAlign.End,
                        color = Fulvous,
                        letterSpacing = 10.sp,
                        modifier = Modifier
                            .fillMaxWidth(0.5f)
                            .padding(top = 5.dp)
                            .onGloballyPositioned { coordinates ->
                                setTopDistance = coordinates.positionInWindow().y.toInt()
                                setLeftDistance = coordinates.positionInWindow().x.toInt()
                            }
                    )
                    Text(
                        buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(
                                    color = AuroMetalSaurus,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            ) {
                                append("D: ")
                            }
                            withStyle(
                                style = SpanStyle(
                                    color = ColumbiaBlue,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            ) {
                                append(mDate.value.split("/")[0] + " ")
                            }
                            withStyle(
                                style = SpanStyle(
                                    color = AuroMetalSaurus,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            ) {
                                append("M: ")
                            }
                            withStyle(
                                style = SpanStyle(
                                    color = ColumbiaBlue,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            ) {
                                append(getMonthName(mDate.value.split("/")[1].toInt()).substring(0,3) + " ")
                            }
                            withStyle(
                                style = SpanStyle(
                                    color = AuroMetalSaurus,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            ) {
                                append("Y: ")
                            }
                            withStyle(
                                style = SpanStyle(
                                    color = ColumbiaBlue,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            ) {
                                append(mDate.value.split("/")[2])
                            }
                        },
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth(0.5f)
                            .padding(top = 4.dp)
                    )
                }
                Column(
                    modifier = Modifier.padding(start = 28.dp)
                ) {
                    Divider(
                        color = CadetBlue, modifier = Modifier
                            .fillMaxHeight(0.23f)
                            .width(1.5.dp)
                    )
                    Divider(
                        color = CadetBlue.copy(0.4f), modifier = Modifier
                            .fillMaxHeight(0.77f)
                            .width(1.5.dp)
                    )
                }
                Column(
                    //Modifier.background(Color.Black)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.carre),
                            contentDescription = "carré",
                            alignment = Alignment.CenterStart,
                            modifier = Modifier
                                .fillMaxSize(0.23f)
                                .padding(start = 15.dp)
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 39.dp)
                                .clickable { expanded = true }
                        ) {
                            Icon(
                                imageVector = Icons.Default.MoreVert,
                                contentDescription = "More options",
                                tint = ColumbiaBlue
                            )
                            if (expanded) {
                                val onDismiss = { expanded = false }

                                BackHandler(enabled = expanded, onBack = onDismiss)

                                DropdownMenu(
                                    expanded = expanded,
                                    onDismissRequest = onDismiss,
                                    modifier = Modifier
                                        .background(color = Color.DarkGray)
                                        .clip(shape = RoundedCornerShape(6.dp))
                                ) {
                                    DropdownMenuItem(
                                        onClick = {
                                            selectedOption = "WEEKLY"
                                            expanded = false
                                        },
                                        text = { Text("WEEKLY", color = ColumbiaBlue) },
                                    )
                                    DropdownMenuItem(onClick = {
                                        selectedOption = "MONTHLY"
                                        expanded = false
                                    }, text = {
                                        Text("MONTHLY", color = ColumbiaBlue)
                                    })
                                }
                            }
                        }
                    }
                    Text(
                        text = selectedOption,
                        textAlign = TextAlign.Start,
                        fontSize = 16.sp,
                        color = CadetBlue,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 32.dp, start = 15.dp)
                    )
                }
            }
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.padding(top = 20.dp)
            ) {
                IconButton(
                    onClick = {
                        counter = (counter + 10).coerceAtMost(500)
                    }
                ) {
                    Icon(
                        Icons.Default.Add, contentDescription = "Add",
                        tint = ColumbiaBlue
                    )
                }

                Box(
                    modifier = Modifier
                        .padding(top = 40.dp)
                        .fillMaxSize(0.7f)
                        .clip(shape = CircleShape)
                        .border(
                            width = 5.dp,
                            color = Color.Black,
                            shape = CircleShape
                        )
                        .aspectRatio(1f)
                        .background(
                            color = Color.Black, shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    val sweepAngle = (counter / 500f) * 360f

                    Box(
                        modifier = Modifier
                            .padding(5.dp)
                            .fillMaxSize()
                            .clip(shape = CircleShape)
                            .drawWithContent {
                                drawContent()

                                val diameter = size.minDimension
                                val strokeOffset = (size.width - diameter) / 2
                                val arcPath = Path().apply {
                                    addArc(
                                        Rect(
                                            offset = Offset(strokeOffset, 0f),
                                            size = Size(diameter, diameter)
                                        ),
                                        startAngleDegrees = 270f - sweepAngle / 2,
                                        sweepAngleDegrees = sweepAngle
                                    )
                                }
                                drawPath(
                                    path = arcPath,
                                    color = Fulvous,
                                    style = Stroke(
                                        width = 5.dp.toPx(),
                                        cap = StrokeCap.Round,
                                    )
                                )
                            }
                            .aspectRatio(1f)
                            .background(
                                brush = Brush.verticalGradient(
                                    colors = listOf(
                                        gradientTop,
                                        gradientBottom
                                    )
                                ), shape = CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            verticalArrangement = Arrangement.Center,
                        ) {
                            Text(
                                text = "" + counter + " g",
                                color = Fulvous,
                                fontFamily = orbitron_medium,
                                modifier = Modifier.padding(start = 30.dp)
                            )
                            Button(
                                onClick = {
                                    sendOrder("",mDate,mTime,counter,navHostController,token, context)
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = gradientTop)
                            ) {
                                Text(
                                    text = "Eject",
                                    fontSize = 19.sp,
                                    color = Fulvous,
                                    fontFamily = orbitron_medium
                                )
                            }
                        }
                    }
                }

                IconButton(
                    onClick = {
                        counter = (counter - 10).coerceAtLeast(0)
                    }
                ) {
                    Icon(
                        Icons.Default.ArrowBack, contentDescription = "Remove",
                        tint = ColumbiaBlue
                    )
                }
            }
        }
        Box(
            modifier = Modifier
                .offset { IntOffset(elementLeftDistance - 170, elementTopDistance); }
        ) {
            if (visible) {
                Box(
                    modifier = Modifier
                        .size(150.dp, 130.dp)
                        .clip(shape = RoundedCornerShape(11.dp))
                        .border(BorderStroke(1.dp, strokeQR), shape = RoundedCornerShape(11.dp))
                        .background(color = QRformColor)
                ) {
                    Column(

                        modifier = Modifier
                            .padding(start = 3.dp, top = 10.dp)
                            .fillMaxSize(0.95f)
                    )
                    {
                        Text(
                            text = "Set Time",
                            color = ColumbiaBlue,
                            fontSize = 12.sp,
                            modifier = Modifier
                                .clickable { setTimed = !setTimed; setDate = false }
                                .padding(start = 8.dp)
                        )
                        Text(
                            text = "Set Date",
                            color = ColumbiaBlue,
                            fontSize = 12.sp,
                            modifier = Modifier
                                .clickable { setDate = !setDate; setTimed = false }
                                .padding(start = 8.dp, top = 2.dp)
                        )
                        Divider(
                            color = strokeQR,
                            thickness = 1.dp,
                            modifier = Modifier.padding(vertical = 4.dp)
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { }
                                .background(color = Fulvous, shape = RoundedCornerShape(3.dp))
                        ) {
                            Text(
                                text = "View Orders",
                                color = ColumbiaBlue,
                                fontSize = 12.sp,
                                fontFamily = notosans_light,
                                modifier = Modifier.padding(start = 3.dp)
                            )
                            Icon(
                                imageVector = Icons.Default.List,
                                contentDescription = "orders",
                                tint = CadetBlue,
                                modifier = Modifier.padding(start = 47.dp)
                            )
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { println("hello") }

                        ) {
                            Text(
                                text = "24h format",
                                color = ColumbiaBlue,
                                fontSize = 12.sp,
                                fontFamily = notosans_light,
                                modifier = Modifier.padding(start = 3.dp)
                            )
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = "orders",
                                tint = CadetBlue,
                                modifier = Modifier.padding(start = 50.dp)
                            )
                        }
                        Divider(
                            color = strokeQR, thickness = 1.dp,
                            modifier = Modifier.padding(top = 5.dp)
                        )
                    }
                }
            }
        }

        Box(
            modifier = Modifier.offset { IntOffset(setLeftDistance, setTopDistance) }
        ) {
            if (setTimed) {
                setDate = false
                mTimePickerDialog.show()
                    /*OutlinedTextField(
                        value = time,
                        onValueChange = { texte: String -> time = texte },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            cursorColor = Fulvous,
                            textColor = CadetBlue,
                            unfocusedBorderColor = Color.Transparent,
                            focusedBorderColor = Color.Transparent
                        ),
                        singleLine = true,
                        maxLines = 1,
                        modifier = Modifier
                            .padding(start = 10.dp)
                            .size(width = 90.dp, height = 50.dp)
                            .background(textColorInput, RoundedCornerShape(14.dp))
                    )*/
            }
            if(setDate){
                setTimed = false
                mDatePickerDialog.show()
                /*Column(
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .padding(16.dp)
                ) {
                    TextField(
                        value = day,
                        onValueChange = { texte: String -> day = texte },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            cursorColor = Fulvous,
                            textColor = CadetBlue,
                            unfocusedBorderColor = Color.Transparent,
                            focusedBorderColor = Color.Transparent
                        ),
                        singleLine = true,
                        maxLines = 1,
                        label = { Text("Day", color = Color.White) },
                        modifier = Modifier
                            .size(width = 90.dp, height = 55.dp)
                            .background(textColorInput, RoundedCornerShape(14.dp))
                    )
                    TextField(
                        value = month,
                        onValueChange = { texte: String -> month = texte },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            cursorColor = Fulvous,
                            textColor = CadetBlue,
                            unfocusedBorderColor = Color.Transparent,
                            focusedBorderColor = Color.Transparent
                        ),
                        label = { Text("Month", color = Color.White) },
                        singleLine = true,
                        maxLines = 1,
                        modifier = Modifier
                            .padding(top = 4.dp)
                            .size(width = 90.dp, height = 55.dp)
                            .background(textColorInput, RoundedCornerShape(14.dp))
                    )
                    TextField(
                        value = year,
                        onValueChange = { texte: String -> year = texte },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            cursorColor = Fulvous,
                            textColor = CadetBlue,
                            unfocusedBorderColor = Color.Transparent,
                            focusedBorderColor = Color.Transparent
                        ),
                        singleLine = true,
                        maxLines = 1,
                        label = { Text("Year", color = Color.White) },
                        modifier = Modifier
                            .padding(top = 4.dp)
                            .size(width = 90.dp, height = 55.dp)
                            .background(textColorInput, RoundedCornerShape(14.dp))
                    )
                }*/
                 }
            }
        }

    }


fun sendOrder(option : String,Date:MutableState<String>,Time:MutableState<String>, counter:Int, navController: NavHostController, token: String,context: Context) {
    val retrofit = Retrofit.Builder()
        .baseUrl("http://192.168.100.10:8080/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val myApi = retrofit.create(MyApi::class.java)

    val requestBody = JSONObject().apply {
        put("type", "SPIN")
        put("params", "$counter")
        put("programmedAt", convertToIsoDate(Date.value) + Time.value + ":00Z" )
        put("periodicity", option)
    }

    Log.d("token",token)
    Log.d("request",requestBody.toString())

    val requestBodyObject =
        RequestBody.create(MediaType.parse("application/json"), requestBody.toString())

    GlobalScope.launch {
        val response = withContext(Dispatchers.IO) {
            myApi.sendOrder(requestBody = requestBodyObject, token = "Bearer $token")
        }

        withContext(Dispatchers.Main) {
            if (response.code() == 201) {
                val message = "Order placed successfully!"
                showToasst(context, message)
            }
            if (response.code() == 400) {
                val errorMessage = response.errorBody()?.string() ?: "An error occurred"
                showToasst(context, errorMessage)
                //navController.navigate("Devices")
            }
        }
    }
}

fun getMonthName(month: Int): String {
    return when (month) {
        Calendar.JANUARY -> "December"
        Calendar.FEBRUARY -> "January"
        Calendar.MARCH -> "February"
        Calendar.APRIL -> "March"
        Calendar.MAY -> "April"
        Calendar.JUNE -> "May"
        Calendar.JULY -> "June"
        Calendar.AUGUST -> "July"
        Calendar.SEPTEMBER -> "August"
        Calendar.OCTOBER -> "September"
        Calendar.NOVEMBER -> "October"
        Calendar.DECEMBER -> "November"
        else -> ""
    }
}

fun convertToIsoDate(dateString: String): String {
    val parts = dateString.split("/")
    val day = parts[0].padStart(2, '0')
    val month = parts[1].padStart(2, '0')
    val year = parts[2]
    return "$year-$month-$day" + "T"
}

fun showToasst(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}








