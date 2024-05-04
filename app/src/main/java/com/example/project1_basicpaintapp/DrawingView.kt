package com.example.project1_basicpaintapp

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.graphics.Path
import android.view.View
//Class Kotlin là một khai báo để tạo ra các đối tượng.
//File Kotlin là một tệp tin chứa mã nguồn bao gồm các class Kotlin và các thành phần khác.
class DrawingView (context: Context, attributeSet: AttributeSet): View(context, attributeSet){
    // đây bản chất là một view không phải 1 class nên cần kế thừa lớp view bởi nó chứa đủ các code, thuộc tính cần thiết
    // cho việc tạo giao diện cho drawapp, đảm bảo android có thể nhận dạng được nó.
    // tạo các inner class chứa các thuộc tính của ứng dụng
    // drawing path
    private lateinit var drawPath: FingerPath
    // define what to draw
    private lateinit var canvasPaint: Paint
    //define how to draw
    private lateinit var drawPaint: Paint
    //color
    private var color = Color.BLACK
    //canvas giữ bản vẽ, để vẽ 1 cái gì đó thì cần 4 thành phần cơ bản
    // bitmap để giữ các pixel, canvas giữ draw calls, paint chữa thông tin về kiểu, màu sắc nét
    private lateinit var canvas: Canvas
    private lateinit var canvasBitmap: Bitmap
    private var brushSize: Float = 0.toFloat()
    init {
        setUpDrawing()
    }
    // khi màn hình thay đổi hướng ngang dọc bất kì
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {

        canvasBitmap = Bitmap.createBitmap(w,h,Bitmap.Config.ARGB_8888)

    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawBitmap(canvasBitmap,0f,0f,drawPaint)

        if (!drawPath.isEmpty ) {
            drawPaint.strokeWidth = drawPath.brushThickness
            drawPaint.color = drawPath.color
            canvas.drawPath(drawPath,drawPaint) //drawing path on canvas
        }
    }

    private fun setUpDrawing() {
        drawPaint = Paint()
        drawPath = FingerPath(color,brushSize) // lưu phần hiện tại đc vẽ bởi user
        drawPaint.color = color
        drawPaint.style = Paint.Style.STROKE  // lamf cho phần được vẽ là 1 nét thằng
        drawPaint.strokeJoin = Paint.Join.ROUND // 2 cái round dùng đê cho nét nó tròn,
        drawPaint.strokeCap = Paint.Cap.ROUND
        canvasPaint = Paint(Paint.DITHER_FLAG) // xdinh kiểu của canvas và làm cho nét vẽ ra đc mịn
        brushSize = 20.toFloat()
    }

    internal inner class FingerPath(var color:Int , var brushThickness:Float):Path()
}

