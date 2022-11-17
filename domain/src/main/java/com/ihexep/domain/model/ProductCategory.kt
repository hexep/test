package com.ihexep.domain.model

data class ProductCategory(
    val id: Int,
    val title: String,
    val imageUrl: String
)

// Fake class. I think it should come from server like id, title, imageUrl
// Now image is path to drawable resource
enum class Categories (val id: Int, val title: String, val imageUrl: String) {
    Phones(0, "Phones", "cat_phones"),
    Computer(1, "Computer", "cat_computer"),
    Health(2, "Health", "cat_health"),
    Books(3, "Books", "cat_books"),
    Tools(4, "Tools", "cat_books")
}