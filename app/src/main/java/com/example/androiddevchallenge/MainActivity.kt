/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.*
import com.example.androiddevchallenge.model.Gender
import com.example.androiddevchallenge.model.Puppy
import com.example.androiddevchallenge.ui.theme.MyTheme
import com.example.androiddevchallenge.ui.theme.typography


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTheme {
                MyApp()
            }
        }
    }
}

val puppies = List(5) { index ->
    Puppy(
        id = index,
        name = "こいぬ #${index + 1}",
        gender = Gender.MALE,
        age = "1歳",
        description = "寒さにつよい。",
        photo = R.drawable.red_panda
    )
}

// Start building your app here!
@Composable
fun MyApp() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "main") {
        composable("main") {
            MainScreen(navController)
        }
        composable("detail/{puppyId}",
            arguments = listOf(navArgument("puppyId") { type= NavType.IntType })
        ) {
            DetailScreen(id = it.arguments?.getInt("puppyId"))
        }
    }
}

@Composable
fun MainScreen(navController: NavController?) {
    Surface(color = MaterialTheme.colors.background) {
        LazyColumn {
            items(puppies) { puppy ->
                Row(
                    modifier = Modifier
                        .clickable {
                            navController?.navigate("detail/${puppy.id}")
                        }
                ) {
                    PuppyListCell(puppy = puppy)
                }
            }
        }
    }
}

@Composable
fun PuppyListCell(puppy: Puppy) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Image(
            painter = painterResource(id = puppy.photo),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .height(80.dp)
                .width(80.dp)
                .clip(shape = RoundedCornerShape(40.dp)),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(
            modifier = Modifier.align(Alignment.CenterVertically)
        ) {
            Text(puppy.name, style = typography.h6)
            Row {
                Text(puppy.gender.toString(), style = typography.body2)
                Text(puppy.age, style = typography.body2)
            }
        }
    }
}

@Preview("List - Light", widthDp = 360, heightDp = 640)
@Composable
fun LightPreview() {
    MyTheme {
        MainScreen(null)
    }
}

@Preview("List - Dark", widthDp = 360, heightDp = 640)
@Composable
fun DarkPreview() {
    MyTheme(darkTheme = true) {
        MainScreen(null)
    }
}

@Preview("ListCell - Light", widthDp = 360, heightDp = 112)
@Composable
fun CellLightPreview() {
    Surface(color = MaterialTheme.colors.background) {
        PuppyListCell(
            Puppy(
                id = 0,
                name = "なまえ",
                gender = Gender.MALE,
                age = "3ヶ月",
                description = "これは説明文",
                photo = R.drawable.red_panda
            )
        )
    }
}

// region Detail

@Composable
fun DetailScreen(id: Int?) {
    Surface(color = MaterialTheme.colors.background) {
        PuppyDetail(id = id)
    }
}

@Composable
fun PuppyDetail(id: Int?) {

    val puppy = puppies.firstOrNull { it.id == id }

    val typography = MaterialTheme.typography

    if (puppy == null) {
        Text("No Data")
        return
    }

    Column {
        Image(
            painter = painterResource(id = puppy.photo),
            contentDescription = null,
            modifier = Modifier
                .height(180.dp)
                .fillMaxWidth(),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(puppy.name, style = typography.h4)
        Spacer(modifier = Modifier.height(16.dp))
        Row {
            Text(puppy.gender.toString(), style = typography.body2)
            Text(puppy.age, style = typography.body2)
        }
        Text(puppy.description, style = typography.body2)
    }
}

@Preview("Detail - Light", widthDp = 360, heightDp = 640)
@Composable
fun DetailLightPreview() {
    MyTheme {
        DetailScreen(id = 1)
    }
}

// endregion