package com.example.dictionaryapp.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.dictionaryapp.R
import com.example.dictionaryapp.databinding.WordInfoItemBinding
import com.example.dictionaryapp.domain.model.Meaning
import com.example.dictionaryapp.domain.model.Phonetic
import com.example.dictionaryapp.domain.model.WordInfo

class WordInfoAdapter(private val onClick: (WordInfo) -> Unit) :
    ListAdapter<WordInfo, WordInfoAdapter.WordInfoViewHolder>(DiffCallback) {

    class WordInfoViewHolder(
        itemView: View,
        val onClick: (WordInfo) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {

        private val binding = WordInfoItemBinding.bind(itemView)
        private var currentWordInfo: WordInfo? = null

        init {
            itemView.setOnClickListener {
                currentWordInfo?.let {
                    onClick(it)
                }
            }
        }

        fun bind(wordInfo: WordInfo, isUsingDivider: Boolean = true) = with(binding) {
            currentWordInfo = wordInfo

            tvWordTitle.text = wordInfo.word

            val phonetic = wordInfo.phonetic
            if (phonetic.isNotEmpty()) {
                tvPhoneticTitle.apply {
                    text = wordInfo.phonetic
                    visibility = View.VISIBLE
                }
            } else {
                tvPhoneticTitle.visibility = View.GONE
            }

            val formatedPhonetics = getFormattedPhonetics(wordInfo.phonetics)
            if (formatedPhonetics.isNotEmpty()) {
                tvPhoneticsTitle.apply {
                    text = "Phonetics"
                    visibility = View.VISIBLE
                }
                tvPhoneticsSubtitle.apply {
                    text = formatedPhonetics
                    visibility = View.VISIBLE
                }
            } else {
                tvPhoneticsTitle.visibility = View.GONE
                tvPhoneticsSubtitle.visibility = View.GONE
            }

            val formatedNouns = getFormattedMeanings(wordInfo.meanings, "noun")
            if (formatedNouns.isNotEmpty()) {
                tvNounsTitle.apply {
                    text = "Nouns"
                    visibility = View.VISIBLE
                }
                tvNounsSubtitle.apply {
                    text = formatedNouns
                    visibility = View.VISIBLE
                }
            } else {
                tvNounsTitle.visibility = View.GONE
                tvNounsSubtitle.visibility = View.GONE
            }

            val formatedVerbs = getFormattedMeanings(wordInfo.meanings, "verb")
            if (formatedVerbs.isNotEmpty()) {
                tvVerbsTitle.apply {
                    text = "Verbs"
                    visibility = View.VISIBLE
                }
                tvVerbsSubtitle.apply {
                    text = formatedVerbs
                    visibility = View.VISIBLE
                }
            } else {
                tvVerbsTitle.visibility = View.GONE
                tvVerbsSubtitle.visibility = View.GONE
            }

            divider.visibility = if (isUsingDivider) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }

        private fun getFormattedPhonetics(phonetics: List<Phonetic>): String {
            var text = ""
            phonetics.forEachIndexed { index, phonetic ->
                text += "${index + 1}. ${phonetic.text}"
                if (index < (phonetics.size - 1)) {
                    text += "\n"
                }
            }
            return text
        }

        private fun getFormattedMeanings(meanings: List<Meaning>, speech: String): String {
            val definitions = meanings
                .filter {
                    it.partOfSpeech == speech && it.definitions.isNotEmpty()
                }
                .map {
                    it.definitions
                }
                .flatten()

            var text = ""
            definitions.forEachIndexed { index, definition ->
                text += "${index + 1}. ${definition.definition}"
                if (index < (definitions.size - 1)) {
                    text += "\n"
                }
            }
            return text
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordInfoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.word_info_item, parent, false)
        return WordInfoViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: WordInfoViewHolder, position: Int) {
        val currency = getItem(position)
        val isUsingDivider = position < (itemCount - 1)
        holder.bind(currency, isUsingDivider)
    }
}

private object DiffCallback : DiffUtil.ItemCallback<WordInfo>() {
    override fun areItemsTheSame(oldItem: WordInfo, newItem: WordInfo): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: WordInfo, newItem: WordInfo): Boolean {
        return oldItem.hashCode() == newItem.hashCode()
    }
}
