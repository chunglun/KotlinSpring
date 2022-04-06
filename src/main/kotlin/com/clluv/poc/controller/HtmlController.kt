package com.clluv.poc.controller

import com.clluv.poc.exception.NFTNotFoundException
import com.clluv.poc.model.NFT
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RequestMapping("/nfts")
@RestController
class HtmlController {

    @Value("\${company_name}")
    private lateinit var name: String

    private var NFTs = mutableListOf(
        NFT(1, "CryptoPunks", 100.0),
        NFT(2, "Sneaky Vampire Syndicate", 36.9),
        NFT(3, "The Sevens (Official)", 0.6),
        NFT(4, "Art Blocks Curated", 1.1),
        NFT(5, "Pudgy Penguins", 2.5),
    )

    @GetMapping("/hello")
    fun sayHello(): String = "Hello World"

    @GetMapping("")
    fun getNFTs() = NFTs

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    fun postNFT(@RequestBody nft: NFT): NFT {
        val maxId = NFTs.map { it.id }.maxOrNull() ?: 0
        val nextId = maxId + 1
        val newNft = NFT(id = nextId, name = nft.name, floor_price = nft.floor_price)
        NFTs.add(newNft)
        return newNft
    }

    @GetMapping("/{id}")
    fun getNFTById(@PathVariable id: Int): NFT? = NFTs.firstOrNull { it.id == id } ?: throw NFTNotFoundException()

    @GetMapping("/home")
    fun getHome() = "$name: NFTs Marketplace"
}