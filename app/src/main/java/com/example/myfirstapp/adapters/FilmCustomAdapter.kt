package com.example.myfirstapp.adapters
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.myfirstapp.R
import com.example.myfirstapp.api.model.Film

class FilmCustomAdapter(private val frag: Fragment, val c: Context, val films: List<Film>): ArrayAdapter<Film>(c, R.layout.film_row_item, films) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = frag.layoutInflater
        val rowView = inflater.inflate(R.layout.film_row_item, null, true)

        val nameText = rowView.findViewById(R.id.name) as TextView
        val yearText = rowView.findViewById(R.id.year) as TextView
        val typeText = rowView.findViewById(R.id.type) as TextView

        val currentFilm = films[position]

        nameText.text = currentFilm.name
        yearText.text = currentFilm.year
        typeText.text = currentFilm.type

        return rowView
    }
}