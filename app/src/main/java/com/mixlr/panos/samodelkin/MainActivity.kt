package com.mixlr.panos.samodelkin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.provider.Contacts
import com.mixlr.panos.samodelkin.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.io.Serializable

private const val CHARACTER_DATA_KEY = "CHARACTER_DATA_KEY"

private var Bundle.characterData
    get() = getSerializable(CHARACTER_DATA_KEY) as CharacterGenerator.CharacterData
    set(value) = putSerializable(CHARACTER_DATA_KEY, value)

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var characterData: CharacterGenerator.CharacterData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        CoroutineScope(Dispatchers.IO).launch {
            characterData = savedInstanceState?.characterData ?: CharacterGenerator.generate()
            displayCharacterData()
        }

        binding.btnGenerate.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                characterData = CharacterGenerator.fetchCharacterData()
                displayCharacterData()
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.characterData = characterData
    }

    private suspend fun displayCharacterData() {
        withContext(Dispatchers.Main) {
            characterData.run {
                val cd = this
                binding.apply {
                    nameTextView.text = cd.name
                    raceTextView.text = cd.race
                    dexterityTextView.text = cd.dex
                    wisdomTextView.text = cd.wis
                    strengthTextView.text = cd.str
                }
            }
        }
    }
}