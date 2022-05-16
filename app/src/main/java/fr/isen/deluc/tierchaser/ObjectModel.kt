package fr.isen.deluc.tierchaser

class ObjectModel(
    val id: String = "plant0",
    val name: String = "Bonnet",
    val description: String = "Bonnet vintage en taille S",
    val imageUrl: String = "https://cdn.pixabay.com/photo/2019/09/26/09/09/knit-beanie-cap-4505450_960_720.jpg",
    val prix: String = "14,9 euros",
    var liked: Boolean = false,
    var user: String = "c.nadaud"
)