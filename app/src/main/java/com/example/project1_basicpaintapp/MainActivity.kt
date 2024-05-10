package com.example.project1_basicpaintapp

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.ActivityChooserView.InnerLayout
import androidx.core.app.ActivityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import com.example.project1_basicpaintapp.databinding.ActivityMainBinding
import yuku.ambilwarna.AmbilWarnaDialog
import yuku.ambilwarna.AmbilWarnaDialog.OnAmbilWarnaListener

@SuppressLint("StaticFieldLeak")
private lateinit var binding: ActivityMainBinding
class MainActivity : AppCompatActivity() {
    //request permission

    val requesPermission: ActivityResultLauncher<Array<String>> =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()){
        permissions -> permissions.entries.forEach{
            val permissionName = it.key
            val isGranted = it.value

            if (isGranted && permissionName == android.Manifest.permission.READ_MEDIA_IMAGES){
                Toast.makeText(this,"THE PERMISSION IS GRANTED",Toast.LENGTH_SHORT).show()
                val pickIntent  = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                openGalleryLauncher.launch(pickIntent)
            }else{
                if(permissionName == android.Manifest.permission.READ_MEDIA_IMAGES){
                    Toast.makeText(this,"THE PERMISSION IS DENIED",Toast.LENGTH_SHORT).show()
                }
            }
    }
    }
    //lay anh tu gallery
    private val openGalleryLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result -> findViewById<ImageView>(R.id.imgGallery).setImageURI(result.data?.data)
        }


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
        binding.imgBtnUndo.setOnClickListener {
            binding.drawingView.UndoPath()
        }
        binding.imgBtnEraser.setOnClickListener {
            binding.drawingView.ClearAll()
            binding.drawingView.UndoPath()
        }

        binding.imgBtnGallery.setOnClickListener {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED ){
                requestStoragePermission()
            }else{
                //truy cap gallery
                val pickIntent  = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                openGalleryLauncher.launch(pickIntent)

            }
        }



    }

    private fun requestStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                android.Manifest.permission.READ_MEDIA_IMAGES)){
            showRationalDialog()
        }else{
            requesPermission.launch(
                arrayOf(android.Manifest.permission.READ_MEDIA_IMAGES)
            )
        }
    }

    private fun showRationalDialog() {
        val builder = AlertDialog.Builder(this)
        builder.apply {
            //tieu de
            setTitle("STORAGE PERMISSION")
            setMessage("To Access the Gallery")
            setPositiveButton("Yes") { dialogInterface: DialogInterface, i: Int ->
                requesPermission.launch(
                    arrayOf(android.Manifest.permission.READ_MEDIA_IMAGES)
                )
                finish()
            }
            setNegativeButton("No") { dialogInterface: DialogInterface, i: Int ->
                dialogInterface.dismiss()
            }


        }
        builder.create().show()
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