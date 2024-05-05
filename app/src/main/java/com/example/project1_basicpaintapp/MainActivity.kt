package com.example.project1_basicpaintapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import com.example.project1_basicpaintapp.databinding.ActivityMainBinding

@SuppressLint("StaticFieldLeak")
private lateinit var binding: ActivityMainBinding
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
       binding.imgChangeBrushSize.setOnClickListener {
           showBrushChangeDialog()
       }
        binding.seekbarBrushSize.visibility = View.GONE
    }

    private fun showBrushChangeDialog() {
        if(binding.seekbarBrushSize.isVisible == false){
            binding.seekbarBrushSize.visibility = View.VISIBLE
            binding.txtBrushSize.visibility = View.VISIBLE
        }else{
            binding.seekbarBrushSize.visibility = View.GONE
            binding.txtBrushSize.visibility = View.GONE
        }

        binding.seekbarBrushSize.setOnSeekBarChangeListener(object :SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar, progess: Int, p2: Boolean) {
                binding.txtBrushSize.setText("$progess/100")
                val brushSize = seekBar.progress.toFloat()
                binding.drawingView.changeBrushSize(brushSize)


            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }

        })

    }


}