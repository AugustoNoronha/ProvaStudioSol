package com.example.studiosolprova

//Imports
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ValueRecyclerAdapter(private val values: ArrayList<String>, val color:Int, val size: Float): RecyclerView.Adapter<ValueRecyclerAdapter.ValueHolder>(){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ValueRecyclerAdapter.ValueHolder {
        //Inflar value_item.xml de acordo com o size passando com parâmetro
        val inflatedView = parent.inflate(R.layout.value_item, false)
        inflatedView.layoutParams.width = size.toInt()
        inflatedView.layoutParams.height = (size * 2).toInt()
        return  ValueHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: ValueRecyclerAdapter.ValueHolder, position: Int) {
        val itemValue = values[position]
        holder.bindValue(itemValue, color)
    }

    override fun getItemCount() = values.size

    class ValueHolder(v: View): RecyclerView.ViewHolder(v) {
        fun bindValue(value: String, color:Int) {
            val top = itemView.findViewById<View>(R.id.toptop)
            val topLeft = itemView.findViewById<View>(R.id.topLeft)
            val topRight = itemView.findViewById<View>(R.id.topRight)
            val mid = itemView.findViewById<View>(R.id.mid)
            val botLeft = itemView.findViewById<View>(R.id.botLeft)
            val botRight = itemView.findViewById<View>(R.id.botRight)
            val bot = itemView.findViewById<View>(R.id.bot)

            //Cada component LED possui 7 Views dentro deles
            //que vão se colorindo ou não de acordo com o número
            //que foi passado para a ViewHolder
            when (value) {
                "1" -> {
                    top.setBackgroundResource(R.color.gray)
                    topLeft.setBackgroundResource(R.color.gray)
                    topRight.setBackgroundColor(color)
                    mid.setBackgroundResource(R.color.gray)
                    botLeft.setBackgroundResource(R.color.gray)
                    botRight.setBackgroundColor(color)
                    bot.setBackgroundResource(R.color.gray)
                }
                "2" -> {
                    top.setBackgroundColor(color)
                    topLeft.setBackgroundResource(R.color.gray)
                    topRight.setBackgroundColor(color)
                    mid.setBackgroundColor(color)
                    botLeft.setBackgroundColor(color)
                    botRight.setBackgroundResource(R.color.gray)
                    bot.setBackgroundColor(color)
                }
                "3" -> {
                    top.setBackgroundColor(color)
                    topLeft.setBackgroundResource(R.color.gray)
                    topRight.setBackgroundColor(color)
                    mid.setBackgroundColor(color)
                    botLeft.setBackgroundResource(R.color.gray)
                    botRight.setBackgroundColor(color)
                    bot.setBackgroundColor(color)
                }
                "4" -> {
                    top.setBackgroundResource(R.color.gray)
                    topLeft.setBackgroundColor(color)
                    topRight.setBackgroundColor(color)
                    mid.setBackgroundColor(color)
                    botLeft.setBackgroundResource(R.color.gray)
                    botRight.setBackgroundColor(color)
                    bot.setBackgroundResource(R.color.gray)
                }
                "5" -> {
                    top.setBackgroundColor(color)
                    topLeft.setBackgroundColor(color)
                    topRight.setBackgroundResource(R.color.gray)
                    mid.setBackgroundColor(color)
                    botLeft.setBackgroundResource(R.color.gray)
                    botRight.setBackgroundColor(color)
                    bot.setBackgroundColor(color)
                }
                "6" -> {
                    top.setBackgroundColor(color)
                    topLeft.setBackgroundColor(color)
                    topRight.setBackgroundResource(R.color.gray)
                    mid.setBackgroundColor(color)
                    botLeft.setBackgroundColor(color)
                    botRight.setBackgroundColor(color)
                    bot.setBackgroundColor(color)
                }
                "7" -> {
                    top.setBackgroundColor(color)
                    topLeft.setBackgroundResource(R.color.gray)
                    topRight.setBackgroundColor(color)
                    mid.setBackgroundResource(R.color.gray)
                    botLeft.setBackgroundResource(R.color.gray)
                    botRight.setBackgroundColor(color)
                    bot.setBackgroundResource(R.color.gray)
                }
                "8" -> {
                    top.setBackgroundColor(color)
                    topLeft.setBackgroundColor(color)
                    topRight.setBackgroundColor(color)
                    mid.setBackgroundColor(color)
                    botLeft.setBackgroundColor(color)
                    botRight.setBackgroundColor(color)
                    bot.setBackgroundColor(color)
                }
                "9" -> {
                    top.setBackgroundColor(color)
                    topLeft.setBackgroundColor(color)
                    topRight.setBackgroundColor(color)
                    mid.setBackgroundColor(color)
                    botLeft.setBackgroundResource(R.color.gray)
                    botRight.setBackgroundColor(color)
                    bot.setBackgroundColor(color)
                }
                else -> {
                    top.setBackgroundColor(color)
                    topLeft.setBackgroundColor(color)
                    topRight.setBackgroundColor(color)
                    mid.setBackgroundResource(R.color.gray)
                    botLeft.setBackgroundColor(color)
                    botRight.setBackgroundColor(color)
                    bot.setBackgroundColor(color)
                }
            }
        }

    }
}