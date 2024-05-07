package com.example.project1_basicpaintapp

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import com.example.project1_basicpaintapp.databinding.ActivityMainBinding
import yuku.ambilwarna.AmbilWarnaDialog
import yuku.ambilwarna.AmbilWarnaDialog.OnAmbilWarnaListener

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
        // change brush size
       binding.imgChangeBrushSize.setOnClickListener {
           showBrushChangeDialog()
       }
        //change brush color
        binding.imgBtnBrushColor.setOnClickListener {
            showColorBrushChangeDialog()
        }

    }

    private fun showColorBrushChangeDialog() {
        val dialogChangeColor = AmbilWarnaDialog(this, Color.BLACK,object :OnAmbilWarnaListener{
            override fun onCancel(dialog: AmbilWarnaDialog?) {

            }

            override fun onOk(dialog: AmbilWarnaDialog?, color: Int) {
                binding.drawingView.changeBrushColor(color)
            }

        })
        dialogChangeColor.show()
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
            @SuppressLint("SetTextI18n")
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