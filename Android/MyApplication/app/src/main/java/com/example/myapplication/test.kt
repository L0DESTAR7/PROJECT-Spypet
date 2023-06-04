package com.example.myapplication

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.CadetBlue
import com.example.myapplication.ui.theme.ColumbiaBlue
import com.example.myapplication.ui.theme.Fulvous
import com.example.myapplication.ui.theme.QRformColor
import com.example.myapplication.ui.theme.notosans_light
import com.example.myapplication.ui.theme.strokeQR

@Composable
fun test(){
    Box(
        modifier = Modifier
            .size(150.dp, 130.dp)
            .clip(shape = RoundedCornerShape(25.dp))
            .border(BorderStroke(1.dp, strokeQR), shape = RoundedCornerShape(25.dp))
            .background(color = QRformColor)
    ){
       Column (
           modifier = Modifier
               .padding(start = 10.dp, top = 10.dp)
               .fillMaxSize(0.8f)
       )
       {
           Text(
               text = "Set Time",
               color = ColumbiaBlue,
               fontSize = 12.sp,
               modifier = Modifier
                   .clickable { println("allo") }
                   .padding(start = 8.dp)
           )
           Text(
               text = "Set Date",
               color = ColumbiaBlue,
               fontSize = 12.sp,
               modifier = Modifier
                   .clickable {  }
                   .padding(start = 8.dp, top = 2.dp)
           )
           Divider(color = strokeQR, thickness = 1.dp, modifier = Modifier.padding(vertical = 4.dp))
           Row(
               modifier = Modifier
                   .fillMaxWidth()
                   .clickable { println("hello") }
                   .background(color = Fulvous)
                   .clip(shape = RoundedCornerShape(15.dp))
           ){
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

           ){
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
           Divider(color = strokeQR, thickness = 1.dp,
           modifier = Modifier.padding(top = 5.dp)
               )
       }
    }
}