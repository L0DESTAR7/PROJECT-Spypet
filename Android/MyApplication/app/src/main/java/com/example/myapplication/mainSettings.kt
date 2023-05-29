package com.example.myapplication

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.myapplication.ui.theme.AuroMetalSaurus
import com.example.myapplication.ui.theme.CadetBlue
import com.example.myapplication.ui.theme.ColumbiaBlue
import com.example.myapplication.ui.theme.Fulvous
import com.example.myapplication.ui.theme.gradientBottom
import com.example.myapplication.ui.theme.gradientTop
import com.example.myapplication.ui.theme.montserrat
import com.example.myapplication.ui.theme.notosans_regular
import com.example.myapplication.ui.theme.notosans_semibold

@Composable
fun mainSettings(navHostController: NavController){

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
            ){
                Divider(color = ColumbiaBlue.copy(alpha = 0.3f), modifier = Modifier
                    .fillMaxHeight(0.6f)
                    .width(1.5.dp))
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
            Row (
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
                   Row(){
                       Image(
                           painter = painterResource(id = R.drawable.carre),
                           contentDescription = "carré",
                           modifier = Modifier
                               .fillMaxSize(0.1f)
                               .padding(start = 10.dp)
                       )
                       Text(text = "Capacity",  fontSize = 16.sp, color = ColumbiaBlue, letterSpacing = 0.5.sp, modifier = Modifier.padding(start = 13.dp))
                       Icon(
                           imageVector = Icons.Default.MoreVert,
                           contentDescription = "More options",
                           tint = ColumbiaBlue,
                           modifier = Modifier.padding(start = 50.dp)
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
                    ){
                        Image(
                            painter = painterResource(id = R.drawable.pet),
                            contentDescription = "pet",
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .padding(start = 10.dp)
                                .fillMaxSize(0.12f)
                        )
                        Text(text = "Bowl",  fontSize = 16.sp, color = ColumbiaBlue, letterSpacing = 0.5.sp, modifier = Modifier.padding(start = 13.dp))
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
            ){
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
                    Row(
                    ){
                        Image(
                            painter = painterResource(id = R.drawable.carre),
                            contentDescription = "carré",
                            alignment = Alignment.CenterStart,
                            modifier = Modifier
                                .fillMaxSize(0.23f)
                                .padding(start = 15.dp)
                        )
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "More options",
                            tint = ColumbiaBlue,
                            modifier = Modifier.padding(start = 80.dp)
                        )

                    }
                    Text(
                        text = "10 : 45",
                        fontWeight = FontWeight.Black,
                        fontSize = 20.sp,
                        textAlign = TextAlign.End,
                        color = Fulvous,
                        letterSpacing = 10.sp,
                        modifier = Modifier.fillMaxWidth(0.5f)
                            .padding(top = 5.dp)
                        )
                    Text(
                        buildAnnotatedString {
                            withStyle(style = SpanStyle(color = AuroMetalSaurus, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)) {
                                append("D: ")
                            }
                            withStyle(style = SpanStyle(color = ColumbiaBlue, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)) {
                                append("Tue ")
                            }
                            withStyle(style = SpanStyle(color = AuroMetalSaurus, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)) {
                                append("M: ")
                            }
                            withStyle(style = SpanStyle(color = ColumbiaBlue, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)) {
                                append("April ")
                            }
                            withStyle(style = SpanStyle(color = AuroMetalSaurus, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)) {
                                append("Y: ")
                            }
                            withStyle(style = SpanStyle(color = ColumbiaBlue, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)) {
                                append("2023")
                            }
                        },
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(0.5f).padding(top = 4.dp)
                    )
                }
                Column(
                    modifier = Modifier.padding(start = 35.dp)
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
                ){
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ){
                        Image(
                            painter = painterResource(id = R.drawable.carre),
                            contentDescription = "carré",
                            alignment = Alignment.CenterStart,
                            modifier = Modifier
                                .fillMaxSize(0.23f)
                                .padding(start = 15.dp)
                        )
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "More options",
                            tint = ColumbiaBlue,
                            modifier = Modifier.fillMaxWidth().padding(start = 39.dp)
                        )
                    }
                    Text(text = "MONTHLY", textAlign = TextAlign.Start, fontSize = 16.sp, color = CadetBlue,
                        modifier = Modifier.fillMaxSize()
                        .padding(top = 35.dp, start = 15.dp)
                    )
                }
            }

            Box(
                modifier = Modifier
                    .padding(70.dp)
                    .fillMaxSize()
                    .clip(shape = CircleShape)
                    .border(
                        width = 5.dp,
                        color = Color.Black,
                        shape = CircleShape,
                    )
                    .border(BorderStroke(1.dp, Fulvous))
                    .aspectRatio(1f)
                    .background(brush = Brush.verticalGradient(
                        colors = listOf(
                            gradientTop,
                            gradientBottom
                        )
                    ), shape = CircleShape),
                contentAlignment = Alignment.Center
            ){

            }
                }
            }
}
