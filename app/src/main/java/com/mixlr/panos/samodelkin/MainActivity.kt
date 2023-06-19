package com.mixlr.panos.samodelkin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mixlr.panos.samodelkin.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var characterData: CharacterGenerator.CharacterData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        characterData = CharacterGenerator.generate()
        displayCharacterData()
        binding.btnGenerate.setOnClickListener {
            characterData = CharacterGenerator.generate()
            displayCharacterData()
        }
    }

    private fun displayCharacterData() {
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