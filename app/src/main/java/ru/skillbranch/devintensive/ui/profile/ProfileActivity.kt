package ru.skillbranch.devintensive.ui.profile


import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import ru.skillbranch.devintensive.R


class ProfileActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d("M_MainActivity", "onCreate")

    }
    // метод сохраняет состояние activity в Bundle
    //не вызывается при явном закрытии activity клавишей back
    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
    }

}

