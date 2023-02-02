package com.example.myfirstapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.myfirstapp.adapters.FilmCustomAdapter
import com.example.myfirstapp.api.model.Film
import com.example.myfirstapp.api.retrofit.FilmsApiInterface
import com.example.myfirstapp.api.retrofit.RetrofitClient
import com.example.myfirstapp.databinding.FragmentApiBinding

class ApiFragment : Fragment() {

    private var _binding: FragmentApiBinding? = null

    private val binding get() = _binding!!

    private val args: ApiFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentApiBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.return_button).setOnClickListener {
            val action = ApiFragmentDirections.actionApiFragmentToFirstFragment()
            findNavController().navigate(action)
        }
        val listView = view.findViewById<ListView>(R.id.film_list_view)
        getFilms(this, view, listView)
    }

    private fun getFilms(apiFragment: ApiFragment, view: View, listView: ListView) {
        val retrofit = RetrofitClient.getInstance(apiFragment.context!!)
        val apiInterface = retrofit.create(FilmsApiInterface::class.java)
        val query = args.searchQuery
        val type = when (args.searchType) {
            "Movie" -> "movie"
            "Series" -> "series"
            else -> "all"
        }
        lifecycleScope.launchWhenCreated {
            try {
                val response = apiInterface.searchFilms(query, type)
                if (response.isSuccessful()) {
                    val responseFilms: List<Film> = response.body()!!
                    val filmCustomAdapter = FilmCustomAdapter(apiFragment, view.context, responseFilms)
                    listView.adapter = filmCustomAdapter
                } else {
                    val myToast = Toast.makeText(context, "Bad response!", Toast.LENGTH_SHORT)
                    myToast.show()
                }
            } catch(ex: Exception) {
                Log.e("Error", ex.localizedMessage as String)
            }
        }
    }
}