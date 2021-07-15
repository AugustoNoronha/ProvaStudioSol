package com.example.studiosolprova

//imports

import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.dhaval2404.colorpicker.ColorPickerDialog
import com.github.dhaval2404.colorpicker.model.ColorShape
import com.google.android.material.button.MaterialButton
import com.google.android.material.slider.Slider
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {

    //Properties

    private val baseUrl = "https://us-central1-ss-devops.cloudfunctions.net/"
    private var value = 0
    private var currentLed =ArrayList<String>()
    private var fontSize = 200f
    private var currentColor = -51591

    //UI
    private lateinit var  statusTextView: TextView
    private lateinit var  newGameButton: MaterialButton
    private lateinit var  valueTextInput: TextInputEditText
    private lateinit var  sendButton: MaterialButton
    private lateinit var  toolBar: Toolbar
    private lateinit var  valueTextInputLayout: TextInputLayout
    private lateinit var  linearLayoutManager: LinearLayoutManager
    private lateinit var  valueRecycleView: RecyclerView
    private lateinit var  adapter: ValueRecyclerAdapter




    //Lifecycle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bindUi()
        bindActions()
        setupToolbar()
        setupRecyclerView()
        didChooseColor(currentColor)
        newGame()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean{
        menuInflater.inflate((R.menu.menu), menu)
        return true
    }

    override  fun onOptionsItemSelected(item: MenuItem): Boolean = when(item.itemId){
        R.id.fontColor -> didClickFontColorItem()
        R.id.fontSize -> didClickFontSizeItem()
        else -> true
    }

    //Bindings
    private fun bindUi() {
        statusTextView = findViewById(R.id.statusTextView)
        newGameButton = findViewById(R.id.newGameButton)
        valueTextInput = findViewById(R.id.valueTextInput)
        sendButton = findViewById(R.id.sendButton)
        toolBar = findViewById(R.id.toolbar)
        valueTextInputLayout = findViewById(R.id.valueTextInputLayout)
        valueRecycleView = findViewById(R.id.valueRecycleView)
    }

    private fun bindActions() {
        sendButton.setOnClickListener { didClickSentButton() }
        newGameButton.setOnClickListener { didClickNewGameButton() }
    }

    //RecyclerView

    private fun setupRecyclerView() {
        linearLayoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false)
        valueRecycleView.layoutManager = linearLayoutManager
        adapter = ValueRecyclerAdapter(currentLed , currentColor, fontSize)
        valueRecycleView.adapter = adapter
    }

    private fun remakeAdapter(){
        adapter = ValueRecyclerAdapter(currentLed , currentColor, fontSize)
        valueRecycleView.adapter = adapter

    }

    //Toolbar
    private fun setupToolbar(){
        setSupportActionBar(toolBar)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    }

    //Dialog de cores, usando biblioteca: dhaval2404:colorpicker:2.0
    private fun didClickFontColorItem(): Boolean {
        ColorPickerDialog
            .Builder(this)
            .setTitle("Escolha a cor")
            .setColorShape(ColorShape.SQAURE)
            .setDefaultColor(0)
            .setColorListener({ color, _ ->
                didChooseColor(color)
                remakeAdapter()
            })
            .show()
        return true
    }

    private fun didChooseColor(color:Int){
        currentColor = color
        sendButton.setBackgroundColor(color)
        newGameButton.setBackgroundColor(color)
        toolBar.setBackgroundColor(color)
        window.statusBarColor = color
        valueTextInputLayout.boxStrokeColor = color
        valueTextInputLayout.hintTextColor = ColorStateList.valueOf(color)
        adapter.notifyDataSetChanged()
    }

    //Dialog para alterar o tamanho do led no arquivo xml: font_size_dialog.xml
    private fun didClickFontSizeItem(): Boolean {
        val builder = AlertDialog.Builder(this)
        val view = layoutInflater.inflate(R.layout.font_size_dialog, null)
        builder.setTitle("Escolha o tamanho!")
        builder.setView(view)
        var alert = builder.create()
        view.findViewById<Slider>(R.id.slider).value = fontSize
        view.findViewById<MaterialButton>(R.id.applyButton).setOnClickListener {
            val sliderValue = view.findViewById<Slider>(R.id.slider).value
            fontSize = sliderValue
            adapter.notifyDataSetChanged()
            remakeAdapter()
            alert.dismiss()
        }
       alert.show()
        return true
    }
    //Actions

    private fun didClickSentButton(){
        makeGuess()
    }

    private fun didClickNewGameButton(){
        newGame()
    }

    //Lógica do jogo:
    //Fazendo a requisição ao começar uma nova partida, checando sempre
    //se o usuario ganhou ou não a cada rodada
    private fun makeGuess(){
        if( valueTextInput.text.toString() == ""){ return }
        val guess = valueTextInput.text.toString().toInt()

        statusTextView.visibility = View.VISIBLE
//
        if(guess < value){
            statusTextView.text = "É Maior!"
        }else if(guess > value) {
            statusTextView.text = "É menor!"
        }else {
            treatWin()
        }

        currentLed.clear()
        guess.toString().forEach { char ->
            currentLed.add("$char")

        }
        adapter.notifyDataSetChanged()

        valueTextInput.setText("")
    }

    private fun treatWin(){
        statusTextView.text = "Acertou!"
        newGameButton.visibility = View.VISIBLE
        sendButton.isEnabled = false
    }

    private fun newGame(){
        getValue { value ->
            when(value){
                502 -> treatError()
                else -> treatValue(value)
            }
        }
    }

    //TreatValue e TreatError mudando a UI de acordo com necessidade
    private  fun treatValue(value:Int){
        println(value)
        this.value = value
        statusTextView.visibility = View.INVISIBLE
        newGameButton.visibility =View.INVISIBLE
        currentLed.clear()
        currentLed.add("0")
        adapter.notifyDataSetChanged()
        sendButton.isEnabled = true
    }

    private fun treatError(){
        statusTextView.visibility = View.VISIBLE
        newGameButton.visibility =View.VISIBLE
        statusTextView.text = "Erro"
        currentLed.clear()
        currentLed.add("5")
        currentLed.add("0")
        currentLed.add("2")
        adapter.notifyDataSetChanged()
        sendButton.isEnabled = false
    }


    //Service
    //Funções de requisiçãoes API utilizando: retrofit2:retrofit:2.9.0
    private fun getService() =  Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(OkHttpClient.Builder().build())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ValueServices::class.java)

    private fun getValue(completion:(value: Int) -> Unit) {
        getService().getValue().enqueue(object : Callback<ValueModel> {
            override fun onResponse(call: Call<ValueModel>, response: Response<ValueModel>) {
                completion(response.body()?.value ?: 502)
            }
            override fun onFailure(call: Call<ValueModel>, t: Throwable) {
                completion(502)
            }
        })
    }
}




