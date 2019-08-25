package ru.skillbranch.devintensive.ui.profile


import android.graphics.ColorFilter
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_profile.*
import ru.skillbranch.devintensive.R
import ru.skillbranch.devintensive.models.Profile
import ru.skillbranch.devintensive.viewmodels.ProfileViewModel


class ProfileActivity : AppCompatActivity() {


    //свойства класса (или поле?)
    companion object {
        const val IS_EDIT_MODE = "IS_EDIT_MODE"
    }

    private lateinit var viewModel: ProfileViewModel
    var isEditMode = false
    lateinit var viewFields: Map<String, TextView>

    //////////////////////////////
    // ? Что за \b ?
    private val validRegEx = "(https://|www.|https://www.)+github.com/(?!enterprise|features|topics|collections|trending|events|marketplace|pricing|nonprofit|customerstories|security|login|join)".toRegex()
  //  private val validRegEx = "www".toRegex()

    private val UnvalidRegEx = "enterprise|features|topics|collections|trending|events|marketplace|pricing|nonprofit|customerstories|security|login|join".toRegex()
var containsMatchIn = false
var matches = false



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Указываем на activity, которое хотим использвать по умолчанию
        setContentView(R.layout.activity_profile)
        //Внимание на последовательность.
        InitViews(savedInstanceState)
        //Здесь перебираются Views, которые проинициализировали предыдущим методом
        initViewModel()

        Log.d("M_ProfileActivity", "onCreate")

    }

    // метод сохраняет состояние activity в Bundle
    //не вызывается при явном закрытии activity клавишей back
    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        // сохранение состояния иконки редактирования при перевороте экрана
        outState?.putBoolean(IS_EDIT_MODE, isEditMode)
    }


    private fun initViewModel() {
        //получение viewModel из ViewModelProviders.of (ТЕКУЩАЯ АКТИВИТИ).get(КЛАСС, КОТОРЫЙ ХОТИМ ПОЛУЧИТЬ)
        viewModel = ViewModelProviders.of(this).get(ProfileViewModel::class.java)
        // Observer{updateUI(it) - Реализация интерфейса Observer.
        viewModel.getProfileData().observe(this, Observer { updateUI(it) })
        viewModel.getTheme().observe(this, Observer { updateTheme(it) })
    }

    private fun updateTheme(mode: Int) {
        Log.d("M_ProfileActivity", "updateTheme")
        delegate.setLocalNightMode(mode)
    }

    //Реализация соответствует единственному методу интрефейса Observer
    private fun updateUI(profile: Profile) {
        profile.toMap().also {
            //перебираем все viewFields
            for ((k, v) in viewFields) {
                //присвоим им в цикле значения, получаемые из профайла
                v.text = it[k].toString()
            }
        }
    }

    //чтобы позже через цикл привязывать значения к соотв-м view
    private fun InitViews(savedInstanceState: Bundle?) {

        viewFields = mapOf(
                "nickName" to tv_nick_name,
                "rank" to tv_rank,
                "firstName" to et_first_name,
                "lastName" to et_last_name,
                "about" to et_about,
                "repository" to et_repository,
                "rating" to tv_rating,
                "respect" to tv_respect
        )
        // восстанавливаем значение из Bundle (для иконки редактирования при перевороте)
        isEditMode = savedInstanceState?.getBoolean(IS_EDIT_MODE, false) ?: false
        // вызываем текущий режим в зависимости от того, какое значение восстановили из Bundle
        showCurrentMode(isEditMode)


        //классический случай без лямбды
        //object : View.OnClickListener - object имплементирует интрефейс View.OnClickListener
        //поэтому переопределяется метод onClick
        /* btn_edit.setOnClickListener(object : View.OnClickListener {
             override fun onClick(v: View?) {
                 isEditMode = !isEditMode
                 showCurrentMode(isEditMode)
             }
         })*/


        //Лямбда на клик. Без имплементации интрефейса кликЛисенера
        // !!! Если единственный аргумент метода - это лямбда, то её можно вынести за скобки
        // !!! it - название для лямбды с единственным аргументом
        btn_edit.setOnClickListener(View.OnClickListener {
            //инверсия значения
            if (isEditMode) saveProfileInfo()
            isEditMode = !isEditMode
            showCurrentMode(isEditMode)
        })

        btn_switch_theme.setOnClickListener(View.OnClickListener {
            viewModel.switchTheme()
        })

        // Отслеживание ввода в поле et_repository
        et_repository.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {

                containsMatchIn = validRegEx.containsMatchIn(et_repository.text.toString())
                matches = validRegEx.matches(et_repository.text.toString())




                println("containsMatchIn  $containsMatchIn")
                println("matches  $matches")

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        //----private fun InitViews(savedInstanceState: Bundle?)
    }



    fun showCurrentMode(isEdit: Boolean) {
        //проверить, содержит ли набор из setOf() элемент, принимаемый на вход (НЕ ПОНЯТНО)
        //key - элемент "Ключ" из пары ключ-значение
        val info = viewFields.filter { setOf("firstName", "lastName", "about", "repository").contains(it.key) }
        //неиспользуемое значение пары заменяется нижним подчёркиванием
        //FOR обеспечивает перебор всех значений, поставляемых итератором.
        for ((_, v) in info) {
            v as EditText
            v.isFocusable = isEdit
            v.isFocusableInTouchMode = isEdit
            v.isEnabled = isEdit
            v.background.alpha = if (isEdit) 255 else 0
        }
        //Скрывать иконку глаза в режиме редактирования
        //View можеть быть видимая, невидимая и GONE - когда она уходит полностью из разметки и не занимает пространство в вёрстке
        ic_eye.visibility = if (isEdit) View.GONE else View.VISIBLE
        wr_about.isCounterEnabled = isEdit

        with(btn_edit) {
            val filter: ColorFilter? =
                    if (isEdit) {
                        PorterDuffColorFilter(
                                resources.getColor(R.color.color_accent, theme),
                                PorterDuff.Mode.SRC_IN)
                    } else null

            val icon = if (isEdit) {
                resources.getDrawable(R.drawable.ic_save_black_24dp, theme)
            } else {
                resources.getDrawable(R.drawable.ic_edit_black_24dp, theme)
            }
            background.colorFilter = filter
            setImageDrawable(icon)
        }
    }

    // apply - чтобы обратиться к только что созданному инстансу Profile
    // apply - чтобы не повторять имя экземпляра
    //синим цветом - поля data class Profile. В них записываются значения, введённые в поля ввода EditText (et_)
    private fun saveProfileInfo() {
        Profile(
                firstName = et_first_name.text.toString(),
                lastName = et_last_name.text.toString(),
                about = et_about.text.toString(),
                repository = et_repository.text.toString()
        ).apply {
            viewModel.saveProfileData(this)
        }
    }

    private fun validateRepoUrl(repoUrl: String) =
            repoUrl.isEmpty() || validRegEx.matches(repoUrl.replace("-", ""))

}

